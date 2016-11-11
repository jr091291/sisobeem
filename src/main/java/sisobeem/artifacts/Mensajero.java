package sisobeem.artifacts;

import java.util.ArrayList;
import java.util.Map;

import jadex.bridge.IComponentIdentifier;
import sisobeem.websocket.client.zoneClientEndpoint;

public class Mensajero extends Thread{
    
	private boolean enviar ;

	private zoneClientEndpoint clientEndPoint,clientEndPoint2,clientEndPoint3,clientEndPoint4,
	clientEndPoint5,clientEndPoint6,clientEndPoint7,clientEndPoint8,clientEndPoint9,clientEndPoint10;

	
	Map <String,String> bandejaMsg;
	ArrayList<IComponentIdentifier> cidsPerson ;
	int inicioPerson,finPerson;
	
	public Mensajero(int incioPerson, int finPerson,
			        ArrayList<IComponentIdentifier> cidsPerson,
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
			        zoneClientEndpoint clientEndPoint10) {
		
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
	
		int bandera=0;
		while (this.enviar) {
	 //  System.out.println("Inicio :"+this.inicioPerson);
	   //System.out.println("Fin :"+this.finPerson);
	   //System.out.println("Bandeja :"+this.bandejaMsg.size());
	  // System.out.println("Personas :"+this.inicioPerson);
		for (int i = inicioPerson; i < finPerson;) {
			// System.out.println(cidsPerson.get(i).getLocalName()); 
			 String data = bandejaMsg.get(cidsPerson.get(i).getLocalName());
			 // System.out.println(data); 
			  if(data!=null){
				
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
						  //System.out.println("enviando msg 2");
					break;
					
					case 2:
						  sendMensaje(clientEndPoint3,data);
						  bandera++;
						  i++;
						 // System.out.println("enviando msg 3");
					break;
					case 3:
						  sendMensaje(clientEndPoint4,data);
						  bandera++;
						  i++;
						 // System.out.println("enviando msg 4");
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
						  //System.out.println("enviando msg 7");
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
					break;
				}
			  }
			
			  
			   if(i==finPerson){
			      i=inicioPerson;
			   }
					  
			   try {
				   Thread.sleep(9);	
			   } catch (InterruptedException e) {
				 // TODO Auto-generated catch block
				  e.printStackTrace();
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
