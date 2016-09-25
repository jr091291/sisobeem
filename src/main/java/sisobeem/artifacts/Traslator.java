package sisobeem.artifacts;
/**
 * Es un artefacto que traduce un sistema de Coordenadas basadas
 * en Latitud y lengitud en un sistema de Coordenadas X,Y
 * Intermedia entre el mapa y el ambiente (que es una matriz)
 * @author Erley
 *
 */
public class Traslator {
    
	//Valor minimo movimiento de una persona
	private double minMove;
	
	//Variables que definen la locacion especifica en latitudes y longitudes
	private double norte;
	private double sur;
	private double oeste;
	private double este;
	
	
	/**
	 * Constructor del traductor Latiud-longitud a Coordenadas x,y
	 * @param minMove Escala minima de movimiento ej: 0.0001
	 */
	public Traslator(double norte, double sur, double oeste, double este,double minMove){
		this.minMove = minMove;
		this.norte = norte;
		this.sur= sur;
		this.oeste = oeste;
		this.este = este;
	}
	
	/**
	 *  Medodo que establece el tama�o de la matriz en base a un sistema
	 *  de coordenadas latitud y logitud
	 * @param norte Latiud norte
	 * @param sur   Latiud sur
	 * @param oeste Longitud oeste
	 * @param este  Logitud este
	 * @return Coordenada x y y que definen el tama�o de la matrizMap
	 */
	public Coordenada getTamaño(){
		double Altura = Math.abs(norte-sur);
		double Anchura = Math.abs(oeste-este);
		
		int y = (int) (Altura/this.minMove);
		int x = (int) (Anchura/this.minMove);
		
		return new Coordenada(x,y);
	}
	
	//public Coordenada traducirCoordenada(double latiud, double longitud){
		

}
