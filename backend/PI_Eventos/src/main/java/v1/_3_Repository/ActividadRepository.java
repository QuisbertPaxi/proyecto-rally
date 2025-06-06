package v1._3_Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import v1._1_Model.Actividad;
import v1._2_DTO.ActividadDTO;

import java.util.List;

public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    List<Actividad> findByOfertanteId(Long ofertanteId); // Método para encontrar todas las actividades por id del ofertante
    List<Actividad> findByTituloContainingIgnoreCase(String titulo); // Encuentra las actividades que contenga en su titulo la cadena solicitada

    @Query("SELECT new v1._2_DTO.ActividadDTO(a.titulo, a.descripcion, a.fecha, a.ubicacion, a.precio, a.ofertante.id, a.id ) FROM Actividad a")
    List<ActividadDTO> findAllDtos();
}
