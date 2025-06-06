package v1._4_Service.Interface;

import org.springframework.http.ResponseEntity;
import v1._1_Model.Actividad;
import v1._2_DTO.ActividadDTO;

import java.util.List;

public interface ActividadService {

    /**--------------------------- OFERTANTES ------------------------------------------------**/
    public ResponseEntity<Object> addActividad (ActividadDTO actividadDTO); // Añadir una actividad solo ofertante
    public ResponseEntity<Object> editActividad (ActividadDTO actividadDTO); // Actualizar una actividad solo ofertante
    public ResponseEntity<Object> deleteActividad (ActividadDTO actividadDTO); // Eliminar una actividad solo ofertante
    public List<ActividadDTO> obtenerActividadesPorOfertante(Long ofertanteId); // Listar todas las actividades de un ofertante


    /**--------------------------- CONSUMIDORES ------------------------------------------------**/
    public List<ActividadDTO> allActividades (Long id); // Listar todas las actividades solo para consumidores
    public ResponseEntity<Object> anadirActividadConsumidor (ActividadDTO actividadDTO); // Añadimos una actividad a la reservas de un consumidor
    public List<Actividad> obtenerActividadesPorTitulo (String searchWord, Long ofertanteId); // Lista todas las actividades que contengan la searchWord para consumidores


    /**--------------------------- LOS DOS -------------------------------------------------------**/
    public ActividadDTO searchActividad (Long id); // Listar 1 actividad dado su id
}
