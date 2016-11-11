package sisobeem.artifacts.print.pojo;

import sisobeem.artifacts.Ubicacion;

public class RoutePojo {
	private Ubicacion origen;
	private Ubicacion destino;
	private Ubicacion[] coordenadas;

	public RoutePojo(Ubicacion origen, Ubicacion destino) {
		super();
		this.origen = origen;
		this.destino = destino;
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
}
