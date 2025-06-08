package v1._4_Service.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import v1._1_Model.Fotografia;
import v1._1_Model.Voto;
import v1._1_Model.Usuario;
import v1._2_DTO.FotografiaDTO;
import v1._2_DTO.VotoDTO;
import v1._3_Repository.VotoRepository;
import v1._3_Repository.FotografiaRepository;
import v1._3_Repository.UsuarioRepository;
import v1._4_Service.Interface.VotoService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VotoImpl implements VotoService {

    @Autowired
    VotoRepository votoRepository;

    @Autowired
    FotografiaRepository fotografiaRepository;

    @Override
    public Long contarVotos(Long idFotografia) {
        return votoRepository.contarVotosPorFotografia(idFotografia);
    }

    @Override
    public ResponseEntity<Object> votarFotografia(VotoDTO votoDTO) {
        Voto votoIp = votoRepository.findByIp(votoDTO.getIp()).orElse(null);
        Fotografia fotografia = fotografiaRepository.findById(votoDTO.getIdFotografia()).orElse(null);

        if(fotografia == null) {
            return  ResponseEntity.notFound().build();
        }

        if(votoIp != null){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Esta dirección IP ya ha votado");
        }

        Voto voto = new Voto();
        voto.setFotografia(fotografia);
        voto.setIp(votoDTO.getIp());
        voto.setFechaCreacion(new Timestamp(new Date().getTime()));

        Voto votoCreado = votoRepository.save(voto);

        VotoDTO votoDTORespuesta = convertirADTO(votoCreado);

        return ResponseEntity.ok(votoDTORespuesta);
    }

    private VotoDTO convertirADTO(Voto voto) {
        VotoDTO dto = new VotoDTO();
        dto.setIp(voto.getIp());
        dto.setIdFotografia(voto.getFotografia().getId());
        dto.setFechaCreacion(voto.getFechaCreacion());
        return dto;
    }

}
