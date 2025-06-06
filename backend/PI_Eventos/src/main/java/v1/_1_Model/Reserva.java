package v1._1_Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fechaReserva;

    /************ ONE-TO-MANY *************/
    @ManyToOne
    @JoinColumn(name = "actividad_id")
    private Actividad actividad;
    /************ Fin ONE-TO-MANY *************/

    /************ ONE-TO-MANY *************/
    @ManyToOne
    @JoinColumn(name = "cosumidor_id")
    private Usuario consumidor;
    /************ Fin ONE-TO-MANY *************/

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Usuario getConsumidor() {
        return consumidor;
    }

    public void setConsumidor(Usuario consumidor) {
        this.consumidor = consumidor;
    }

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
}
