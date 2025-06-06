package v1._4_Service.Interface;

import org.springframework.http.ResponseEntity;
import v1._2_DTO.ReservaDTO;

public interface ReservaServicio {
    public ResponseEntity<Object> listaDeReservasDeUnConsumidor (ReservaDTO reservaDTO);
    public ResponseEntity<Object> deleteReserva (ReservaDTO reservaDTO);
}
