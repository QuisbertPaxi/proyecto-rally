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
                concursoDTO.getFechaAnuncio() == null) {

            return ResponseEntity.badRequest().body("No esta bien los datos");
        }
        concurso.setDescripcion(concursoDTO.getDescripcion());
        concurso.setFechaInicioEnvio(concursoDTO.getFechaInicioEnvio());
        concurso.setFechaFinEnvio(concursoDTO.getFechaFinEnvio());
        concurso.setFechaInicioVotacion(concursoDTO.getFechaInicioVotacion());
        concurso.setFechaFinVotacion(concursoDTO.getFechaFinVotacion());
        concurso.setFechaAnuncio(concursoDTO.getFechaAnuncio());
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
        dto.setFechaInicioEnvio(concurso.getFechaInicioEnvio());
        dto.setFechaFinEnvio(concurso.getFechaFinEnvio());
        dto.setFechaInicioVotacion(concurso.getFechaInicioVotacion());
        dto.setFechaFinVotacion(concurso.getFechaFinVotacion());
        dto.setFechaAnuncio(concurso.getFechaAnuncio());
        dto.setUsuCre(concurso.getUsuarioCreacion());
        dto.setFecCre(concurso.getFechaCreacion());
        dto.setUsuMod(concurso.getUsuarioModificacion());
        return dto;
    }

    @Override
    public ConcursoDTO getConcurso() {
        return this.convertirADTO(concursoRepository.findFirstBy());
    }
}
