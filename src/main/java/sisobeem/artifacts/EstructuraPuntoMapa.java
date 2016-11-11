package sisobeem.artifacts;

import java.util.concurrent.CopyOnWriteArrayList;

import jadex.bridge.IComponentIdentifier;

public class EstructuraPuntoMapa {
	
	
	CopyOnWriteArrayList<IComponentIdentifier> agents;
	int daño;
	
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
	public int getDaño() {
		return daño;
	}
	public void setDaño(int daño) {
		this.daño = daño;
	}
	
	

}
