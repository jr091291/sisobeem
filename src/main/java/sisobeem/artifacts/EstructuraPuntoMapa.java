package sisobeem.artifacts;

import java.util.ArrayList;

import jadex.bridge.IComponentIdentifier;

public class EstructuraPuntoMapa {
	
	
	ArrayList<IComponentIdentifier> agents;
	int daño;
	
	public EstructuraPuntoMapa(){
		agents = new ArrayList<IComponentIdentifier>();
	}
	
	/**
	 * Metodos Accesores
	 * @return
	 */
	public ArrayList<IComponentIdentifier> getAgents() {
		return agents;
	}
	public void setAgents(ArrayList<IComponentIdentifier> agents) {
		this.agents = agents;
	}
	public int getDaño() {
		return daño;
	}
	public void setDaño(int daño) {
		this.daño = daño;
	}
	
	

}
