package v1._4_Service.Interface;

import org.springframework.http.ResponseEntity;
import v1._2_DTO.ConcursoDTO;

public interface ConcursoService {

    /**--------------------------- OFERTANTES ------------------------------------------------**/
    public ResponseEntity<Object> editConcurso (Long idAdmin, ConcursoDTO concursoDTO); // Actualizar un concurso
    public ConcursoDTO getConcurso();

}
