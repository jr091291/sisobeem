package sisobeem.artifacts.print.pojo;

import sisobeem.artifacts.Ubicacion;

public class EdificePojo {
	
	private String idAgent;
	private Ubicacion posicion;
	private String Tipo;
	
	//Estadisticas
	int MsgAyuda;	
	int MsgDeCalma;
	int MsgDeConfianza;
	int MsgFrsutracion;
	int MsgHostilidad;
	int MsgPanico;
	int MsgPrimerosAux;
	int MsgResguardo;
	int MsgMotivacion;
	int PersonasMuertas;
	int PersonasAtrapadas;
	int Suicidios;
	int derrumbado;
	
	public EdificePojo(String id,Ubicacion p, String t) {
		this.idAgent = id;
		this.posicion = p;
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
	
	public int getMsgAyuda() {
		return MsgAyuda;
	}

	public void setMsgAyuda(int msgAyuda) {
		MsgAyuda = msgAyuda;
	}

	public int getMsgDeCalma() {
		return MsgDeCalma;
	}

	public void setMsgDeCalma(int msgDeCalma) {
		MsgDeCalma = msgDeCalma;
	}

	public int getMsgDeConfianza() {
		return MsgDeConfianza;
	}

	public void setMsgDeConfianza(int msgDeConfianza) {
		MsgDeConfianza = msgDeConfianza;
	}

	public int getMsgFrsutracion() {
		return MsgFrsutracion;
	}

	public void setMsgFrsutracion(int msgFrsutracion) {
		MsgFrsutracion = msgFrsutracion;
	}

	public int getMsgHostilidad() {
		return MsgHostilidad;
	}

	public void setMsgHostilidad(int msgHostilidad) {
		MsgHostilidad = msgHostilidad;
	}

	public int getMsgPanico() {
		return MsgPanico;
	}

	public void setMsgPanico(int msgPanico) {
		MsgPanico = msgPanico;
	}

	public int getMsgPrimerosAux() {
		return MsgPrimerosAux;
	}

	public void setMsgPrimerosAux(int msgPrimerosAux) {
		MsgPrimerosAux = msgPrimerosAux;
	}

	public int getMsgResguardo() {
		return MsgResguardo;
	}

	public void setMsgResguardo(int msgResguardo) {
		MsgResguardo = msgResguardo;
	}

	public int getMsgMotivacion() {
		return MsgMotivacion;
	}

	public void setMsgMotivacion(int msgMotivacion) {
		MsgMotivacion = msgMotivacion;
	}

	public int getPersonasMuertas() {
		return PersonasMuertas;
	}

	public void setPersonasMuertas(int personasMuertas) {
		PersonasMuertas = personasMuertas;
	}

	public int getPersonasAtrapadas() {
		return PersonasAtrapadas;
	}

	public void setPersonasAtrapadas(int personasAtrapadas) {
		PersonasAtrapadas = personasAtrapadas;
	}

	public int getSuicidios() {
		return Suicidios;
	}

	public void setSuicidios(int suicidios) {
		Suicidios = suicidios;
	}

	public int getDerrumbado() {
		return derrumbado;
	}

	public void setDerrumbado(int derrumbado) {
		this.derrumbado = derrumbado;
	}
	

}
