package sisobeem.core.simulation;

import sisobeem.artifacts.Ubicacion;

public  class EdificesConfig {
	  private int id;
	  private int personas;
	  private Ubicacion ubicacion;
	  private float resistencia;
	  private int pisos;
	  private int salidas;
	
	  public EdificesConfig(){}
	  
	  public EdificesConfig(int id, int personas, Ubicacion ubicacion, float resistencia, int pisos, int salidas) {
		this.id = id;
		this.personas = personas;
		this.ubicacion = ubicacion;
		this.resistencia = resistencia;
		this.pisos = pisos;
		this.salidas = salidas;
	  }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPersonas() {
		return personas;
	}

	public void setPersonas(int personas) {
		this.personas = personas;
	}

	public Ubicacion getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(Ubicacion ubicacion) {
		this.ubicacion = ubicacion;
	}

	public float getResistencia() {
		return resistencia;
	}

	public void setResistencia(float resistencia) {
		this.resistencia = resistencia;
	}

	public int getPisos() {
		return pisos;
	}

	public void setPisos(int pisos) {
		this.pisos = pisos;
	}

	public int getSalidas() {
		return salidas;
	}

	public void setSalidas(int salidas) {
		this.salidas = salidas;
	}
}