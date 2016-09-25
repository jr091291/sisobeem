package sisobeem.artifacts.sisobeem.config;

public class Escala {
	private double bajo , medio, alto;

	public Escala() {
		this.bajo = 0;
		this.medio = 0;
		this.alto = 0;
	}
	
	public Escala(double bajo, double medio, double alto) {
		this.bajo = bajo;
		this.medio = medio;
		this.alto = alto;
	}

	public double getBajo() {
		return bajo;
	}

	public void setBajo(double bajo) {
		this.bajo = bajo;
	}

	public double getMedio() {
		return medio;
	}

	public void setMedio(double medio) {
		this.medio = medio;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}
}
