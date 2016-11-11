package sisobeem.services.edificeServices;

import jadex.bridge.IComponentIdentifier;

/**
 * Servicio para la actualizacion de las creencias del agente edificio.
 * @author Erley
 *
 */
public interface ISetBeliefEdificeService {
   
	/**
	 * Servicio para modificar la creencia Zone en el agente edificio.
	 * @param zone
	 */
	public void setZone(IComponentIdentifier zone);
	
	
	/**
	 * Servicio para modificar la creencia intensidadSismo en el agente edificio
	 * @param intensidad
	 */
	public void setSismo(double intensidad);
}
