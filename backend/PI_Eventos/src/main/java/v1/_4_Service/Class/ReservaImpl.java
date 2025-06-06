package v1._4_Service.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import v1._1_Model.Reserva;
import v1._1_Model.Usuario;
import v1._2_DTO.ReservaDTO;
import v1._3_Repository.ActividadRepository;
import v1._3_Repository.ReservaRepository;
import v1._3_Repository.UsuarioRepository;
import v1._4_Service.Interface.ReservaServicio;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservaImpl implements ReservaServicio {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    ReservaRepository reservaRepository;

    String consu = "consumidor";

    @Override
    public ResponseEntity<Object> listaDeReservasDeUnConsumidor(ReservaDTO reservaDTO) {

        Usuario unicoConsu = usuarioRepository.findById(reservaDTO.getConsumidorId()).orElse(null);

        if (unicoConsu == null || !unicoConsu.getRole().equals(consu)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permito entrar en esta ruta");
        }

        List<ReservaDTO> reservas = new ArrayList<>();
        ReservaDTO reserva = new ReservaDTO();

        List<Reserva> reservasUnicoConsu = reservaRepository.findByConsumidorId(unicoConsu.getId());

        if (reservasUnicoConsu.isEmpty()){
            return ResponseEntity.noContent().build(); // devuelve una lista vacía
        }

        for (Reserva i: reservasUnicoConsu) {
           reserva.setFechaReserva(i.getFechaReserva());
           reserva.setTitulo(i.getActividad().getTitulo());
           reserva.setId(i.getId());
           reservas.add(reserva);
        }

        return ResponseEntity.ok(reservas);
    }

    @Override
    public ResponseEntity<Object> deleteReserva(ReservaDTO reservaDTO) {

        Usuario consumidor = usuarioRepository.findById(reservaDTO.getConsumidorId()).orElse(null);

        if (consumidor == null || !consumidor.getRole().equals(consu)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permito entrar en esta ruta");
        }

        reservaRepository.deleteById(reservaDTO.getId());

        return ResponseEntity.ok("Se ha eliminado correctamente");
    }
}
