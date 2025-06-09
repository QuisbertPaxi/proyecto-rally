package v1._4_Service.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import v1.Security.JWT.JWTService;
import v1.Security.JWT.TokenResponse;
import v1._1_Model.Usuario;
import v1._2_DTO.InicioSesionDTO;
import v1._2_DTO.UsuarioDTO;
import v1._3_Repository.UsuarioRepository;
import v1._4_Service.Interface.UsuarioService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioImpl implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JWTService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    String roleAdmin = "admin";

    @Override
    public TokenResponse registrarUsuario(UsuarioDTO usuario) {

        if (usuarioRepository.findByEmail(usuario.getEmail()) != null ||
                usuarioRepository.findByUserName(usuario.getUserName()).isPresent() ){
            return null;
        }

        // Codifica la contraseña antes de almacenarla
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        Usuario user = new Usuario(usuario);

        // Guardar el nuevo usuario en la base de datos
        usuarioRepository.save(user);

        return TokenResponse.builder()
                .token(jwtService.getToken(user))
                .build();

    }

    @Override
    public TokenResponse iniciarSesion(InicioSesionDTO usuario) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUserName(), usuario.getPassword()));
        Usuario user = usuarioRepository.findByUserName(usuario.getUserName()).orElseThrow();
        String token = jwtService.getToken(user);

        return new TokenResponse(token);
    }

    @Override
    public Optional<UsuarioDTO> reedUsuario(Long id) {
        Optional<Usuario> a = null;

        if (id != null) {
            a = usuarioRepository.findById(id);
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);

            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();
                UsuarioDTO usuarioDTO = new UsuarioDTO();
                usuarioDTO.setId(usuario.getId());
                usuarioDTO.setUserName(usuario.getUserName());
                usuarioDTO.setEmail(usuario.getEmail());
                usuarioDTO.setNombre(usuario.getNombre());
                usuarioDTO.setApellidos(usuario.getApellidos());
                usuarioDTO.setRole(usuario.getRole());

                return Optional.of(usuarioDTO);
            }

        }

        return  Optional.empty();
    }

    @Override
    public Usuario actualizarUsuario(final Long id, UsuarioDTO usuarioDTO) {

        Usuario d = usuarioRepository.findById(id).orElse(null);


        if (d != null ) {

            String pass = usuarioDTO.getPassword();
            if (pass != null && !pass.isBlank()) {
                d.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
            }

            d.setEmail(usuarioDTO.getEmail());
            d.setUserName(usuarioDTO.getUserName());
            d.setNombre(usuarioDTO.getNombre());
            d.setApellidos(usuarioDTO.getApellidos());

            if (esUnicoUsuario(d)) {
                try {
                    usuarioRepository.save(d);
                    return d;
                } catch (DataIntegrityViolationException e) {
                   return null;
                }
            }
        }
        return null;
    }

    @Override
    public boolean borrarUsuario(Long id) {

        Usuario u = usuarioRepository.findById(id).orElse(null);

        if (u == null) {
            return false;
        }

        u.setEstado("ELIMINADO");
        u.setFechaModificacion(new Timestamp(new Date().getTime()));
        usuarioRepository.save(u);
        return true;
    }

    @Override
    public List<UsuarioDTO> getAllUser(Long id){
        Usuario admin = usuarioRepository.findById(id).orElse(null);

        if (admin == null || !admin.getRole().equals(roleAdmin)) {
            return null;
        }

        return usuarioRepository.findByEstadoNot();
    }

    private boolean esUnicoUsuario(Usuario u) {
        long count = usuarioRepository.countByNombreOrEmailExcludingId(u.getUserName(), u.getEmail(), u.getId());
        return count == 0;
    }
}
