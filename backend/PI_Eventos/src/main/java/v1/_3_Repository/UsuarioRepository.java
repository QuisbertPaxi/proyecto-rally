package v1._3_Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import v1._1_Model.Usuario;
import v1._2_DTO.UsuarioDTO;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email);
    Optional<Usuario> findByUserName(String name);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE (u.nombre = :nombre OR u.email = :email) AND u.id <> :id")
    long countByNombreOrEmailExcludingId(@Param("nombre") String nombre, @Param("email") String email, @Param("id") Long id);

    Optional<Usuario> findByIdAndEstadoNot(Long id, String estado);

    @Query("""
            SELECT new v1._2_DTO.UsuarioDTO(
                u.id, u.userName, u.email, u.nombre, u.apellidos, u.role
            )
            FROM Usuario u
            WHERE u.estado <> 'ELIMINADO'
        """)
    List<UsuarioDTO> findByEstadoNot();

      @Modifying
      @Transactional
    @Query("DELETE FROM Usuario u WHERE u.id = :usuarioId")
    void eliminarUsuarioPorId(Long usuarioId);

}
