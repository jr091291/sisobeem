package sisobeem.artifacts.sisobeem.config;

public class SimulationConfig {
	private Bounds lugar;
	private int duracion, duracionSismo, intencidad;
	public String momento;
	
	public SimulationConfig(){
		this.lugar = new Bounds();
		this.momento = "dia";
		this.duracion = 60;
		this.duracionSismo = 5;
		this.intencidad = 7;

	}
	
	public SimulationConfig(Bounds lugar, String momento, int duracion, int duracionSismo, int intencidad) {
		this.lugar = lugar;
		this.momento = momento;
		this.duracion = duracion;
		this.duracionSismo = duracionSismo;
		this.intencidad = intencidad;
	}

	public Bounds getLugar() {
		return lugar;
	}

	public void setLugar(Bounds lugar) {
		this.lugar = lugar;
	}

	public String getMomento() {
		return momento;
	}

	public void setMomento(String momento) {
		this.momento = momento;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public int getDuracionSismo() {
		return duracionSismo;
	}

	public void setDuracionSismo(int duracionSismo) {
		this.duracionSismo = duracionSismo;
	}

	public int getIntencidad() {
		return intencidad;
	}

	public void setIntencidad(int intencidad) {
		this.intencidad = intencidad;
	}
	
	
}
