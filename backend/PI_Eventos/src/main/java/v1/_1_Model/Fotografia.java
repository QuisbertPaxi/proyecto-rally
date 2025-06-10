package v1._1_Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import v1._2_DTO.FotografiaDTO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "fotografias")
public class Fotografia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "usu_aprobacion", nullable = false)
    private String usuarioAprobacion;

    @Column(name = "fec_aprobacion", nullable = false)
    private Timestamp fechaAprobacion;

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

    @ManyToOne
    @JoinColumn(name="id_participante")
    Usuario participante;

    @OneToMany(mappedBy = "fotografia", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Voto> votos = new HashSet<>();


    public Fotografia(FotografiaDTO fotografiaDTO) {
        this.setTitulo(fotografiaDTO.getTitulo());
        this.setDescripcion(fotografiaDTO.getDescripcion());
        this.setLink(fotografiaDTO.getLink());
        this.setUsuarioCreacion(fotografiaDTO.getUsuCre());
        this.setFechaCreacion(new Timestamp(new Date().getTime()));
        this.setEstado("PENDIENTE");
    }

    public Fotografia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuarioAprobacion() {
        return usuarioAprobacion;
    }

    public void setUsuarioAprobacion(String usuarioAprobacion) {
        this.usuarioAprobacion = usuarioAprobacion;
    }

    public Timestamp getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Timestamp fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
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

    public Usuario getParticipante() {
        return participante;
    }

    public void setParticipante(Usuario participante) {
        this.participante = participante;
    }

    public Set<Voto> getVotos() {
        return votos;
    }

    public void setVotos(Set<Voto> votos) {
        this.votos = votos;
    }

    @Override
    public String toString() {
        return "Fotografia{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
