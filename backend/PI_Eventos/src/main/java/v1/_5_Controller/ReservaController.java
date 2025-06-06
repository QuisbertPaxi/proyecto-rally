package v1._5_Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import v1._2_DTO.ReservaDTO;
import v1._4_Service.Interface.ReservaServicio;

@Controller
@RequestMapping("/api/v1/reservas")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ReservaController {

    @Autowired
    ReservaServicio reservaServicio;

    @GetMapping("/Consumidores/All")
    public ResponseEntity<Object> obtenerTodasLasReservasDeUnConsumidor (ReservaDTO reservaDTO) {
        return reservaServicio.listaDeReservasDeUnConsumidor(reservaDTO);
    }

    @DeleteMapping("/Consumidor/Delete")
    public ResponseEntity<Object> elminarReservaDeUnConsumidor (ReservaDTO reservaDTO) {
        return reservaServicio.deleteReserva(reservaDTO);
    }
}
