package v1._4_Service.Interface;

import org.springframework.http.ResponseEntity;
import v1._1_Model.Fotografia;
import v1._2_DTO.FotografiaDTO;

import java.util.List;

public interface FotografiaService {

    public ResponseEntity<Object> addFotografia (FotografiaDTO fotografiaDTO); // Añadir una fotografia solo participante
    public ResponseEntity<Object> editFotografia (FotografiaDTO fotografiaDTO); // Actualizar una fotografia solo participante
    public ResponseEntity<Object> deleteFotografia (FotografiaDTO fotografiaDTO); // Eliminar una fotografia solo participante
    public List<FotografiaDTO> obtenerFotografiasPorParticipante(Long participanteId); // Listar todas las fotografias de un participante


    public List<FotografiaDTO> allFotografiasAprobadas (); // Listar todas las fotografias
    public List<FotografiaDTO> allFotografiasPorEstado(Long adminId, String estado);
    //public ResponseEntity<Object> anadirFotografiaConsumidor (FotografiaDTO fotografiaDTO); // Añadimos una fotografia a la reservas de un consumidor
    public List<Fotografia> obtenerFotografiasPorTituloODescripcion (String searchWord); // Lista todas las fotografias que contengan la searchWord para consumidores

    /**--------------------------- LOS DOS -------------------------------------------------------**/
    public FotografiaDTO searchFotografia (Long id); // Listar 1 fotografia dado su id
    public ResponseEntity<Object> aprobarFotografia(Long idFotografia, Long idAdmin); // Aprobar una fotografia, solo administrador

}
