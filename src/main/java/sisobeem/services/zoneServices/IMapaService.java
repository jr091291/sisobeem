package sisobeem.services.zoneServices;

import jadex.bridge.IComponentIdentifier;
import sisobeem.artifacts.Coordenada;

/**
 * Servicios relacionados con el Mapa 
 * @author Erley
 *
 */
public interface IMapaService {
	
   /**
    * Servicio para cambiar la piscion del agente en el mapa del ambiente ZONE	
    *  @param nueva
    *   @param cid
    */
   public boolean changePosition(Coordenada nueva, IComponentIdentifier cid, String tipo);
   
   
   /**
    * Servicio para generar el daño por la caida de un edificio
    * @param cidEdifice
    */
   public void derrumbarEdifice(IComponentIdentifier cidEdifice);
   
   
   /**
    * Servicio para solicitar una ruta 
    * @param agent
    * @param destino
    */
   public void getRuta(IComponentIdentifier agent, Coordenada destino);
}
