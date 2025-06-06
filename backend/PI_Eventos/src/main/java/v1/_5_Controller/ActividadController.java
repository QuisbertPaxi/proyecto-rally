package v1._5_Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import v1._1_Model.Actividad;
import v1._2_DTO.ActividadDTO;
import v1._4_Service.Interface.ActividadService;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/api/v1/actividades")
@CrossOrigin(origins = {"http://localhost:4200"})
public class ActividadController {

    @Autowired
    ActividadService actividadService;

    /**-------------------------------------- LOS DOS ----------------------------------------------------**/

    @GetMapping("/{idActividad}")
    public ResponseEntity<Object> obtenerUnaActividadPorId (@PathVariable Long idActividad) {
        ActividadDTO activityFound = actividadService.searchActividad(idActividad);

        if (activityFound == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(activityFound);
    }


    /**-------------------------------------- OFERTANTES ----------------------------------------------------**/

    @PostMapping("/Ofertantes/Add")
    public ResponseEntity<Object> anadirActividadOfertante (@RequestBody ActividadDTO actividadDTO){

        return actividadService.addActividad(actividadDTO);
    }

    @PutMapping("/Ofertantes/Update")
    public ResponseEntity<Object> actualizarActividad (@RequestBody ActividadDTO act) {
        return actividadService.editActividad(act);
    }

    @DeleteMapping("/Ofertantes/Delete")
    public ResponseEntity<Object> EliminarActividad(@RequestBody ActividadDTO actividadDTO){
        return actividadService.deleteActividad(actividadDTO);
    }

    @GetMapping("/Ofertantes/All/{idOfertante}")
    public ResponseEntity<Object> obtenerActividadesDeUnOfertante (@PathVariable Long idOfertante) {
        List<ActividadDTO> actividadListOfertante = actividadService.obtenerActividadesPorOfertante(idOfertante);
        HashMap<String, String> responseBody = new HashMap<>();

        if (actividadListOfertante == null) {
            responseBody.put("mensaje", "No tiene permiso de entrar en esta página");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
        }

        if (actividadListOfertante.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(actividadListOfertante);
    }



    /**-------------------------------------- CONSUMIDORES ----------------------------------------------------**/

    @GetMapping("/Consumidores/All/{idConsumidor}")
    public ResponseEntity<Object> obtenerTodasLasActividades (@PathVariable Long idConsumidor) {
     List<ActividadDTO> allActivities = actividadService.allActividades(idConsumidor);

        if (allActivities == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso para estar en este apartado");
        }

        if (allActivities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(allActivities);
    }

    @PostMapping("/Consumidores/Add")
    public ResponseEntity<Object> anadirActividadConsumidor (@RequestBody ActividadDTO actividadDTO) {
        return  actividadService.anadirActividadConsumidor(actividadDTO);
    }

    @GetMapping("/Consumidores/All/{wordSearch}/{id}")
    public ResponseEntity<Object> obtenerActividadesPorCadenaDeLetras (@PathVariable String wordSearch, @PathVariable Long id){

        List<Actividad> activityListWordSearch = actividadService.obtenerActividadesPorTitulo(wordSearch, id);

        if (activityListWordSearch == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le permite estar en esta pagina");
        }

        if (activityListWordSearch.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(activityListWordSearch);
    }

}
