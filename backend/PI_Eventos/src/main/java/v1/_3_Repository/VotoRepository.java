package v1._3_Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import v1._1_Model.Fotografia;
import v1._1_Model.Voto;
import v1._2_DTO.FotografiaDTO;

import java.util.List;
import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    @Query("SELECT COUNT(v) FROM Voto v WHERE v.fotografia.id = :idFotografia")
    Long contarVotosPorFotografia(Long idFotografia);

    Optional<Voto> findByIp(String ip);

}
