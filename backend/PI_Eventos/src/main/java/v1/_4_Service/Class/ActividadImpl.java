package v1._4_Service.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import v1._1_Model.Actividad;
import v1._1_Model.Reserva;
import v1._1_Model.Usuario;
import v1._2_DTO.ActividadDTO;
import v1._2_DTO.UsuarioDTO;
import v1._3_Repository.ActividadRepository;
import v1._3_Repository.ReservaRepository;
import v1._3_Repository.UsuarioRepository;
import v1._4_Service.Interface.ActividadService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActividadImpl implements ActividadService {

    @Autowired
    ActividadRepository actividadRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ReservaRepository reservaRepository;

    String of = "ofertante";

    @Override
    public List<ActividadDTO> allActividades( Long id) {

        Usuario consumidor = usuarioRepository.findById(id).orElse(null);

        if (consumidor == null || consumidor.getRole().equals(of)) {
            return null;
        }

        return actividadRepository.findAllDtos();
    }

    @Override
    public ActividadDTO searchActividad(Long id) {

        Actividad a = actividadRepository.findById(id).orElse(null);

        if (a == null){

            return null;
        }

        System.out.println(a.toString());
        ActividadDTO foundAct = convertirADTO(a);

        System.out.println(foundAct.toString());

        return foundAct;
    }

    @Override
    public ResponseEntity<Object> addActividad(ActividadDTO actividadDTO) {

        Usuario ofertante = usuarioRepository.findById(actividadDTO.getIdOfertante()).orElse(null);

        if(!ofertante.getRole().equals(of) || ofertante == null || actividadDTO == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if (actividadDTO.getDescripcion() == null ||
                actividadDTO.getFecha() == null  ||
                actividadDTO.getPrecio() == null ||
                actividadDTO.getTitulo() == null ||
                actividadDTO.getUbicacion() == null ) {

            return ResponseEntity.badRequest().body("Ha habido un error revise el envio de datos");
        }

        Actividad a = new Actividad(actividadDTO);

        // ofertante.addActividad(a);

        a.setOfertante(ofertante);
        actividadRepository.save(a);

        return ResponseEntity.ok(a);
    }

    @Override
    public ResponseEntity<Object> editActividad(ActividadDTO actividadDTO) {
        Actividad activity = actividadRepository.findById(actividadDTO.getId()).orElse(null);
        Usuario user = usuarioRepository.findById(actividadDTO.getIdOfertante()).orElse(null);

        if (!user.getRole().equals(of) || user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if( activity == null) {
            return  ResponseEntity.notFound().build();
        }

        if (actividadDTO.getDescripcion() == null ||
                actividadDTO.getFecha() == null  ||
                actividadDTO.getPrecio() == null ||
                actividadDTO.getTitulo() == null ||
                actividadDTO.getUbicacion() == null ) {

            return ResponseEntity.badRequest().body("No esta bien los datos");
        }
        System.out.println(activity.toString());
        System.out.println(actividadDTO.toString());
        activity.setDescripcion(actividadDTO.getDescripcion());
        activity.setFecha(actividadDTO.getFecha());
        activity.setTitulo(actividadDTO.getTitulo());
        activity.setUbicacion(actividadDTO.getUbicacion());
        activity.setPrecio(actividadDTO.getPrecio());

        actividadRepository.save(activity);

        ActividadDTO updatedActivityDTO = convertirADTO(activity);

        return ResponseEntity.ok(updatedActivityDTO);
    }

    @Override
    public ResponseEntity<Object> deleteActividad(ActividadDTO actividadDTO) {

        Actividad act = actividadRepository.findById(actividadDTO.getId()).orElse(null);
        Usuario user = usuarioRepository.findById(actividadDTO.getIdOfertante()).orElse(null);

        if (!user.getRole().equals(of) || actividadDTO == null || user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (act == null) {
            return  ResponseEntity.notFound().build();
        }
        actividadRepository.deleteById(act.getId());
        return ResponseEntity.ok("Se ha eliminado con exito la actividad");
    }

    @Override
    public List<ActividadDTO> obtenerActividadesPorOfertante(Long ofertanteId) {
        Usuario ofertante = usuarioRepository.findById(ofertanteId).orElse(null);

        if (ofertante == null || !ofertante.getRole().equals(of)) {
            return null;
        }

        List<Actividad> actividades = actividadRepository.findByOfertanteId(ofertanteId);

        return actividades.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    private ActividadDTO convertirADTO(Actividad actividad) {
        ActividadDTO dto = new ActividadDTO();
        dto.setId(actividad.getId());
        dto.setTitulo(actividad.getTitulo());
        dto.setDescripcion(actividad.getDescripcion());
        dto.setFecha(actividad.getFecha());
        dto.setUbicacion(actividad.getUbicacion());
        dto.setPrecio(actividad.getPrecio());
        dto.setIdOfertante(actividad.getOfertante().getId());

        return dto;
    }

    @Override
    public ResponseEntity<Object> anadirActividadConsumidor(ActividadDTO actividadDTO) {

        System.out.println(actividadDTO);

        Actividad act = actividadRepository.findById(actividadDTO.getId()).orElse(null);
        Usuario consumidor = usuarioRepository.findById(actividadDTO.getIdConsumidor()).orElse(null);

        System.out.println(consumidor);

        if (consumidor.getRole().equals(of) || actividadDTO == null || consumidor == null || act == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes acceder ha este apartado");
        }

        Reserva nuevaReserva = new Reserva();
        nuevaReserva.setActividad(act);
        nuevaReserva.setConsumidor(consumidor);
        nuevaReserva.setFechaReserva(actividadDTO.getFechaReserva());

        reservaRepository.save(nuevaReserva);

        return ResponseEntity.ok("La reserva fue un exito");
    }

    @Override
    public List<Actividad> obtenerActividadesPorTitulo(String searchWord, Long id) {

        Usuario consumidor = usuarioRepository.findById(id).orElse(null);

        if (consumidor == null || consumidor.getRole().equals(of)) {
            return null;
        }


        return actividadRepository.findByTituloContainingIgnoreCase(searchWord);
    }
}
