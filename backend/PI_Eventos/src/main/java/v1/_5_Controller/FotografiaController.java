package v1._5_Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import v1._1_Model.Fotografia;
import v1._2_DTO.FotografiaDTO;
import v1._4_Service.Interface.FotografiaService;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/v1/fotografias")
@CrossOrigin(origins = {"http://localhost:4200"})
public class FotografiaController {

    @Autowired
    FotografiaService fotografiaService;

    @GetMapping("/{idFotografia}")
    public ResponseEntity<Object> obtenerUnaFotografiaPorId (@PathVariable Long idFotografia) {
        FotografiaDTO fotografiaFound = fotografiaService.searchFotografia(idFotografia);

        if (fotografiaFound == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(fotografiaFound);
    }


    /**-------------------------------------- PARTICIPANTES ----------------------------------------------------**/

    @PostMapping("/Participantes/Add")
    public ResponseEntity<Object> anadirFotografiaParticipante(
            @RequestPart("fotografia") FotografiaDTO fotografiaDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        return fotografiaService.addFotografia(fotografiaDTO, file);
    }

    @PutMapping("/Participantes/Update")
    public ResponseEntity<Object> actualizarFotografia(
            @RequestPart("fotografia") FotografiaDTO fotografiaDTO,
            @RequestPart(value = "file", required = false) MultipartFile file) {

        return fotografiaService.editFotografia(fotografiaDTO, file);
    }


    @DeleteMapping("/Participantes/Delete")
    public ResponseEntity<Object> EliminarFotografia(@RequestBody FotografiaDTO fotografiaDTO){
        return fotografiaService.deleteFotografia(fotografiaDTO);
    }

    @GetMapping("/Participantes/All/{idParticipante}")
    public ResponseEntity<Object> obtenerFotografiasDeUnParticipante (@PathVariable Long idParticipante) {
        List<FotografiaDTO> fotografiaListParticipante = fotografiaService.obtenerFotografiasPorParticipante(idParticipante);
        HashMap<String, String> responseBody = new HashMap<>();

        if (fotografiaListParticipante == null) {
            responseBody.put("mensaje", "No tiene permiso de entrar en esta página");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }

        if (fotografiaListParticipante.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(fotografiaListParticipante);
    }


    @GetMapping("/All")
    public ResponseEntity<Object> obtenerTodasLasFotografiasAprobadas () {
     List<FotografiaDTO> allActivities = fotografiaService.allFotografiasAprobadas();

        if (allActivities == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso para estar en este apartado");
        }

        if (allActivities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allActivities);
    }

    @GetMapping("/State/{id}/{state}")
    public ResponseEntity<Object> obtenerTodasLasFotografiasPorEstado (
            @PathVariable("id") Long idAdmin,
            @PathVariable("state") String estado
    ) {
        List<FotografiaDTO> allActivities = fotografiaService.allFotografiasPorEstado(idAdmin, estado);

        if (allActivities == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso para estar en este apartado");
        }

        if (allActivities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allActivities);
    }

    @GetMapping("/All/{wordSearch}")
    public ResponseEntity<Object> obtenerFotografiasPorCadenaDeLetras (@PathVariable String wordSearch){

        List<Fotografia> fotografiaListWordSearch = fotografiaService.obtenerFotografiasPorTituloODescripcion(wordSearch);

        if (fotografiaListWordSearch == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le permite estar en esta pagina");
        }

        if (fotografiaListWordSearch.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fotografiaListWordSearch);
    }

    @PutMapping("/Aprobar/{idFotografia}/{idAdmin}")
    public ResponseEntity<Object> aprobarFotografia (@PathVariable Long idFotografia, @PathVariable Long idAdmin) {
        return fotografiaService.aprobarFotografia(idFotografia, idAdmin);
    }

}
