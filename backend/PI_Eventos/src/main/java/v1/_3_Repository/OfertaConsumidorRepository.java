package v1._3_Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import v1._1_Model.OfertaConsumidor;
import v1._2_DTO.OfertaConsumidorDTO;

import java.util.List;

public interface OfertaConsumidorRepository extends JpaRepository<OfertaConsumidor, Long> {
    @Query("SELECT new v1._2_DTO.OfertaConsumidorDTO(o.id, o.titulo, o.descripcion, o.fechaSolicitada, o.ubicacion, o.estado) " +
            "FROM OfertaConsumidor o")
    List<OfertaConsumidorDTO> findAllDtos();
    List<OfertaConsumidor> findByConsuId (Long consumidorId); // Método para encontrar todas las ofertas por id del consumidor
}
