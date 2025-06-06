package v1._3_Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import v1._1_Model.Reserva;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    List<Reserva> findByConsumidorId(Long consumidorId);
}
