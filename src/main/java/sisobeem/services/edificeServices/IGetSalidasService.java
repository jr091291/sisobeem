package sisobeem.services.edificeServices;

public interface IGetSalidasService {
    
	/**
	 * Servicio que prestan lo ambientes informando el numero de salidas disponibles.
	 * @param conocimientoDeLaZona
	 * @return
	 */
	public int getSalidasDisponibles(double conocimientoDeLaZona );
}
