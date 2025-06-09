package v1._5_Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import v1._2_DTO.VotoDTO;
import v1._4_Service.Interface.VotoService;

@Controller
@RequestMapping("/votos")
@CrossOrigin(origins = {"http://localhost:4200"})
public class VotoController {

    @Autowired
    VotoService votoService;

    @GetMapping("/{id}")
    public ResponseEntity<Long> contarVotos(@PathVariable Long id) {
        Long cantidadVotos = votoService.contarVotos(id);
        return ResponseEntity.ok(cantidadVotos);
    }

    @PostMapping()
    public ResponseEntity<Object> votarFotografia (@RequestBody VotoDTO votoDTO) {
        return votoService.votarFotografia(votoDTO);
    }
}
