package v1._3_Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import v1._1_Model.Usuario;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Optional<Usuario> findByUserName(String name);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE (u.nombre = :nombre OR u.email = :email) AND u.id <> :id")
    long countByNombreOrEmailExcludingId(@Param("nombre") String nombre, @Param("email") String email, @Param("id") Long id);

    Optional<Usuario> findByIdAndEstadoNot(Long id, String estado);
}
