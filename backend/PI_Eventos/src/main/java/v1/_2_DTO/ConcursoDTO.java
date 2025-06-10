package v1._2_DTO;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;

public class ConcursoDTO {
    private Long id;
    private String descripcion;

    private String fechaInicioEnvio;

    private String fechaFinEnvio;

    private String fechaInicioVotacion;

    private String fechaFinVotacion;

    private String fechaAnuncio;

    private Integer numeroFotografias;
    private String usuCre;
    private Timestamp fecCre;
    private String usuMod;
    private Timestamp fecMod;
    private String estado;

    // Constructores
    public ConcursoDTO() {
    }

    public ConcursoDTO(Long id, String descripcion, String fechaInicioEnvio, String fechaFinEnvio,
                       String fechaInicioVotacion, String fechaFinVotacion, String fechaAnuncio,
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

    public String getFechaInicioEnvio() { return fechaInicioEnvio; }
    public void setFechaInicioEnvio(String fechaInicioEnvio) { this.fechaInicioEnvio = fechaInicioEnvio; }

    public String getFechaFinEnvio() { return fechaFinEnvio; }
    public void setFechaFinEnvio(String fechaFinEnvio) { this.fechaFinEnvio = fechaFinEnvio; }

    public String getFechaInicioVotacion() { return fechaInicioVotacion; }
    public void setFechaInicioVotacion(String fechaInicioVotacion) { this.fechaInicioVotacion = fechaInicioVotacion; }

    public String getFechaFinVotacion() { return fechaFinVotacion; }
    public void setFechaFinVotacion(String fechaFinVotacion) { this.fechaFinVotacion = fechaFinVotacion; }

    public String getFechaAnuncio() { return fechaAnuncio; }
    public void setFechaAnuncio(String fechaAnuncio) { this.fechaAnuncio = fechaAnuncio; }

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

    // Métodos helper para convertir String a LocalDate cuando necesites usarlos en la lógica
    public LocalDate getFechaInicioEnvioAsLocalDate() {
        return fechaInicioEnvio != null ? LocalDate.parse(fechaInicioEnvio) : null;
    }

    public LocalDate getFechaFinEnvioAsLocalDate() {
        return fechaFinEnvio != null ? LocalDate.parse(fechaFinEnvio) : null;
    }

    public LocalDate getFechaInicioVotacionAsLocalDate() {
        return fechaInicioVotacion != null ? LocalDate.parse(fechaInicioVotacion) : null;
    }

    public LocalDate getFechaFinVotacionAsLocalDate() {
        return fechaFinVotacion != null ? LocalDate.parse(fechaFinVotacion) : null;
    }

    public LocalDate getFechaAnuncioAsLocalDate() {
        return fechaAnuncio != null ? LocalDate.parse(fechaAnuncio) : null;
    }

    // Métodos helper para convertir String a Date (para compatibilidad con el modelo)
    public Date getFechaInicioEnvioAsDate() {
        return fechaInicioEnvio != null ? java.sql.Date.valueOf(LocalDate.parse(fechaInicioEnvio)) : null;
    }

    public Date getFechaFinEnvioAsDate() {
        return fechaFinEnvio != null ? java.sql.Date.valueOf(LocalDate.parse(fechaFinEnvio)) : null;
    }

    public Date getFechaInicioVotacionAsDate() {
        return fechaInicioVotacion != null ? java.sql.Date.valueOf(LocalDate.parse(fechaInicioVotacion)) : null;
    }

    public Date getFechaFinVotacionAsDate() {
        return fechaFinVotacion != null ? java.sql.Date.valueOf(LocalDate.parse(fechaFinVotacion)) : null;
    }

    public Date getFechaAnuncioAsDate() {
        return fechaAnuncio != null ? java.sql.Date.valueOf(LocalDate.parse(fechaAnuncio)) : null;
    }

    @Override
    public String toString() {
        return "ConcursoDTO{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", fechaInicioEnvio='" + fechaInicioEnvio + '\'' +
                ", fechaFinEnvio='" + fechaFinEnvio + '\'' +
                ", fechaInicioVotacion='" + fechaInicioVotacion + '\'' +
                ", fechaFinVotacion='" + fechaFinVotacion + '\'' +
                ", fechaAnuncio='" + fechaAnuncio + '\'' +
                ", numeroFotografias=" + numeroFotografias +
                ", estado='" + estado + '\'' +
                '}';
    }
}