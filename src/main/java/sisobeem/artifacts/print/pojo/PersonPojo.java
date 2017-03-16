package sisobeem.artifacts.print.pojo;

import sisobeem.artifacts.Ubicacion;

public class PersonPojo {
	
	private String idAgent;
	private Ubicacion posicion;
	private String Tipo;
	
	public PersonPojo(String id,Ubicacion p, String t) {
		this.idAgent = id;
		this.posicion = p;
		this.Tipo = t;
	}
	
	public PersonPojo(String id, String t){
		this.idAgent = id;
		this.Tipo = t;
	}

	public String getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(String idAgent) {
		this.idAgent = idAgent;
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
