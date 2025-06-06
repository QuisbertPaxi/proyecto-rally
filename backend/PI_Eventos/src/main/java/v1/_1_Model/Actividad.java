package v1._1_Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import v1._2_DTO.ActividadDTO;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "actividades")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 900)
    private String descripcion;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column(nullable = false)
    private String ubicacion;

    @Column
    private Double precio;

    /************ ONE-TO-MANY *************/
    @ManyToOne
    @JoinColumn(name="id_ofertante")
    Usuario ofertante;
    /************ Fin ONE-TO-MANY *************/

    /************ ONE-TO-MANY *************/
    @OneToMany(mappedBy="actividad",
            cascade = CascadeType.ALL,
            orphanRemoval = true)

    private Set<Reserva> reservas = new HashSet<>();

    // Métodos HELPERs
    public void addReserva(Reserva i) {
        this.reservas.add(i);
        i.setActividad(this);
    }
    public void removeReserva(Reserva i) {
        this.reservas.remove(i);
        i.setActividad(null);
    }

    public Actividad() {
    }

    public Actividad (ActividadDTO actividadDTO) {
        this.setDescripcion(actividadDTO.getDescripcion());
        this.setFecha(actividadDTO.getFecha());
        this.setTitulo(actividadDTO.getTitulo());
        this.setUbicacion(actividadDTO.getUbicacion());
        this.setPrecio(actividadDTO.getPrecio());
    }

    /************ Fin ONE-TO-MANY *************/


    public Usuario getOfertante() {
        return ofertante;
    }

    public void setOfertante(Usuario ofertante) {
        this.ofertante = ofertante;
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
        return "Actividad{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", ubicacion='" + ubicacion + '\'' +
                ", precio=" + precio +
                '}';
    }
}
