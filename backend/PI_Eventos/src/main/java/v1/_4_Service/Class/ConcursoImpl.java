package v1._4_Service.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import v1._1_Model.Concurso;
import v1._1_Model.Usuario;
import v1._2_DTO.ConcursoDTO;
import v1._3_Repository.ConcursoRepository;
import v1._3_Repository.UsuarioRepository;
import v1._4_Service.Interface.ConcursoService;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class ConcursoImpl implements ConcursoService {

    @Autowired
    ConcursoRepository concursoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    String of = "admin";

    @Override
    public ResponseEntity<Object> editConcurso(Long idAdmin, ConcursoDTO concursoDTO) {
        Concurso concurso = concursoRepository.findFirstBy();
        Usuario user = usuarioRepository.findById(idAdmin).orElse(null);

        if (!user.getRole().equals(of)|| user == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No se le esta permitido entrar en esta ruta");
        }

        if( concurso == null) {
            return  ResponseEntity.notFound().build();
        }

        if (concursoDTO.getDescripcion() == null ||
                concursoDTO.getFechaInicioEnvio() == null  ||
                concursoDTO.getFechaFinEnvio() == null ||
                concursoDTO.getFechaInicioVotacion() == null ||
                concursoDTO.getFechaFinVotacion() == null ||
                concursoDTO.getFechaAnuncio() == null ||
                concursoDTO.getNumeroFotografias() == null) {

            return ResponseEntity.badRequest().body("No esta bien los datos");
        }
        concurso.setDescripcion(concursoDTO.getDescripcion());
        concurso.setFechaInicioEnvio(concursoDTO.getFechaInicioEnvioAsDate());
        concurso.setFechaFinEnvio(concursoDTO.getFechaFinEnvioAsDate());
        concurso.setFechaInicioVotacion(concursoDTO.getFechaInicioVotacionAsDate());
        concurso.setFechaFinVotacion(concursoDTO.getFechaFinVotacionAsDate());
        concurso.setFechaAnuncio(concursoDTO.getFechaAnuncioAsDate());
        concurso.setNumeroFotografias(concursoDTO.getNumeroFotografias());
        concurso.setUsuarioModificacion(user.getUserName());
        concurso.setFechaModificacion(new Timestamp(new Date().getTime()));

        concursoRepository.save(concurso);

        ConcursoDTO updatedConcursoDTO = convertirADTO(concurso);

        return ResponseEntity.ok(updatedConcursoDTO);
    }



    private ConcursoDTO convertirADTO(Concurso concurso) {
        ConcursoDTO dto = new ConcursoDTO();
        dto.setId(concurso.getId());
        dto.setDescripcion(concurso.getDescripcion());

        // Usar java.sql.Date que tiene el método toString() que devuelve formato yyyy-MM-dd
        dto.setFechaInicioEnvio(concurso.getFechaInicioEnvio() != null ?
                new java.sql.Date(concurso.getFechaInicioEnvio().getTime()).toString() : null);
        dto.setFechaFinEnvio(concurso.getFechaFinEnvio() != null ?
                new java.sql.Date(concurso.getFechaFinEnvio().getTime()).toString() : null);
        dto.setFechaInicioVotacion(concurso.getFechaInicioVotacion() != null ?
                new java.sql.Date(concurso.getFechaInicioVotacion().getTime()).toString() : null);
        dto.setFechaFinVotacion(concurso.getFechaFinVotacion() != null ?
                new java.sql.Date(concurso.getFechaFinVotacion().getTime()).toString() : null);
        dto.setFechaAnuncio(concurso.getFechaAnuncio() != null ?
                new java.sql.Date(concurso.getFechaAnuncio().getTime()).toString() : null);

        dto.setNumeroFotografias(concurso.getNumeroFotografias());
        dto.setUsuCre(concurso.getUsuarioCreacion());
        dto.setFecCre(concurso.getFechaCreacion());
        dto.setUsuMod(concurso.getUsuarioModificacion());
        dto.setFecMod(concurso.getFechaModificacion());
        dto.setEstado(concurso.getEstado());

        return dto;
    }

    @Override
    public ConcursoDTO getConcurso() {
        return this.convertirADTO(concursoRepository.findFirstBy());
    }
}
