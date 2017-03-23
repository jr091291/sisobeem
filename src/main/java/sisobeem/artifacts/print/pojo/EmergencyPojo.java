package sisobeem.artifacts.print.pojo;



public class EmergencyPojo {

	private String idAgent;
	private String Tipo;
	
	private int personasAyudadas;
	private int pacientes;
	public int getPacientes() {
		return pacientes;
	}
	public void setPacientes(int pacientes) {
		this.pacientes = pacientes;
	}
	public int getPersonasAyudadas() {
		return personasAyudadas;
	}
	public void setPersonasAyudadas(int personasAyudadas) {
		this.personasAyudadas = personasAyudadas;
	}
	public int getPersonasAtendidas() {
		return personasAtendidas;
	}
	public void setPersonasAtendidas(int personasAtendidas) {
		this.personasAtendidas = personasAtendidas;
	}
	public int getPuntosCubiertos() {
		return puntosCubiertos;
	}
	public void setPuntosCubiertos(int puntosCubiertos) {
		this.puntosCubiertos = puntosCubiertos;
	}
	private int personasAtendidas;
	private int puntosCubiertos;
	
	public EmergencyPojo(String id, String tipo) {
		this.idAgent = id;
		this.Tipo = tipo;
	} 
	public String getIdAgent() {
		return idAgent;
	}
	public void setIdAgent(String idAgent) {
		this.idAgent = idAgent;
	}
	public String getTipo() {
		return Tipo;
	}
	public void setTipo(String tipo) {
		Tipo = tipo;
	}
	
	
}
