package sisobeem.artifacts;

import java.util.ArrayList;
import java.util.Map;
import java.util.zip.ZipInputStream;

import jadex.bridge.IComponentIdentifier;
import sisobeem.utilities.Random;
import sisobeem.websocket.client.zoneClientEndpoint;

public class Mensajero extends Thread{
    
	private boolean enviar ;

	private zoneClientEndpoint clientEndPoint,clientEndPoint2,clientEndPoint3,clientEndPoint4,
	clientEndPoint5,clientEndPoint6,clientEndPoint7,clientEndPoint8,clientEndPoint9,clientEndPoint10;

	
	Map <String,String> bandejaMsg;
	ArrayList<String> cidsPerson ;
	int inicioPerson,finPerson;
	
	int numero;
	
	public Mensajero(int incioPerson, int finPerson,
			        ArrayList<String> cidsPerson,
			        Map <String,String> bandejaMsg,
			        zoneClientEndpoint clientEndPoint,
					zoneClientEndpoint clientEndPoint2,
			        zoneClientEndpoint clientEndPoint3,
			        zoneClientEndpoint clientEndPoint4,
			        zoneClientEndpoint clientEndPoint5,
			        zoneClientEndpoint clientEndPoint6,
			        zoneClientEndpoint clientEndPoint7,
			        zoneClientEndpoint clientEndPoint8,
			        zoneClientEndpoint clientEndPoint9,
			        zoneClientEndpoint clientEndPoint10, int x) {
	this.numero=x;	
	this.bandejaMsg= bandejaMsg;
	this.cidsPerson = cidsPerson;
	this.inicioPerson = incioPerson;
	this.finPerson= finPerson;
	this.clientEndPoint = clientEndPoint;
	this.clientEndPoint2 = clientEndPoint2;
	this.clientEndPoint3 = clientEndPoint3;
	this.clientEndPoint4 = clientEndPoint4;
	this.clientEndPoint5 = clientEndPoint5;
	this.clientEndPoint6 = clientEndPoint6;
	this.clientEndPoint7 = clientEndPoint7;
	this.clientEndPoint8 = clientEndPoint8;
	this.clientEndPoint9 = clientEndPoint9;
	this.clientEndPoint10= clientEndPoint10;
	
//	System.out.println("Inicio: "+ incioPerson);
//	System.out.println("Final: "+ finPerson);
	//for (String string : cidsPerson) {
	//	System.out.println(string);
	//}
	//System.out.println("¨************************************************");
		
		
	}
	
    /**
     * Método para enviar mensajes a la vista
     * @param mensaje
     */
    public void sendMensaje(zoneClientEndpoint cliendtEndPoint, String mensaje){
    	
        cliendtEndPoint.sendMessage(mensaje);
          }
    
	
	@Override
	public void run() {
		
		System.out.println("Motor "+this.numero+" "+"Inicio: " +this.inicioPerson+" "+"Fin: " +this.finPerson);
		//System.out.println("Motor: "+this.numero+" "+this);

	
		int bandera=0;
		while (this.enviar) {
	 //  System.out.println("Inicio :"+this.inicioPerson);
	   //System.out.println("Fin :"+this.finPerson);
	   //System.out.println("Bandeja :"+this.bandejaMsg.size());
	  // System.out.println("Personas :"+this.inicioPerson);
		for (int i = inicioPerson; i <= finPerson;) {
		
			String agent = cidsPerson.get(i);
			 String data = bandejaMsg.get(agent);
				//if(this.numero==4){
					//System.out.println(agent+" "+i); 
				//}
			// String data = bandejaMsg.
			
			//synchronized(bandejaMsg){
			  // bandejaMsg.put(agent, null);
			 //}
			 // Eliminamos el mensaje  despues de tenerlo guardado
			 // System.out.println(data); 
			  if(data!=null){
				// System.out.println("Motor :"+numero+" ON");
				switch (bandera) {
					case 0:
						  sendMensaje(clientEndPoint,data);
						  bandera++;
						  i++;
						//  System.out.println("enviando msg 1");
					break;
					
					case 1:
						  sendMensaje(clientEndPoint2,data);
						  bandera++;
						  i++;
						  //  System.out.println("enviando msg 2");
					break;
					
					case 2:
						  sendMensaje(clientEndPoint3,data);
						  bandera++;
						  i++;
						  //System.out.println("enviando msg 3");
					break;
					case 3:
						  sendMensaje(clientEndPoint4,data);
						  bandera++;
						  i++;
						  //System.out.println("enviando msg 4");
				    break;	
					case 4:
				     	  sendMensaje(clientEndPoint5,data);
						  bandera++;
						  i++;
						  //System.out.println("enviando msg 5");
				    break;
					case 5:
						  sendMensaje(clientEndPoint6,data);
						  bandera++;
						  i++;
						  //System.out.println("enviando msg 6");
					break;
					
					case 6:
						  sendMensaje(clientEndPoint7,data);
						  bandera++;
						  i++;
						  // System.out.println("enviando msg 7");
					break;
					
					case 7:
						  sendMensaje(clientEndPoint8,data);
						  bandera++;
						  i++;
						  //System.out.println("enviando msg 8");
					break;
					case 8:
						  sendMensaje(clientEndPoint9,data);
						  bandera++;
						  i++;
						  //System.out.println("enviando msg 9");
				    break;	
					case 9:
				     	  sendMensaje(clientEndPoint10,data);
						  bandera=0;
						  i++;
						  //System.out.println("enviando msg 10");
				    break;	
					
					

				    default:
				    	  i++;
					break;
				}
			  }else{
				  i++;
			  }
			   
			 
			  
			   if(i>finPerson){
			      i=inicioPerson;
			   }else{
				  
			   }
			   
			
					  
			   try {
				   Thread.sleep(14);
				  // System.out.println("Pausa: "+this.numero);
			   } catch (InterruptedException e) {
				 // TODO Auto-generated catch block
				  e.printStackTrace();
				  
				 // System.err.println("errorrrrrrrrrrrrrrrr");
			   }
			
		     }
		
	    }
	
	

   }
	
	/**
	 * Metodo para iniciar el envio de mensajes
	 */
	public void iniciar() {
		this.enviar = true;
		this.start();
	}
	
	
	/**
	 * Metodo para detener el envio de mensajes
	 */
	@SuppressWarnings("deprecation")
	public void parar(){
         this.enviar = false;
         this.stop();
	}

	
}
