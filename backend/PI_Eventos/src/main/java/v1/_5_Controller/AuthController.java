package v1._5_Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import v1.Security.JWT.TokenResponse;
import v1._1_Model.Usuario;
import v1._2_DTO.InicioSesionDTO;
import v1._2_DTO.UsuarioDTO;
import v1._4_Service.Interface.UsuarioService;

import java.util.HashMap;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<Object> InsertarUsuarioSinSeguridad (@RequestBody UsuarioDTO usuarioDTO){

       /* Usuario user =usuarioService.registrarUsuario(usuarioDTO);

        if(user == null){

            HashMap<String, String> responseBody = new HashMap<>();
            responseBody.put("mensaje", "El usuario ya existe");
            return ResponseEntity.badRequest().body(responseBody);

        }

        var response = new HashMap<String, Object>();
        response.put("user",user);

        return ResponseEntity.ok(response);*/
        TokenResponse tokenResponse = usuarioService.registrarUsuario(usuarioDTO);

        if (tokenResponse == null) {
            return ResponseEntity.badRequest().body("No se ha podido ingresar el usuario");
        }

        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> IniciarSesionSinSeguridad (@RequestBody InicioSesionDTO sesionDTO) {
        /*Usuario usuario = usuarioService.iniciarSesion(sesionDTO);
        if (usuario == null) { return ResponseEntity.status(400).body("Email o contraseña incorrecta");}
        var response = new HashMap<String, Object>();
        response.put("user",usuario);

        return ResponseEntity.ok(response);*/
        return ResponseEntity.ok(usuarioService.iniciarSesion(sesionDTO));
    }
}
