package v1._3_Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import v1._1_Model.Concurso;

public interface ConcursoRepository extends JpaRepository<Concurso, Long> {

    Concurso findFirstBy();
}
