package sisobeem.websocket.server.jadex;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.CloseReason;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import sisobeem.websocket.Session.DeviceSessionHandler4;
import static sisobeem.websocket.Session.DeviceSessionHandler4.getDeviceSessionHandler;

@ApplicationScoped
@ServerEndpoint(value="/simulacion/jadex4")
public class JadexWebSocketServer4{
       
	 
	 private DeviceSessionHandler4 sessionHandler = getDeviceSessionHandler();
	 
	 @OnOpen
	 public void open(Session session) {
			System.out.println("Se ha abierto una conexion con el socket" + this.getClass().getName());
			sessionHandler.addSession(session);
		 }
	
	 @OnClose
	 public void close(Session session, CloseReason reason) {
		 System.out.println("Se ha cerrado una conexion con el socket: " + this.getClass().getName() + " " + reason.getReasonPhrase());	
		// sessionHandler.removeSession(session);
	 }
	
	 @OnError
	 public void onError(Session session, Throwable error) {
		 System.out.println("Se ha presentado un error con el socket" + this.getClass().getName() +": "+ error.getMessage());	
	 }
	
	 @OnMessage
	 public void handleMessage(String json, Session session) throws IOException, EncodeException {
		 sessionHandler.sendToVista(json);
	 }


}
