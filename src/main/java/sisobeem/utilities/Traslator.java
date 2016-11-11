package sisobeem.utilities;

import sisobeem.artifacts.Bounds;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Ubicacion;

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
	
	 public static Traslator traductor;

	//Variables que definen la locacion especifica en latitudes y longitudes
	private double norte;
	private double sur;
	private double oeste;
	private double este;
	
	
	private Traslator(){
		
	}
	
	 public static Traslator getTraslator(){
	        if(traductor==null){
	        	traductor = new Traslator();
	            return traductor;
	        }
	       return traductor;     
	    }
	/**
	 * Constructor del traductor Latiud-longitud a Coordenadas x,y
	 * @param minMove Escala minima de movimiento ej: 0.0001
	 */
	public void setDatosIniciales(Bounds bounds,double minMove){
		this.minMove = minMove;
		this.norte = bounds.getNorth();
		this.sur= bounds.getSouth();
		this.oeste = bounds.getWest();
		this.este = bounds.getEast();
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
	
	/**
	 * Metodo que deuelve una coordenada de la matriz x,y en base en una Ubicacion lat,long
	 * @param ubicacion
	 * @return Coordenada
	 */
	public Coordenada getCoordenada(Ubicacion ubicacion){
		
		Coordenada coordenada = new Coordenada();
		coordenada.setX((int) (Math.abs((ubicacion.getLng()/this.minMove)-(this.oeste/this.minMove))));
		coordenada.setY((int) (Math.abs((ubicacion.getLat()/this.minMove)-(this.sur/this.minMove))));
		
		return coordenada; 
	}
	
	public Ubicacion getUbicacion(Coordenada coordenada){
		
		Ubicacion u = new Ubicacion();
		u.setLng((coordenada.getX()*this.minMove)+this.oeste);
		u.setLat((coordenada.getY()*this.minMove)+this.sur);
		
		return u;
	}
	
	public double getNorte() {
		return norte;
	}

	public void setNorte(double norte) {
		this.norte = norte;
	}

	public double getSur() {
		return sur;
	}

	public void setSur(double sur) {
		this.sur = sur;
	}

	public double getOeste() {
		return oeste;
	}

	public void setOeste(double oeste) {
		this.oeste = oeste;
	}

	public double getEste() {
		return este;
	}

	public void setEste(double este) {
		this.este = este;
	}
		

}
