package sisobeem.services.edificeServices;

import jadex.bridge.IComponentIdentifier;

public interface IEvacuarService {

	
	/**
	 * Servicio que presta el edificio para cambiar de piso
	 */
	public void cambiarDePiso(double ConocimientoDeLaZona, IComponentIdentifier piso, IComponentIdentifier agent);
}
