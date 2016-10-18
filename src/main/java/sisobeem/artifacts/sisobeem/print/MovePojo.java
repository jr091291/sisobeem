package sisobeem.artifacts.sisobeem.print;

import sisobeem.artifacts.sisobeem.config.Ubicacion;

public class MovePojo {
	private String idAgent;
	private Ubicacion moveTo;
	
	public MovePojo(){}
	
	public MovePojo(String idAgent, Ubicacion moveTo, boolean random) {
		super();
		this.idAgent = idAgent;
		this.moveTo = moveTo;
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
}
