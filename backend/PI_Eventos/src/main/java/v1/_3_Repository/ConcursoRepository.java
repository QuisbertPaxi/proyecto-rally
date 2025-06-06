package v1._3_Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import v1._1_Model.Concurso;
import v1._1_Model.Fotografia;
import v1._2_DTO.FotografiaDTO;

import java.util.List;

public interface ConcursoRepository extends JpaRepository<Concurso, Long> {

    Concurso findFirstBy();
}
