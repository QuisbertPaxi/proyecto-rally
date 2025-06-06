package v1._2_DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import v1._1_Model.Actividad;

import java.util.Date;

public class ReservaDTO {

    private Long id;
    @DateTimeFormat(pattern = "dd/MM/yyyy") // Formato para solicitudes HTTP (por ejemplo, formularios web)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy",  timezone = "UTC") // Formato para serialización/deserialización JSON
    private Date fechaReserva;
    private Long actividadId;
    private Long consumidorId;
    private String titulo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Long getActividadId() {
        return actividadId;
    }

    public void setActividadId(Long actividadId) {
        this.actividadId = actividadId;
    }

    public Long getConsumidorId() {
        return consumidorId;
    }

    public void setConsumidorId(Long consumidorId) {
        this.consumidorId = consumidorId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
