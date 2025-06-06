package v1._1_Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import v1._2_DTO.OfertaConsumidorDTO;

import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "oferta_de_consumidores")
public class OfertaConsumidor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitada;

    @Column(nullable = false)
    private String ubicacion;

    @Column(nullable = false)
    private boolean estado; // true significa que todavia nadie lo ha seleccionado

    /************ ONE-TO-MANY *************/
    @ManyToOne
    @JoinColumn(name = "id_consumidor")
    private Usuario consu;

    /************ Fin ONE-TO-MANY *************/


    public OfertaConsumidor() {
    }

    public OfertaConsumidor(OfertaConsumidorDTO o) {
        this.setDescripcion(o.getDescripcion());
        this.setFechaSolicitada(o.getFechaSolicitada());
        this.setTitulo(o.getTitulo());
        this.setUbicacion(o.getUbicacion());
        this.setEstado(true);
    }

    public Usuario getConsu() {
        return consu;
    }

    public void setConsu(Usuario consu) {
        this.consu = consu;
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
}
