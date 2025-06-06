package v1._2_DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

public class FotografiaDTO {

    private Long id;
    private Long idParticipante;
    private String link;
    private String descripcion;
    private String titulo;

    private String usuCre;
    private Timestamp fecCre;

    private Timestamp fecMod;

    private String usuMod;

    private Long cantidadVotos;


    public FotografiaDTO() {
    }

    public FotografiaDTO(Long id, String titulo,String descripcion, String link, Timestamp usuCre, Long cantidadVotos) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.link = link;
        this.fecCre = usuCre;
        this.cantidadVotos = cantidadVotos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(Long idParticipante) {
        this.idParticipante = idParticipante;
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

    public Timestamp getFecCre() {
        return fecCre;
    }

    public void setFecCre(Timestamp fecCre) {
        this.fecCre = fecCre;
    }

    public Timestamp getFecMod() {
        return fecMod;
    }

    public void setFecMod(Timestamp fecMod) {
        this.fecMod = fecMod;
    }

    public String getUsuCre() {
        return usuCre;
    }

    public void setUsuCre(String usuCre) {
        this.usuCre = usuCre;
    }

    public String getUsuMod() {
        return usuMod;
    }

    public void setUsuMod(String usuMod) {
        this.usuMod = usuMod;
    }

    public Long getCantidadVotos() {
        return cantidadVotos;
    }

    public void setCantidadVotos(Long cantidadVotos) {
        this.cantidadVotos = cantidadVotos;
    }

    @Override
    public String toString() {
        return "FotografiaDTO{" +
                "id=" + id +
                ", idParticipante=" + idParticipante +
                ", descripcion='" + descripcion + '\'' +
                ", titulo='" + titulo + '\'' +
                ", link='" + link + '\'' +
                ", fecCre='" + fecCre + '\'' +
                ", fecMod='" + fecMod + '\'' +
                '}';
    }
}
