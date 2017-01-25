package sisobeem.websocket.Session;

import java.io.IOException;
import java.util.ArrayList;

import javax.json.JsonObject;
import javax.websocket.Session;
import static sisobeem.artifacts.Log.getLog;

public abstract class DeviceSessionHandlerAbstract {
	  private ArrayList<Session> sessions = new ArrayList<Session>();
	   
      public void addSession(Session session) {
    		 sessions.add(session);
   	     }
      
  

      public void removeSession(Session session) {
          sessions.remove(session);
      }
      
      public void sendToSession(Session session, JsonObject message) {
    	  
   	   try {
              session.getBasicRemote().sendText(message.toString());
          } catch (IOException ex) {
              sessions.remove(session);
          }
     }
     
     public void sendToVista(String message) {
   	  
  	   try {
  		         Session s= sessions.get(sessions.size()-2);
  				 s.getBasicRemote().sendText(message);
  		              
         } catch (IOException ex) {
        	 getLog().setError("Error al enviar el mensaje en el manejador de mensajes");
         }
    }
     
     
     public void sendToZone(String message){
    
     try {
		         Session s= sessions.get(sessions.size()-1);
				 s.getBasicRemote().sendText(message);
		              
       } catch (IOException ex) {
      	 getLog().setError("Error al enviar el mensaje a Zone");
       }
     }
     
     
    public Boolean vista(Session session){
		
    	Session vista = this.sessions.get(0);
    	
    	if(vista.equals(session)){
    		return true;
    	}
    	return false;
    	
    }
}
