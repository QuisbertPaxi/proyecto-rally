package v1._1_Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import v1._2_DTO.ConcursoDTO;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "concurso")
public class Concurso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha_inicio_envio", nullable = false)
    private Date fechaInicioEnvio;

    @Column(name = "fecha_fin_envio", nullable = false)
    private Date fechaFinEnvio;

    @Column(name = "fecha_inicio_votacion", nullable = false)
    private Date fechaInicioVotacion;

    @Column(name = "fecha_fin_votacion", nullable = false)
    private Date fechaFinVotacion;

    @Column(name = "fecha_anuncio", nullable = false)
    private Date fechaAnuncio;

    @Column(name = "nro_fotografias", nullable = false)
    private Integer numeroFotografias;

    @Column(name = "usu_cre", nullable = false)
    private String usuarioCreacion;

    @Column(name = "fec_cre", nullable = false)
    private Timestamp fechaCreacion;

    @Column(name = "usu_mod")
    private String usuarioModificacion;

    @Column(name = "fec_mod")
    private Timestamp fechaModificacion;

    @Column(nullable = false)
    private String estado;

    // Constructor basado en DTO
    public Concurso(ConcursoDTO concursoDTO) {
        this.setDescripcion(concursoDTO.getDescripcion());
        // Usar los métodos helper del DTO para convertir String a Date
        this.setFechaInicioEnvio(concursoDTO.getFechaInicioEnvioAsDate());
        this.setFechaFinEnvio(concursoDTO.getFechaFinEnvioAsDate());
        this.setFechaInicioVotacion(concursoDTO.getFechaInicioVotacionAsDate());
        this.setFechaFinVotacion(concursoDTO.getFechaFinVotacionAsDate());
        this.setFechaAnuncio(concursoDTO.getFechaAnuncioAsDate());
        this.setNumeroFotografias(concursoDTO.getNumeroFotografias());
        this.setUsuarioCreacion(concursoDTO.getUsuCre());
        this.setFechaCreacion(new Timestamp(new Date().getTime()));
        this.setEstado("ACTIVO");
    }

    // Constructor vacío
    public Concurso() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaInicioEnvio() {
        return fechaInicioEnvio;
    }

    public void setFechaInicioEnvio(Date fechaInicioEnvio) {
        this.fechaInicioEnvio = fechaInicioEnvio;
    }

    public Date getFechaFinEnvio() {
        return fechaFinEnvio;
    }

    public void setFechaFinEnvio(Date fechaFinEnvio) {
        this.fechaFinEnvio = fechaFinEnvio;
    }

    public Date getFechaInicioVotacion() {
        return fechaInicioVotacion;
    }

    public void setFechaInicioVotacion(Date fechaInicioVotacion) {
        this.fechaInicioVotacion = fechaInicioVotacion;
    }

    public Date getFechaFinVotacion() {
        return fechaFinVotacion;
    }

    public void setFechaFinVotacion(Date fechaFinVotacion) {
        this.fechaFinVotacion = fechaFinVotacion;
    }

    public Date getFechaAnuncio() {
        return fechaAnuncio;
    }

    public void setFechaAnuncio(Date fechaAnuncio) {
        this.fechaAnuncio = fechaAnuncio;
    }

    public Integer getNumeroFotografias() {
        return numeroFotografias;
    }

    public void setNumeroFotografias(Integer numeroFotografias) {
        this.numeroFotografias = numeroFotografias;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String usuarioCreacion) {
        this.usuarioCreacion = usuarioCreacion;
    }

    public Timestamp getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Timestamp fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public Timestamp getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Timestamp fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Concurso{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicioEnvio=" + fechaInicioEnvio +
                ", fechaFinEnvio=" + fechaFinEnvio +
                ", fechaInicioVotacion=" + fechaInicioVotacion +
                ", fechaFinVotacion=" + fechaFinVotacion +
                ", fechaAnuncio=" + fechaAnuncio +
                ", numeroFotografias=" + numeroFotografias +
                ", usuarioCreacion='" + usuarioCreacion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", usuarioModificacion='" + usuarioModificacion + '\'' +
                ", fechaModificacion=" + fechaModificacion +
                ", estado='" + estado + '\'' +
                '}';
    }
}
