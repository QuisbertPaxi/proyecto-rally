package v1._4_Service.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import v1._1_Model.Actividad;
import v1._1_Model.OfertaConsumidor;
import v1._1_Model.Usuario;
import v1._2_DTO.OfertaConsumidorDTO;
import v1._3_Repository.OfertaConsumidorRepository;
import v1._3_Repository.UsuarioRepository;
import v1._4_Service.Interface.OfertaConsumidorService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfertaConsumidorImpl implements OfertaConsumidorService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    OfertaConsumidorRepository ofertaConsumidorRepository;

    String consu = "consumidor";

    @Override
    public ResponseEntity<Object> addOferta(OfertaConsumidorDTO ofertaConsumidorDTO) {

        Usuario consumidor = usuarioRepository.findById(ofertaConsumidorDTO.getIdConsumidor()).orElse(null);

        if(!consumidor.getRole().equals(consu) || consumidor == null || ofertaConsumidorDTO == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if (ofertaConsumidorDTO.getDescripcion() == null ||
                ofertaConsumidorDTO.getUbicacion() == null  ||
                ofertaConsumidorDTO.getFechaSolicitada() == null ||
                ofertaConsumidorDTO.getTitulo() == null  ) {

            return ResponseEntity.badRequest().body("Ha habido un error revise el envio de datos");
        }

        OfertaConsumidor oferta = new OfertaConsumidor(ofertaConsumidorDTO);

        oferta.setConsu(consumidor);

        ofertaConsumidorRepository.save(oferta);

        // editar para que solo me devuelva OK!
        return ResponseEntity.ok(oferta);
    }

    @Override
    public ResponseEntity<Object> editOferta(OfertaConsumidorDTO ofertaConsumidorDTO) {

        OfertaConsumidor ofer = ofertaConsumidorRepository.findById(ofertaConsumidorDTO.getId()).orElse(null);
        Usuario consumidor = usuarioRepository.findById(ofertaConsumidorDTO.getIdConsumidor()).orElse(null);

        if (!consumidor.getRole().equals(consu) || ofertaConsumidorDTO == null || consumidor == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if( ofer == null) {
            return  ResponseEntity.notFound().build();
        }

        if (ofertaConsumidorDTO.getDescripcion() == null ||
                ofertaConsumidorDTO.getUbicacion() == null  ||
                ofertaConsumidorDTO.getFechaSolicitada() == null ||
                ofertaConsumidorDTO.getTitulo() == null  ) {

            return ResponseEntity.badRequest().body("No esta bien los datos");
        }

        ofer.setDescripcion(ofertaConsumidorDTO.getDescripcion());
        ofer.setFechaSolicitada(ofertaConsumidorDTO.getFechaSolicitada());
        ofer.setTitulo(ofertaConsumidorDTO.getTitulo());
        ofer.setUbicacion(ofertaConsumidorDTO.getUbicacion());

        ofertaConsumidorRepository.save(ofer);

        return ResponseEntity.ok(ofer);
    }

    @Override
    public ResponseEntity<Object> deleteOferta(OfertaConsumidorDTO ofertaConsumidorDTO) {

        OfertaConsumidor offer = ofertaConsumidorRepository.findById(ofertaConsumidorDTO.getId()).orElse(null);
        Usuario consumidor = usuarioRepository.findById(ofertaConsumidorDTO.getIdOfertante()).orElse(null);

        if (!consumidor.getRole().equals(consu) || ofertaConsumidorDTO == null || consumidor == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (offer == null) {
            return  ResponseEntity.notFound().build();
        }

        ofertaConsumidorRepository.deleteById(offer.getId());

        return ResponseEntity.ok("Se ha eliminado con exito la oferta");
    }

    @Override
    public List<OfertaConsumidorDTO> obtenerOfertasPorConsumidor(Long consumidorId) {
        Usuario ofertante = usuarioRepository.findById(consumidorId).orElse(null);

        if (ofertante == null || !ofertante.getRole().equals(consu)) {
            return null;
        }

        List<OfertaConsumidor> ofertas = ofertaConsumidorRepository.findByConsuId(consumidorId);

        return ofertas.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private OfertaConsumidorDTO convertirADTO(OfertaConsumidor oferta) {
        OfertaConsumidorDTO dto = new OfertaConsumidorDTO();
        dto.setId(oferta.getId());
        dto.setTitulo(oferta.getTitulo());
        dto.setDescripcion(oferta.getDescripcion());
        dto.setFechaSolicitada(oferta.getFechaSolicitada());
        dto.setUbicacion(oferta.getUbicacion());
        dto.setEstado(oferta.isEstado());
        return dto;
    }

    @Override
    public List<OfertaConsumidorDTO> allOfertas(Long id) {
        Usuario ofertante = usuarioRepository.findById(id).orElse(null);

        if (ofertante == null || ofertante.getRole().equals(consu)) {
            return null;
        }

        return ofertaConsumidorRepository.findAllDtos();
    }

    @Override
    public ResponseEntity<Object> enviarOfertaOfertante(OfertaConsumidorDTO ofertaConsumidorDTO) {

        Usuario ofertante = usuarioRepository.findById(ofertaConsumidorDTO.getIdOfertante()).orElse(null);

        if (ofertante.getRole().equals(consu) || ofertante == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes acceder ha este apartado");
        }

        if (ofertaConsumidorDTO == null || ofertaConsumidorDTO.getId() == null ) {
            return ResponseEntity.badRequest().body("Faltan datos");
        }

        OfertaConsumidor oferta = searchOferta(ofertaConsumidorDTO.getId());

        oferta.setEstado(false);

        return ResponseEntity.ok(oferta);
    }

    @Override
    public OfertaConsumidor searchOferta(Long id) {
        OfertaConsumidor a = ofertaConsumidorRepository.findById(id).orElse(null);

        if (a == null){

            return null;
        }

        return a;
    }
}
