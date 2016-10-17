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
   public boolean changePosition(Coordenada nueva, IComponentIdentifier cid);
}
