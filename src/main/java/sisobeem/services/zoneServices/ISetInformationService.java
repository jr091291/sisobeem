package sisobeem.services.zoneServices;

import jadex.bridge.IComponentIdentifier;
import sisobeem.artifacts.print.pojo.EdificePojo;

public interface ISetInformationService {
	
	public void setDead(IComponentIdentifier agent);
	
	public void setEstadisticasEdifice(EdificePojo info);

}
