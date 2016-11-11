package sisobeem.core.simulation;

public class EmergencyConfig {
	private float tiempoRespuesta;
	private int personalBusquedaRescate, personalSalud, PersonalSeguridad;
	
	public EmergencyConfig() {
		this.tiempoRespuesta = 0;
		this.personalBusquedaRescate = 0;
		this.personalSalud = 0;
		this.PersonalSeguridad = 0;
	}

	public EmergencyConfig(float tiempoRespuesta, int personalBusquedaRescate, int personalSalud,
			int personalSeguridad) {
		this.tiempoRespuesta = tiempoRespuesta;
		this.personalBusquedaRescate = personalBusquedaRescate;
		this.personalSalud = personalSalud;
		PersonalSeguridad = personalSeguridad;
	}

	public float getTiempoRespuesta() {
		return tiempoRespuesta;
	}

	public void setTiempoRespuesta(float tiempoRespuesta) {
		this.tiempoRespuesta = tiempoRespuesta;
	}

	public int getPersonalBusquedaRescate() {
		return personalBusquedaRescate;
	}

	public void setPersonalBusquedaRescate(int personalBusquedaRescate) {
		this.personalBusquedaRescate = personalBusquedaRescate;
	}

	public int getPersonalSalud() {
		return personalSalud;
	}

	public void setPersonalSalud(int personalSalud) {
		this.personalSalud = personalSalud;
	}

	public int getPersonalSeguridad() {
		return PersonalSeguridad;
	}

	public void setPersonalSeguridad(int personalSeguridad) {
		PersonalSeguridad = personalSeguridad;
	}
}
