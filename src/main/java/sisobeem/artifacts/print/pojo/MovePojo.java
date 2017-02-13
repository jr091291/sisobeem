package sisobeem.artifacts.print.pojo;

import sisobeem.artifacts.Ubicacion;

public class MovePojo {
	private String idAgent;
	private Ubicacion moveTo;
	private String Tipo;
	public MovePojo(){}
	
	public MovePojo(String idAgent, Ubicacion moveTo) {
		super();
		this.idAgent = idAgent;
		this.moveTo = moveTo;
	}
	
	public MovePojo(String idAgent, Ubicacion moveTo,String tipo) {
		super();
		this.idAgent = idAgent;
		this.moveTo = moveTo;
		this.setTipo(tipo);
	}

	public String getIdAgent() {
		return idAgent;
	}

	public void setIdAgent(String idAgent) {
		this.idAgent = idAgent;
	}

	public Ubicacion getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(Ubicacion moveTo) {
		this.moveTo = moveTo;
	}

	public String getTipo() {
		return Tipo;
	}

	public void setTipo(String tipo) {
		Tipo = tipo;
	}
}
