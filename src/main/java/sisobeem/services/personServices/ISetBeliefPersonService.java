package sisobeem.services.personServices;

import java.util.ArrayList;

import jadex.bridge.IComponentIdentifier;
import sisobeem.artifacts.Coordenada;

/**
 * Servicio de modificacion de creencias de los agentes persona
 * @author Erley
 *
 */
public interface ISetBeliefPersonService {
    
	/**
	 * Servicio para modificar la creencia Edificio en el agente persona
	 * @param cidEdificio
	 */
	public void setEdifice(IComponentIdentifier cidEdificio );
	
	/**
	 * Servicio para modificar la creencia Piso en el agente persona
	 * @param cidPlant
	 */
	public void setPlant(IComponentIdentifier cidPlant);
	
	/**
	 * Servicio para modificar la creencia Zone en el agente
	 * @param zone
	 */
	public void setZone(IComponentIdentifier zone);
	
	/**
	 * Servicio para modificar la ubicacion del agente
	 * @param coordenada
	 */
    public void setUbicacion(Coordenada coordenada);
    
	/**
	 * Servicio para modificar la creencia intensidadSismo en el agente edificio
	 * @param intensidad
	 */
	public void setSismo(double intensidad);
	
	
	public void setToCoordinador(IComponentIdentifier coordinador);
	
	public void setRute(ArrayList<Coordenada> rute);
    
}
