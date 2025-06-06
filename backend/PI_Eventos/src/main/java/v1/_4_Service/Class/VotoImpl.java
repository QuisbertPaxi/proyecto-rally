package v1._4_Service.Class;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import v1._1_Model.Fotografia;
import v1._1_Model.Voto;
import v1._1_Model.Usuario;
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

    @Override
    public Long contarVotos(Long idFotografia) {
        return votoRepository.contarVotosPorFotografia(idFotografia);
    }

}
