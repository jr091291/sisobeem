package sisobeem.services.plantServices;

import jadex.bridge.IComponentIdentifier;

public interface IEvacuarPisoService {

	
	/**
	 * Método para eliminar un agente de la lista del piso
	 * @param agent
	 */
	public void Evacuar(IComponentIdentifier agent);
	
	
	/**
	 * Método para adicionar agentes al piso
	 * @param agent
	 */
	public void Adicionar(IComponentIdentifier agent);
	
	
	public void Suicidar(IComponentIdentifier agent);
}

