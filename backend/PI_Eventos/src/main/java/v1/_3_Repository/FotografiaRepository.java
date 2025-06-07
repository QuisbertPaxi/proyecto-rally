package v1._3_Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import v1._1_Model.Fotografia;
import v1._1_Model.Usuario;
import v1._2_DTO.FotografiaDTO;

import java.util.List;
import java.util.Optional;

public interface FotografiaRepository extends JpaRepository<Fotografia, Long> {
    List<Fotografia> findByParticipanteId(Long participanteId); // Método para encontrar todas las fotografias por id del participante
    List<Fotografia> findByTituloContainingIgnoreCase(String titulo); // Encuentra las fotografias que contenga en su titulo la cadena solicitada

    @Query("""
            SELECT new v1._2_DTO.FotografiaDTO(
                f.id, f.titulo, f.descripcion, f.link, f.fechaCreacion, COUNT(v.id)
            )
            FROM Fotografia f
            LEFT JOIN f.votos v
            WHERE f.estado = :estado
            GROUP BY f.id, f.titulo, f.descripcion, f.link, f.fechaCreacion
        """)
    List<FotografiaDTO> findAllDatosPorEstado(String estado);


    @Query("""
                SELECT new v1._2_DTO.FotografiaDTO(
                    f.id, f.titulo, f.descripcion, f.link, f.fechaCreacion, COUNT(v.id)
                )
                FROM Fotografia f
                LEFT JOIN f.votos v
                WHERE f.estado <> 'ELIMINADO'
                GROUP BY f.id, f.titulo, f.descripcion, f.link, f.fechaCreacion
            """)
    List<FotografiaDTO> findAllDatos();

    @Query("""
            SELECT f FROM Fotografia f
            WHERE (LOWER(f.titulo) LIKE LOWER(CONCAT('%', :searchWord, '%'))
                    OR LOWER(f.descripcion) LIKE LOWER(CONCAT('%', :searchWord, '%')))
              AND f.estado = 'APROBADO'
           """)
    List<Fotografia> buscarPorTituloODescripcion(String searchWord);

}
