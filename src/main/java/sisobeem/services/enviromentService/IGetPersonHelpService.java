package sisobeem.services.enviromentService;

import java.util.ArrayList;

import jadex.bridge.IComponentIdentifier;

public interface IGetPersonHelpService {
	
	/**
	 * Servicio que presta el ambiente para consultar el listado de personas que necesitan ayuda
	 * @return
	 */
	public ArrayList<IComponentIdentifier> getPeopleHelp(IComponentIdentifier agent);

}
