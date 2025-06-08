package v1._4_Service.Interface;

import org.springframework.http.ResponseEntity;
import v1._1_Model.Fotografia;
import v1._2_DTO.FotografiaDTO;
import v1._2_DTO.VotoDTO;

import java.util.List;

public interface VotoService {

    public Long contarVotos(Long idFotografia);

    public ResponseEntity<Object> votarFotografia (VotoDTO votoDTO); // Para votar por una fotografia

}
