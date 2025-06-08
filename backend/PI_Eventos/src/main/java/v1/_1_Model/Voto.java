package v1._1_Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "votos")
public class Voto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ip;

    @ManyToOne
    @JoinColumn(name = "id_fotografia", nullable = false)
    @JsonIgnore
    private Fotografia fotografia;

    @Column(name = "fec_cre", nullable = false)
    private Timestamp fechaCreacion;

    public Voto() {
    }

    public Voto(String ip, Fotografia fotografia) {
        this.ip = ip;
        this.fotografia = fotografia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Fotografia getFotografia() {
        return fotografia;
    }

    public void setFotografia(Fotografia fotografia) {
        this.fotografia = fotografia;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}
