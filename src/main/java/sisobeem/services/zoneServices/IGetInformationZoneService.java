package sisobeem.services.zoneServices;

import java.util.ArrayList;

import jadex.bridge.IComponentIdentifier;
import sisobeem.artifacts.Coordenada;

/**
 * Servicios para obtener informacion del Ambiente Zone 
 * @author Erley
 *
 */
public interface IGetInformationZoneService {

	
	/**
	 * Servicio para obetner los 5 puntos seguros del mapa
	 * @return
	 */
	public ArrayList<Coordenada> getPuntosSeguros();
	
	
	/**
	 * Servicio para obtener los puntos con mas daño en el mapa
	 * @return
	 */
	public ArrayList<Coordenada> getPuntosInseguros();
	
	public ArrayList<IComponentIdentifier> getAgentsHeridos(IComponentIdentifier agent);
}
