package sisobeem.artifacts;

/**
 * Clase Coordenada. 
 * @author Erley
 *
 */
public class Coordenada {
   int x;
   int y;
   
   public Coordenada(int x, int y){
	   this.x = x;
	   this.y = y;
   }
   
   public Coordenada(){
	   
   }
  
  /**** Metodos Accesores *****/
   
  public int getX(){
	  return this.x;
  }
  
  public int getY(){
	  return this.y;
  }
  
  public void setX(int x){
	  this.x = x;
  }
  
  public void setY(int y){
	  this.y = y;
  }
}
