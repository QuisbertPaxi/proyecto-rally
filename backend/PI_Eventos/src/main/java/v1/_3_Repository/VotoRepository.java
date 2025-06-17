package v1._3_Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import v1._1_Model.Voto;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
    @Query("SELECT COUNT(v) FROM Voto v WHERE v.fotografia.id = :idFotografia")
    Long contarVotosPorFotografia(Long idFotografia);

    Optional<Voto> findByIp(String ip);

    @Modifying
    @Transactional
    @Query("DELETE FROM Voto v WHERE v.fotografia.id = :idFotografia")
    void eliminarVotosPorFotografia(Long idFotografia);

}
