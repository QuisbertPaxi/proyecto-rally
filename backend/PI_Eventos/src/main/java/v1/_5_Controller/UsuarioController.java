package v1._5_Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v1._1_Model.Usuario;
import v1._2_DTO.FotografiaDTO;
import v1._2_DTO.UsuarioDTO;
import v1._4_Service.Interface.UsuarioService;

import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/usuarios")
@CrossOrigin(origins = {"http://localhost:4200"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);


    @GetMapping("/{id}")
    public ResponseEntity<Object> obtenerDatosUser (@PathVariable Long id){

        Optional<UsuarioDTO> usuarioOptional = usuarioService.reedUsuario(id);

        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }
            return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Object> ActualizarDatosUser (@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO){

        Usuario user = usuarioService.actualizarUsuario(id, usuarioDTO);
        HashMap<String, String> responseBody = new HashMap<>();

        if ( user == null) {
            responseBody.put("mensaje", "No se ha podido actualizar el usuario");
            return ResponseEntity.badRequest().body(responseBody);
        }

        responseBody.put("mensaje", "Se ha actualizado con exito");
        return ResponseEntity.ok(responseBody);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> EliminarUsuario (@PathVariable Long id){
        HashMap<String, String> responseBody = new HashMap<>();
        if (usuarioService.borrarUsuario(id)){
            responseBody.put("mensaje", "Se ha eliminado con exito al usuario");
            return ResponseEntity.ok(responseBody);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/All/{id}")
    public ResponseEntity<Object> obtenerAllUser (@PathVariable Long id){

        List<UsuarioDTO> usuarios = usuarioService.getAllUser(id);

        if (usuarios == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tiene permiso para estar en este apartado");
        }

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

}
