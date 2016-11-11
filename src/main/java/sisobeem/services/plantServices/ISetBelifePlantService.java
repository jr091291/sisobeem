package sisobeem.services.plantServices;

import jadex.bridge.IComponentIdentifier;

/**
 * Servicio para actualizar las creencias del agente Piso
 * @author Erley
 *
 */
public interface ISetBelifePlantService {
   
	/**
	 * Método para modificar la creencia cidEdificio en el agente Piso
	 * @param cidEdifice
	 */
	public void setEdifice(IComponentIdentifier cidEdifice);
	
	/**
	 * Método para modificar el cidZone del agente Piso
	 * @param cidZone
	 */
	public void setZone(IComponentIdentifier cidZone);
	
	/**
	 * Servicio para modificar la creencia intensidadSismo en el agente edificio
	 * @param intensidad
	 */
	public void setSismo(double intensidad);
}
