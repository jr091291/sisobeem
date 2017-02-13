package sisobeem.artifacts.print.pojo;

import sisobeem.artifacts.Ubicacion;

public class PuntoPojo {

	private Ubicacion posicion;
	private String Tipo;
	
	public PuntoPojo(Ubicacion p, String t) {

		this.posicion = p;
		this.Tipo = t;
	}


	public Ubicacion getPosicion() {
		return posicion;
	}

	public void setPosicion(Ubicacion posicion) {
		this.posicion = posicion;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	
}
