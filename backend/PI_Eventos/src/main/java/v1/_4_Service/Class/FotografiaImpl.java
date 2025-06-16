package v1._4_Service.Class;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import v1._1_Model.Concurso;
import v1._1_Model.Fotografia;
import v1._1_Model.Usuario;
import v1._2_DTO.FotografiaDTO;
import v1._3_Repository.ConcursoRepository;
import v1._3_Repository.FotografiaRepository;
import v1._3_Repository.UsuarioRepository;
import v1._4_Service.Interface.FotografiaService;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FotografiaImpl implements FotografiaService {

    @Autowired
    FotografiaRepository fotografiaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ConcursoRepository concursoRepository;

    String roleParticipante = "participante";
    String roleAdmin = "admin";

    @Override
    public List<FotografiaDTO> allFotografiasAprobadas() {
        return fotografiaRepository.findAllDatosPorEstado("APROBADO");
    }

    @Override
    public List<FotografiaDTO> allFotografiasPorEstado(Long adminId, String estado) {
        Usuario admin = usuarioRepository.findById(adminId).orElse(null);

        if (admin == null || !admin.getRole().equals(roleAdmin)) {
            return null;
        }

        if(estado.equals("all")){
            return fotografiaRepository.findAllDatos();
        }
        return fotografiaRepository.findAllDatosPorEstado(estado);
    }

    @Override
    public FotografiaDTO searchFotografia(Long id) {

        Fotografia a = fotografiaRepository.findById(id).orElse(null);

        if (a == null){

            return null;
        }

        FotografiaDTO foundAct = convertirADTO(a);
        return foundAct;
    }

    @Override
    public ResponseEntity<Object> addFotografia(FotografiaDTO fotografiaDTO, MultipartFile file) {
        try {
            Usuario participante = usuarioRepository.findById(fotografiaDTO.getIdParticipante()).orElse(null);
            Concurso concurso = concursoRepository.findFirstBy();
            Integer conteoFotografias = fotografiaRepository.countFotografiasAprobadasYPendientes(fotografiaDTO.getIdParticipante());

            if (participante == null || !participante.getRole().equals(roleParticipante) || fotografiaDTO == null) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le está permitido entrar en esta ruta");
            }

            if(conteoFotografias >= concurso.getNumeroFotografias()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Ha excedido el número de fotografías permitidas");

            }

            if (fotografiaDTO.getDescripcion() == null ||
                    fotografiaDTO.getIdParticipante() == null ||
                    fotografiaDTO.getTitulo() == null ||
                    fotografiaDTO.getUsuCre() == null) {

                return ResponseEntity.badRequest().body("Ha habido un error, revise el envío de datos");
            }

            String imageUrl = uploadToImgbb(file);
            if (imageUrl == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("No se pudo subir la imagen");
            }

            // Asignar el link generado al DTO
            fotografiaDTO.setLink(imageUrl);

            Fotografia a = new Fotografia(fotografiaDTO);

            a.setParticipante(participante);
            fotografiaRepository.save(a);

            return ResponseEntity.ok(a);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno: " + e.getMessage());
        }
    }


    @Override
    public ResponseEntity<Object> editFotografia(FotografiaDTO fotografiaDTO, MultipartFile file) {
        Fotografia fotografia = fotografiaRepository.findById(fotografiaDTO.getId()).orElse(null);
        Usuario user = usuarioRepository.findById(fotografiaDTO.getIdParticipante()).orElse(null);

        if (!user.getRole().equals(roleParticipante) || user.getRole() == null || user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if( fotografia == null) {
            return  ResponseEntity.notFound().build();
        }

        if (fotografiaDTO.getDescripcion() == null ||
                fotografiaDTO.getUsuMod() == null ||
                fotografiaDTO.getTitulo() == null) {

            return ResponseEntity.badRequest().body("No esta bien los datos");
        }

        if (file != null && !file.isEmpty()) {
            String newImageUrl = uploadToImgbb(file);
            if (newImageUrl == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al subir la nueva imagen");
            }
            fotografia.setLink(newImageUrl);
        }

        fotografia.setTitulo(fotografiaDTO.getTitulo());
        fotografia.setDescripcion(fotografiaDTO.getDescripcion());
        fotografia.setUsuarioModificacion(fotografiaDTO.getUsuMod());
        fotografia.setFechaModificacion(new Timestamp(new Date().getTime()));

        fotografiaRepository.save(fotografia);

        FotografiaDTO updatedFotografiaDTO = convertirADTO(fotografia);

        return ResponseEntity.ok(updatedFotografiaDTO);
    }

    @Override
    public ResponseEntity<Object> deleteFotografia(FotografiaDTO fotografiaDTO) {

        Fotografia fotografia = fotografiaRepository.findById(fotografiaDTO.getId()).orElse(null);
        Usuario user = usuarioRepository.findByUserName(fotografiaDTO.getUsuMod()).orElse(null);

        if (!user.getRole().equals(roleParticipante) || fotografiaDTO == null || user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (fotografia == null) {
            return  ResponseEntity.notFound().build();
        }

        if (fotografiaDTO.getUsuMod() == null) {

            return ResponseEntity.badRequest().body("No se envió el usuario que elimina el registro");
        }
       // fotografia.setEstado("ELIMINADO");
       // fotografia.setUsuarioModificacion(fotografiaDTO.getUsuMod());
       // fotografia.setFechaModificacion(new Timestamp(new Date().getTime()));
       // fotografiaRepository.save(fotografia);

        fotografiaRepository.eliminarFotografiaPorId(fotografiaDTO.getId());

        return ResponseEntity.ok("Se ha eliminado con exito la fotografia");
    }

    @Override
    public List<FotografiaDTO> obtenerFotografiasPorParticipante(Long participanteId) {
        Usuario participante = usuarioRepository.findByIdAndEstadoNot(participanteId, "ELIMINADO").orElse(null);

        if (participante == null || !participante.getRole().equals(roleParticipante)) {
            return null;
        }

        List<Fotografia> fotografias = fotografiaRepository.findByParticipanteId(participanteId);

        return fotografias.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private FotografiaDTO convertirADTO(Fotografia fotografia) {
        FotografiaDTO dto = new FotografiaDTO();
        dto.setId(fotografia.getId());
        dto.setTitulo(fotografia.getTitulo());
        dto.setDescripcion(fotografia.getDescripcion());
        dto.setLink(fotografia.getLink());
        dto.setEstado(fotografia.getEstado());
        dto.setUsuCre(fotografia.getUsuarioCreacion());
        dto.setFecCre(fotografia.getFechaCreacion());
        dto.setUsuMod(fotografia.getUsuarioModificacion());
        dto.setIdParticipante(fotografia.getParticipante().getId());

        return dto;
    }

    @Override
    public List<Fotografia> obtenerFotografiasPorTituloODescripcion(String searchWord) {
        return fotografiaRepository.buscarPorTituloODescripcion(searchWord);
    }

    @Override
    public Integer countFotografiasAprobadasYPendientes(Long idParticipante){
        Usuario participante = usuarioRepository.findById(idParticipante).orElse(null);

        if (participante == null || !participante.getRole().equals(roleParticipante)) {
            return -1;
        }

        return fotografiaRepository.countFotografiasAprobadasYPendientes(idParticipante);
    }

    @Override
    public ResponseEntity<Object> aprobarFotografia(Long idFotografia, Long idAdmin, Boolean aprobado) {
        Fotografia fotografia = fotografiaRepository.findById(idFotografia).orElse(null);
        Usuario user = usuarioRepository.findById(idAdmin).orElse(null);
        if (!user.getRole().equals(roleAdmin) || user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if( fotografia == null) {
            return  ResponseEntity.notFound().build();
        }

        if(aprobado){
            fotografia.setEstado("APROBADO");
        }else{
            fotografia.setEstado("RECHAZADO");
        }
        fotografia.setUsuarioAprobacion(user.getUserName());
        fotografia.setFechaAprobacion(new Timestamp(new Date().getTime()));

        fotografiaRepository.save(fotografia);

        FotografiaDTO updatedFotografiaDTO = convertirADTO(fotografia);

        return ResponseEntity.ok(updatedFotografiaDTO);
    }

    private String uploadToImgbb(MultipartFile file) {
        try {
            String apiKey = "3a3305a4d94d0d3983f0cdd31f38ebef";

            byte[] imageBytes = file.getBytes();
            String encodedImage = Base64.getEncoder().encodeToString(imageBytes);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.imgbb.com/1/upload?key=" + apiKey))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("image=" + URLEncoder.encode(encodedImage, StandardCharsets.UTF_8)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(response.body());
                return json.get("data").get("url").asText(); // URL pública de la imagen
            } else {
                System.err.println("Error al subir imagen: " + response.body());
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
