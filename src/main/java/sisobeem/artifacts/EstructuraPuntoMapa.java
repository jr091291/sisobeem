package sisobeem.artifacts;

import java.util.concurrent.CopyOnWriteArrayList;

import jadex.bridge.IComponentIdentifier;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.kernelbase.ExternalAccess;
import sisobeem.services.zoneServices.IMapaService;

public class EstructuraPuntoMapa {
	
	
	CopyOnWriteArrayList<IComponentIdentifier> agents;
	double daño;
	
	public EstructuraPuntoMapa(){
		agents = new CopyOnWriteArrayList<IComponentIdentifier>();
	}
	
	/**
	 * Metodos Accesores
	 * @return
	 */
	public CopyOnWriteArrayList<IComponentIdentifier> getAgents() {
		return agents;
	}
	public void setAgents(CopyOnWriteArrayList<IComponentIdentifier> agents) {
		this.agents = agents;
	}
	public double getDaño() {
		return daño;
	}
	public void setDaño(double d) {
		this.daño = d;
	}
	
	

}
