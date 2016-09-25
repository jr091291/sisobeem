package sisobeem.artifacts.sisobeem.config;

public class PersonsConfig {
	private int transeuntes;
	private String edad, conocimiento;
	private float salud, liderazgo;
		
	public PersonsConfig() {
		this.transeuntes = 10;
		this.edad = "30,30";
		this.conocimiento  = "30,30";
		this.salud =  0;
		this.liderazgo = 0;
	}


	public PersonsConfig(int transeuntes, String edad, String conocimiento, float salud, float liderazgo) {
		this.transeuntes = transeuntes;
		this.edad = edad;
		this.conocimiento = conocimiento;
		this.salud = salud;
		this.liderazgo = liderazgo;
	}


	public int getTranseuntes() {
		return transeuntes;
	}

	public void setTranseuntes(int transeuntes) {
		this.transeuntes = transeuntes;
	}


	public String getEdad() {
		return edad;
	}


	public void setEdad(String edad) {
		this.edad = edad;
	}


	public String getConocimiento() {
		return conocimiento;
	}


	public void setConocimiento(String conocimiento) {
		this.conocimiento = conocimiento;
	}


	public float getSalud() {
		return salud;
	}

	public void setSalud(float salud) {
		this.salud = salud;
	}

	public float getLiderazgo() {
		return liderazgo;
	}

	public void setLiderazgo(float liderazgo) {
		this.liderazgo = liderazgo;
	}
}
