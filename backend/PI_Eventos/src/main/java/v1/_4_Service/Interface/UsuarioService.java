package v1._4_Service.Interface;

import v1.Security.JWT.TokenResponse;
import v1._1_Model.Usuario;
import v1._2_DTO.InicioSesionDTO;
import v1._2_DTO.UsuarioDTO;

import java.util.Optional;

public interface UsuarioService {
    public TokenResponse registrarUsuario(UsuarioDTO usuario); // crear usuario
    public Optional<UsuarioDTO> reedUsuario(final Long id); // leer datos de un usuario
    public Usuario actualizarUsuario(final Long id, UsuarioDTO usuarioDTO); // actualizar usuario
    public boolean borrarUsuario(final Long id); // borrar usuario
    public TokenResponse iniciarSesion (InicioSesionDTO sesionDTO); // iniciar sesion con usuario

}
