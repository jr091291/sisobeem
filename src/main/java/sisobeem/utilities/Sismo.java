package sisobeem.utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

import sisobeem.artifacts.Cronometro;
import sisobeem.interfaces.ISismo;

/**
 * Clase que representa y simula el sismo parametrizado
 * @author Erley
 *
 */
public class Sismo {
    
    private int duracion;
    private int intensidad;
    private double aux;
    private double val; //Valor de la intencidad en un tiempo especifico
    private ISismo vista;
    
    // XYSeries temp;
      boolean bandera=true;
    
    Cronometro cronometro;
      
	public Sismo(int duracion, int intensidad, ISismo vista) {
            this.duracion = duracion;
	    this.intensidad = intensidad;
            try {
                cronometro = new Cronometro();
            } catch (Exception e) {
                    System.out.println("Excepcion");
            }
		
            this.vista = vista;
             aux=0;
	}
        
        public void modificarIntensidadGradual(){
            Thread y = new Thread(){

                @Override
                public void run() {
                                             
                  //   int cont=0;
                   
                     while (cronometro.getSegundos()<duracion) {  
                      try {
                              Thread.sleep(intensidad*10);
                          } catch (InterruptedException ex) {
                        	  
                        	  System.out.println("errrrorrrr");
                              Logger.getLogger(Sismo.class.getName()).log(Level.SEVERE, null, ex);
                          }
                           
                         //  System.out.println("segundo: "+cronometro.getSegundos());
                    
                           
                           if(pico()){
                               
                               double x = Random.getDoubleRandom(0.0, intensidad/5);
                               if((aux+x)<=intensidad){
                                    aux= (aux+x);
                               }
                               // System.out.println(aux);
                           }else{
                               double x = Random.getDoubleRandom(0.0,intensidad/9);
                                if((aux-x)<1){
                                    aux=1;
                               }else{
                                    aux= (aux-x);
                                }
                               
                               // System.out.println(aux);
                           }
                    }
                }
                
            };
            
            y.start();
        }
	
	public void Start(){
             //   temp = new XYSeries("Sismo");
		cronometro.iniciarCronometro();
                try {
                 modificarIntensidadGradual();
            } catch (Exception e) {
            	System.out.println("Error en el graduador de intensidad");
            }
               
		//System.out.println("Dentro del terremoto");
		Thread x = new Thread(){
		   @Override
		   public void run() {
                    //   int contador =0;
			   while (cronometro.getSegundos()<duracion) {
				  	
				   try {
					 //  System.out.println(duracion);
                     //  Thread.sleep(duracion);
					   Thread.sleep(500);
                   } catch (InterruptedException ex) {
                 	  
                 	  System.out.println("errrrorrrr");
                       Logger.getLogger(Sismo.class.getName()).log(Level.SEVERE, null, ex);
                   }
				   generarNuevoValor();
                                    //    System.out.println("Temblando");
                                     //    temp.add(contador,getVal());
					  //  contador++;
                        
					    
					    	// System.out.println(cronometro.getSegundos()+" "+aux);
					    	
		                    vista.setIntensidadSismo(aux);
					
					   
                }	
			
		   }
		   

		};
		
		x.start();
	}
	
	
	/**
	 * Método Accesor
	 * @return
	 */
	public double getVal(){
		
		return val;
		
	}
    
	/**
	 * Pico con el 10 % de probabilidad
	 * @return
	 */
    private boolean pico(){
            if(Random.getIntRandom(0, 1)>0.9){
                return true;
            }
            
            return false;
    }
	
	
    /**
     * Genera valores aleatorios entre o y la intensidad del momento
     */
	private void generarNuevoValor(){
         
	 val= Random.getDoubleRandom(0.0,aux);
	}
        

      
}
