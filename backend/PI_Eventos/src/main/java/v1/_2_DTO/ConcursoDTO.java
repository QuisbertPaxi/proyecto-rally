package v1._2_DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

public class ConcursoDTO {
    private Long id;
    private String descripcion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioEnvio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinEnvio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioVotacion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinVotacion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fechaAnuncio;

    private Integer numeroFotografias;
    private String usuCre;
    private Timestamp fecCre;
    private String usuMod;
    private Timestamp fecMod;
    private String estado;

    // Constructores
    public ConcursoDTO() {
    }

    public ConcursoDTO(Long id, String descripcion, Date fechaInicioEnvio, Date fechaFinEnvio,
                       Date fechaInicioVotacion, Date fechaFinVotacion, Date fechaAnuncio,
                       Integer numeroFotografias, String usuCre) {
        this.id = id;
        this.descripcion = descripcion;
        this.fechaInicioEnvio = fechaInicioEnvio;
        this.fechaFinEnvio = fechaFinEnvio;
        this.fechaInicioVotacion = fechaInicioVotacion;
        this.fechaFinVotacion = fechaFinVotacion;
        this.fechaAnuncio = fechaAnuncio;
        this.numeroFotografias = numeroFotografias;
        this.usuCre = usuCre;
        this.fecCre = new Timestamp(new Date().getTime());
        this.estado = "ACTIVO";
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

    public String getUsuCre() {
        return usuCre;
    }

    public void setUsuCre(String usuCre) {
        this.usuCre = usuCre;
    }

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public String getUsuMod() {
        return usuMod;
    }

    public void setUsuMod(String usuMod) {
        this.usuMod = usuMod;
    }

    public Timestamp getFecMod() {
        return fecMod;
    }

    public void setFecMod(Timestamp fecMod) {
        this.fecMod = fecMod;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ConcursoDTO{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicioEnvio=" + fechaInicioEnvio +
                ", fechaFinEnvio=" + fechaFinEnvio +
                ", fechaInicioVotacion=" + fechaInicioVotacion +
                ", fechaFinVotacion=" + fechaFinVotacion +
                ", fechaAnuncio=" + fechaAnuncio +
                ", numeroFotografias=" + numeroFotografias +
                ", estado='" + estado + '\'' +
                '}';
    }
}