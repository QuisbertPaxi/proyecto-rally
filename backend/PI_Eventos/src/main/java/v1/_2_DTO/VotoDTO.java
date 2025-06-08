package v1._2_DTO;

import java.sql.Timestamp;

public class VotoDTO {

    private String ip;
    private Long idFotografia;
    private Timestamp fechaCreacion;


    public VotoDTO() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Long getIdFotografia() {
        return idFotografia;
    }

    public void setIdFotografia(Long idFotografia) {
        this.idFotografia = idFotografia;
    }
}
