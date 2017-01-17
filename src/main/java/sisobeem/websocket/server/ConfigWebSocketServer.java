package sisobeem.websocket.server;

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

import sisobeem.abstracts.BuildAgentsAbstract;
import sisobeem.core.BuildAgents;
import sisobeem.core.simulation.Configuration;
import sisobeem.core.simulation.EdificesConfig;
import sisobeem.core.simulation.EmergencyConfig;
import sisobeem.core.simulation.PersonsConfig;
import sisobeem.core.simulation.SimulationConfig;
import sisobeem.interfaces.JsonManager;

import static sisobeem.artifacts.Log.getLog;
@ApplicationScoped
@ServerEndpoint(value="/simulacion/config")
public class ConfigWebSocketServer implements  JsonManager<Configuration>{

	 @OnOpen
	 public void open() {
		getLog().setInfo("Se ha abierto una conexion con el socket" + this.getClass().getName());
	 }
	
	 @OnClose
	 public void close(Session session, CloseReason reason) {
		getLog().setInfo("Se ha cerrado una conexion con el socket: " + this.getClass().getName() + " " + reason.getReasonPhrase());
	 }
	
	 @OnError
	 public void onError(Session session, Throwable error) {
		 getLog().setError("Se ha presentado un error con el socket" + this.getClass().getName() +": "+ error.getMessage());	
	 }
	
	 @OnMessage
	 public void handleMessage(String config, Session session) throws IOException, EncodeException {
		getLog().setInfo("Se ha recibido un nuevo mensaje: " + config);
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