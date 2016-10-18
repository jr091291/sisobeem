package sisobeem.artifacts.sisobeem.print;

import sisobeem.artifacts.sisobeem.config.Ubicacion;

public class Route {
	private Ubicacion[] coordenadas;

	public Route(Ubicacion[] coordenadas) {
		super();
		this.coordenadas = coordenadas;
	}

	public Ubicacion[] getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Ubicacion[] coordenadas) {
		this.coordenadas = coordenadas;
	}
}
