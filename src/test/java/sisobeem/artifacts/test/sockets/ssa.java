package sisobeem.artifacts.test.sockets;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.server.ServerEndpointConfig;

import org.junit.Test;

import sisobeem.websocket.client.zoneClientEndpoint;
import sisobeem.websocket.server.jadex.JadexWebSocketServer;

public class ssa {

	@Test
	public void test() {
		 ServerEndpointConfig config = ServerEndpointConfig.Builder.create(JadexWebSocketServer.class, "/foo").build();
		 
		 zoneClientEndpoint clientEndPoint;
		try {
			clientEndPoint = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/foo"));
			clientEndPoint.addMessageHandler(message -> {
				System.out.println("recibi un mensaje (Zone): "+message);
			});
			clientEndPoint.sendMessage("Hola");
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
			
	}
}
