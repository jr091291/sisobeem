package sisobeem.services.personServices;

public interface IGetInformationService {

	/**
	 * Método que devuelve el estado de un agente civil
	 * @return
	 */
	public String getEstado();
	
	/***
	 * Metodo que devulve el liderazgo
	 * @return
	 */
	public double getLiderazgo();
	
	/**
	 * Metoodo que devuelve el estado de salud
	 * @return
	 */
	public int getSalud();
}
