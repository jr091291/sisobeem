package sisobeem.websocket.server.endpoint.config;

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import sisobeem.artifacts.BuildAgents;
import sisobeem.artifacts.JsonManager;
import sisobeem.artifacts.abstractos.BuildAgentsAbstract;
import sisobeem.artifacts.sisobeem.config.Configuration;
import sisobeem.artifacts.sisobeem.config.EdificesConfig;
import sisobeem.artifacts.sisobeem.config.EmergencyConfig;
import sisobeem.artifacts.sisobeem.config.PersonsConfig;
import sisobeem.artifacts.sisobeem.config.SimulationConfig;

@ApplicationScoped
@ServerEndpoint(value="/simulacion/config")
public class ConfigWebSocketServer implements  JsonManager<Configuration>{
	
	 @OnOpen
	 public void open() {
		System.out.println("Se ha abierto una conexion con el socket" + this.getClass().getName());
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
	 public void handleMessage(String config, Session session) throws IOException, EncodeException {
		System.out.println("Se ha recibido un nuevo mensaje: " + config);
		Configuration c = this.toClass(config);
		
		BuildAgentsAbstract build = new BuildAgents(c);
		build.build();
	 }

	 @Override
		public Configuration toClass(String json) {
		 	Gson gson = new Gson();
		 	JsonParser parser = new JsonParser();
		    JsonArray array = parser.parse(json).getAsJsonArray();
		    SimulationConfig sim = gson.fromJson(array.get(0), SimulationConfig.class);
		    EdificesConfig[] edi = gson.fromJson(array.get(1), EdificesConfig[].class );
		    PersonsConfig per = gson.fromJson(array.get(2), PersonsConfig.class);
		    EmergencyConfig eme = gson.fromJson(array.get(3), EmergencyConfig.class );
		    Configuration con = new Configuration(
		    		per,
		    		sim,
		    		edi,
		    		eme
		    		);
		    return con;
		}


		@Override
		public String toJson(Configuration clase) {
			Gson gson = new Gson();
			return gson.toJson(clase);  
		}
}
