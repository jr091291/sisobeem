package sisobeem.services.zoneServices;

import jadex.bridge.IComponentIdentifier;
import sisobeem.artifacts.print.pojo.EdificePojo;
import sisobeem.artifacts.print.pojo.EmergencyPojo;

public interface ISetInformationService {
	
	public void setDead(IComponentIdentifier agent);
	
	public void setEstadisticasEdifice(EdificePojo info);

	public void setEstadisticasEmergencia(EmergencyPojo info);
	
	public void setResguardo(IComponentIdentifier agent);
	
    public void setPersonaAyudada(IComponentIdentifier agent);
	

}
