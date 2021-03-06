package sisobeem.artifacts.print.pojo;

import jadex.bridge.IComponentIdentifier;
import sisobeem.artifacts.Ubicacion;

public class RoutePojo {
	
	private String agent;
	private Ubicacion origen;
	private Ubicacion destino;
	private Ubicacion[] coordenadas;

	public RoutePojo(Ubicacion origen, Ubicacion destino, String agent) {
		super();
		this.origen = origen;
		this.destino = destino;
		
		this.setAgent(agent);
	}

	
	public Ubicacion getOrigen() {
		return origen;
	}


	public void setOrigen(Ubicacion origen) {
		this.origen = origen;
	}


	public Ubicacion getDestino() {
		return destino;
	}


	public void setDestino(Ubicacion destino) {
		this.destino = destino;
	}


	public Ubicacion[] getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Ubicacion[] coordenadas) {
		this.coordenadas = coordenadas;
	}


	public String getAgent() {
		return agent;
	}


	public void setAgent(String agent) {
		this.agent = agent;
	}
}
