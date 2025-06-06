package v1._2_DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OfertaConsumidorDTO {
    private Long id;
    private String titulo;
    private String descripcion;
    @DateTimeFormat(pattern = "dd/MM/yyyy") // Formato para solicitudes HTTP (por ejemplo, formularios web)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy",  timezone = "UTC") // Formato para serialización/deserialización JSON
    private Date fechaSolicitada;
    private String ubicacion;
    private boolean estado;
    private Long idConsumidor;
    private Long idOfertante;

    public OfertaConsumidorDTO() {}
    public OfertaConsumidorDTO(Long id, String titulo, String descripcion, Date fechaSolicitada, String ubicacion, boolean estado) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaSolicitada = fechaSolicitada;
        this.ubicacion = ubicacion;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaSolicitada() {
        return fechaSolicitada;
    }

    public void setFechaSolicitada(Date fechaSolicitada) {
        this.fechaSolicitada = fechaSolicitada;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Long getIdConsumidor() {
        return idConsumidor;
    }

    public void setIdConsumidor(Long idConsumidor) {
        this.idConsumidor = idConsumidor;
    }

    public Long getIdOfertante() {
        return idOfertante;
    }

    public void setIdOfertante(Long idOfertante) {
        this.idOfertante = idOfertante;
    }
}
