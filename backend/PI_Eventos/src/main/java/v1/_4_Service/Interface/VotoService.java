package v1._4_Service.Interface;

import org.springframework.http.ResponseEntity;
import v1._2_DTO.VotoDTO;

public interface VotoService {

    public Long contarVotos(Long idFotografia);

    public ResponseEntity<Object> votarFotografia (VotoDTO votoDTO);

}
