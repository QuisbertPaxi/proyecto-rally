package v1._4_Service.Interface;

import org.springframework.http.ResponseEntity;
import v1._1_Model.OfertaConsumidor;
import v1._2_DTO.ActividadDTO;
import v1._2_DTO.OfertaConsumidorDTO;


import java.util.List;

public interface OfertaConsumidorService {
    /**--------------------------- CONSUMIDORES ------------------------------------------------**/
    public ResponseEntity<Object> addOferta (OfertaConsumidorDTO ofertaConsumidorDTO); // Añadir una Oferta solo consumidor
    public ResponseEntity<Object> editOferta (OfertaConsumidorDTO ofertaConsumidorDTO); // Actualizar una Oferta solo consumidor
    public ResponseEntity<Object> deleteOferta (OfertaConsumidorDTO ofertaConsumidorDTO); // Eliminar una Oferta solo consumidor
    public List<OfertaConsumidorDTO> obtenerOfertasPorConsumidor(Long consumidorId); // Listar todas las Ofertas de un consumidor


    /**--------------------------- OFERTANTES ------------------------------------------------**/
    public List<OfertaConsumidorDTO> allOfertas (Long id); // Listar todas las Ofertas solo para ofertante
    public ResponseEntity<Object> enviarOfertaOfertante (OfertaConsumidorDTO ofertaConsumidorDTO); // Desactivar oferta y enviar datos solo para ofertante


    /**--------------------------- LOS DOS -------------------------------------------------------**/
    public OfertaConsumidor searchOferta (Long id); // Listar 1 oferta dado su id
}
