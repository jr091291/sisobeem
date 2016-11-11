package sisobeem.websocket.server.jadex;

import static sisobeem.websocket.Session.DeviceSessionHandler32.getDeviceSessionHandler;

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

import sisobeem.websocket.Session.DeviceSessionHandler32;

@ApplicationScoped
@ServerEndpoint(value="/simulacion/jadex32")
public class JadexWebSocketServer32{
       
	 
	 private DeviceSessionHandler32 sessionHandler = getDeviceSessionHandler();
	 
	 @OnOpen
	 public void open(Session session) {
			System.out.println("Se ha abierto una conexion con el socket" + this.getClass().getName());
			sessionHandler.addSession(session);
		 }
	
	 @OnClose
	 public void close(Session session, CloseReason reason) {
		 System.out.println("Se ha cerrado una conexion con el socket: " + this.getClass().getName() + " " + reason.getReasonPhrase());	
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
