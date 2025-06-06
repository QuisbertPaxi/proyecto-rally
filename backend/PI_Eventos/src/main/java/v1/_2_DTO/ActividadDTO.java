package v1._2_DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ActividadDTO {

    private String titulo;
    private String descripcion;
    @DateTimeFormat(pattern = "dd/MM/yyyy") // Formato para solicitudes HTTP (por ejemplo, formularios web)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy",  timezone = "UTC") // Formato para serialización/deserialización JSON
    private Date fecha;
    private String ubicacion;
    private Double precio;
    private Long idOfertante;
    private Long id;
    private  Long idConsumidor;
    @DateTimeFormat(pattern = "dd/MM/yyyy") // Formato para solicitudes HTTP (por ejemplo, formularios web)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy",  timezone = "UTC") // Formato para serialización/deserialización JSON
    private Date fechaReserva;

    // a.titulo, a.descripcion, a.fecha, a.ubicacion, a.precio, a.idOfertante, a.id, a.idConsumidor, a.fechaReserva

    public ActividadDTO(String titulo, String descripcion, Date fecha, String ubicacion, Double precio, Long idOfertante, Long id) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ubicacion = ubicacion;
        this.precio = precio;
        this.idOfertante = idOfertante;
        this.id = id;
    }

    public ActividadDTO() {
    }

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

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Long getIdOfertante() {
        return idOfertante;
    }

    public void setIdOfertante(Long idOfertante) {
        this.idOfertante = idOfertante;
    }

    public Long getIdConsumidor() {
        return idConsumidor;
    }

    public void setIdConsumidor(Long idConsumidor) {
        this.idConsumidor = idConsumidor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ActividadDTO{" +
                "titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", ubicacion='" + ubicacion + '\'' +
                ", precio=" + precio +
                ", idOfertante=" + idOfertante +
                ", id=" + id +
                ", idConsumidor=" + idConsumidor +
                ", fechaReserva=" + fechaReserva +
                '}';
    }
}
