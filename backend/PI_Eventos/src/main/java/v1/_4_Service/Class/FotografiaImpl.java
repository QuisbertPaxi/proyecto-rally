package v1._4_Service.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import v1._1_Model.Fotografia;
import v1._1_Model.Reserva;
import v1._1_Model.Usuario;
import v1._2_DTO.FotografiaDTO;
import v1._3_Repository.FotografiaRepository;
import v1._3_Repository.ReservaRepository;
import v1._3_Repository.UsuarioRepository;
import v1._4_Service.Interface.FotografiaService;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    ReservaRepository reservaRepository;

    String of = "participante";

    @Override
    public List<FotografiaDTO> allFotografiasAprobadas() {
        return fotografiaRepository.findAllDatosPorEstado("APROBADO");
    }

    @Override
    public List<FotografiaDTO> allFotografiasPorEstado(Long adminId, String estado) {
        Usuario admin = usuarioRepository.findById(adminId).orElse(null);

        if (admin == null || !admin.getRole().equals("admin")) {
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

        System.out.println(a.toString());
        FotografiaDTO foundAct = convertirADTO(a);

        System.out.println(foundAct.toString());

        return foundAct;
    }

    @Override
    public ResponseEntity<Object> addFotografia(FotografiaDTO fotografiaDTO) {

        Usuario participante = usuarioRepository.findById(fotografiaDTO.getIdParticipante()).orElse(null);

        if(participante.getRole() == null || participante == null || fotografiaDTO == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if (fotografiaDTO.getDescripcion() == null ||
                fotografiaDTO.getIdParticipante() == null ||
                fotografiaDTO.getTitulo() == null ||
                fotografiaDTO.getLink() == null  ||
                fotografiaDTO.getUsuCre() == null) {

            return ResponseEntity.badRequest().body("Ha habido un error revise el envio de datos");
        }

        Fotografia a = new Fotografia(fotografiaDTO);

        // participante.addFotografia(a);

        a.setParticipante(participante);
        fotografiaRepository.save(a);

        return ResponseEntity.ok(a);
    }

    @Override
    public ResponseEntity<Object> editFotografia(FotografiaDTO fotografiaDTO) {
        Fotografia fotografia = fotografiaRepository.findById(fotografiaDTO.getId()).orElse(null);
        Usuario user = usuarioRepository.findById(fotografiaDTO.getIdParticipante()).orElse(null);

        if (user.getRole() == null || user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if( fotografia == null) {
            return  ResponseEntity.notFound().build();
        }

        if (fotografiaDTO.getDescripcion() == null ||
                fotografiaDTO.getLink() == null  ||
                fotografiaDTO.getUsuMod() == null ||
                fotografiaDTO.getTitulo() == null) {

            return ResponseEntity.badRequest().body("No esta bien los datos");
        }
        System.out.println(fotografia.toString());
        System.out.println(fotografiaDTO.toString());
        fotografia.setTitulo(fotografiaDTO.getTitulo());
        fotografia.setDescripcion(fotografiaDTO.getDescripcion());
        fotografia.setLink(fotografiaDTO.getLink());
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

        if (user.getRole() == null || fotografiaDTO == null || user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (fotografia == null) {
            return  ResponseEntity.notFound().build();
        }

        if (fotografiaDTO.getUsuMod() == null) {

            return ResponseEntity.badRequest().body("No se envió el usuario que elimina el registro");
        }
        fotografia.setEstado("ELIMINADO");
        fotografia.setUsuarioModificacion(fotografiaDTO.getUsuMod());
        fotografia.setFechaModificacion(new Timestamp(new Date().getTime()));
        fotografiaRepository.save(fotografia);

        return ResponseEntity.ok("Se ha eliminado con exito la fotografia");
    }

    @Override
    public List<FotografiaDTO> obtenerFotografiasPorParticipante(Long participanteId) {
        Usuario participante = usuarioRepository.findByIdAndEstadoNot(participanteId, "ELIMINADO").orElse(null);

        if (participante == null || participante.getRole() == null) {
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

    /*@Override
    public ResponseEntity<Object> anadirFotografiaConsumidor(FotografiaDTO fotografiaDTO) {

        System.out.println(fotografiaDTO);

        Fotografia act = fotografiaRepository.findById(fotografiaDTO.getId()).orElse(null);
        Usuario consumidor = usuarioRepository.findById(fotografiaDTO.getIdConsumidor()).orElse(null);

        System.out.println(consumidor);

        if (consumidor.getRole().equals(of) || fotografiaDTO == null || consumidor == null || act == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes acceder ha este apartado");
        }

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setFotografia(act);
        nuevaReserva.setConsumidor(consumidor);
        nuevaReserva.setFechaReserva(fotografiaDTO.getFechaReserva());

        reservaRepository.save(nuevaReserva);

        return ResponseEntity.ok("La reserva fue un exito");
    }*/

    @Override
    public List<Fotografia> obtenerFotografiasPorTituloODescripcion(String searchWord) {
        return fotografiaRepository.buscarPorTituloODescripcion(searchWord);
    }

    @Override
    public ResponseEntity<Object> aprobarFotografia(Long idFotografia, Long idAdmin) {
        Fotografia fotografia = fotografiaRepository.findById(idFotografia).orElse(null);
        Usuario user = usuarioRepository.findById(idAdmin).orElse(null);
        if (user.getRole() == null || user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if( fotografia == null) {
            return  ResponseEntity.notFound().build();
        }

        fotografia.setEstado("APROBADO");
        fotografia.setUsuarioAprobacion(user.getUserName());
        fotografia.setFechaAprobacion(new Timestamp(new Date().getTime()));

        fotografiaRepository.save(fotografia);

        FotografiaDTO updatedFotografiaDTO = convertirADTO(fotografia);

        return ResponseEntity.ok(updatedFotografiaDTO);
    }
}
