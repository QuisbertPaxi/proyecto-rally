package v1._5_Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import v1._1_Model.Actividad;
import v1._1_Model.OfertaConsumidor;
import v1._2_DTO.OfertaConsumidorDTO;
import v1._4_Service.Interface.OfertaConsumidorService;

import java.util.List;

@Controller
@RequestMapping("/api/v1/ofertas")
@CrossOrigin(origins = {"http://localhost:4200"})
public class OfertaConsumidorController {

    @Autowired
    OfertaConsumidorService ofertaConsumidorService;

    /**-------------------------------------- LOS DOS ----------------------------------------------------**/
    @GetMapping("/{ofertaId}")
    public ResponseEntity<Object> obtenerUnaOfertaPorId (@PathVariable Long ofertaId) {
        OfertaConsumidor offerFound = ofertaConsumidorService.searchOferta(ofertaId);

        if (offerFound == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(offerFound);
    }


    /**-------------------------------------- CONSUMIDORES ----------------------------------------------------**/

    @PostMapping("/Consumidores/Add")
    public ResponseEntity<Object> anadirOferta (@RequestBody OfertaConsumidorDTO ofertaConsumidorDTO) {
        return ofertaConsumidorService.addOferta(ofertaConsumidorDTO);
    }

    @PutMapping("/Consumidores/Update")
    public ResponseEntity<Object> actualizarOferta (@RequestBody  OfertaConsumidorDTO ofertaConsumidorDTO) {
        return ofertaConsumidorService.editOferta(ofertaConsumidorDTO);
    }

    @DeleteMapping("/Consumidores/Delete")
    public ResponseEntity<Object> EliminarOferta (@RequestBody  OfertaConsumidorDTO ofertaConsumidorDTO) {
        return ofertaConsumidorService.deleteOferta(ofertaConsumidorDTO);
    }

    @GetMapping("/Consumidores/All/{idConsumidor}")
    public ResponseEntity<Object> obtenerOfertasConsumidor (@PathVariable Long idConsumidor) {

        List<OfertaConsumidorDTO> ofertasListConsumidor = ofertaConsumidorService.obtenerOfertasPorConsumidor(idConsumidor);

        if (ofertasListConsumidor == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso de entrar en esta página");
        }

        if (ofertasListConsumidor.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ofertasListConsumidor);
    }



    /**-------------------------------------- OFERTANTES ----------------------------------------------------**/

    @GetMapping("/Ofertantes/All/{ofertanteId}")
    public ResponseEntity<Object> obtenerTodasLasOfertas (@PathVariable Long ofertanteId) {

        List<OfertaConsumidorDTO> allOfertas = ofertaConsumidorService.allOfertas(ofertanteId);

        if (allOfertas == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso para estar en este apartado");
        }

        if (allOfertas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allOfertas);
    }

    @GetMapping("/Ofertantes")
    public ResponseEntity<Object> enviarDatosParaCrearActividad (@RequestBody OfertaConsumidorDTO ofertaConsumidorDTO){
        return ofertaConsumidorService.enviarOfertaOfertante(ofertaConsumidorDTO);
    }

}
