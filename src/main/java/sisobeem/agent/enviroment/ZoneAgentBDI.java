package sisobeem.agent.enviroment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

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

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.ComponentTerminatedException;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.commons.transformation.annotations.Classname;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentKilled;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.BuildAgents;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.EstructuraPuntoMapa;
import sisobeem.artifacts.Random;
import sisobeem.artifacts.Traslator;
import sisobeem.artifacts.abstractos.BuildAgentsAbstract;
import sisobeem.artifacts.sisobeem.config.Configuration;
import sisobeem.artifacts.sisobeem.config.EdificesConfig;
import sisobeem.artifacts.sisobeem.config.SimulationConfig;
import sisobeem.artifacts.sisobeem.print.MoveAction;
import sisobeem.artifacts.sisobeem.print.MovePojo;
import sisobeem.services.edificeServices.ISetBeliefEdificeService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;
import sisobeem.services.zoneServices.IMapaService;


@Agent
@Description("Zone: Ambiente Superior, Mapa.")
@Service
@RequiredServices({
	@RequiredService(name="ISetUbicacionService", type=ISetBeliefPersonService.class),
	@RequiredService(name="ISetBeliefEdificeService", type=ISetBeliefEdificeService.class),
	@RequiredService(name="ISetStartService", type=ISetStartService.class)
})
@ProvidedServices({
	@ProvidedService(name ="IMapaService",type=IMapaService.class)
})
public class ZoneAgentBDI extends EnviromentAgentBDI implements IMapaService {

	ArrayList<IComponentIdentifier> cidsEdifices ;
	
	@Belief
	EstructuraPuntoMapa [][] mapa;
	

	/**
	 * Datos del sismo
	 */
	@Belief
    SimulationConfig simulacionConfig;
	EdificesConfig[] edConfig;


	@SuppressWarnings("unchecked")
	@AgentCreated
	public void init() {
   
		System.out.println("Agente " + agent.getComponentIdentifier().getLocalName() + " creado");
		
		this.cidsEdifices= new ArrayList<IComponentIdentifier>();

		// Accedemos a los argumentos del agente
    	this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();
		
		//Obtenemos los cid de las personas en la Zona
		cidsPerson = (ArrayList<IComponentIdentifier>) arguments.get("cidsPerson");
		
		//Obtenemos los cid de las edificaciones
		cidsEdifices = (ArrayList<IComponentIdentifier>) arguments.get("cidsEdifices");
		
		//Datos de la simulacion
		this.simulacionConfig =  (SimulationConfig) this.arguments.get("simulacionConfig");
		
		this.edConfig = (EdificesConfig[]) this.arguments.get("edificesConfig");
		
		/**
		 * Creacion del mapa
		 */
		try {
	        this.mapa = new EstructuraPuntoMapa[(int) this.arguments.get("x")][(int) this.arguments.get("y")];		
		}catch (Exception e) {
			System.out.println("Mapa demasiado grande");
		}
		
		asignarCoordenadasIniciales();
	
		sendCoordenadasIniciales();
	
		setMetoPerson();
		
	    setMetoEdifices();
		
		startAgents();
		
		
		
	}
	
	@AgentBody
	public void body() {
    

	}

	
	
	/**
	 * Metodo para enviar el cidZone a los agentes transeuntes
	 */
	private void setMetoPerson() {
		 System.out.println("Enviado creencias a las personas");
		 for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				
				for (IComponentIdentifier person : this.mapa[i][j].getAgents()) {
					
					IFuture<ISetBeliefPersonService> persona= agent.getComponentFeature(IRequiredServicesFeature.class).searchService(ISetBeliefPersonService.class, person);
				
						   persona.addResultListener( new IResultListener<ISetBeliefPersonService>(){

							@Override
							public void resultAvailable(ISetBeliefPersonService result) {
								//System.out.println("Asignando Zone a Persona");
								result.setZone(getAgent().getComponentIdentifier());
								
							}

							@Override
							public void exceptionOccurred(Exception exception) {
								
							}
							   
						   });
					   
				}
			}
		 } 

	}
    
	/**
	 * Metodo para enviar el cidZone a los agentes Edificios
	 */
	private void setMetoEdifices() {
		
		System.out.println("Asignando creencias a los edificios");
		 for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				
				for (IComponentIdentifier person : this.mapa[i][j].getAgents()) {
					
					IFuture<ISetBeliefEdificeService> persona= agent.getComponentFeature(IRequiredServicesFeature.class).searchService(ISetBeliefEdificeService.class, person);
					  
						   persona.addResultListener( new IResultListener<ISetBeliefEdificeService>(){

							@Override
							public void resultAvailable(ISetBeliefEdificeService result) {
								//System.out.println("Asignando Zone al  edificio");
								result.setZone(getAgent().getComponentIdentifier());
								
							}

							@Override
							public void exceptionOccurred(Exception exception) {
								
							}
							   
						   });
					   
				}
			}
		 } 

	}
    
	
	/**
	 * Metodo para enviar la señal de busqueda de objetivos
	 */
	private void startAgents() {
		 System.out.println("Star Agents");
		 for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				
				for (IComponentIdentifier person : this.mapa[i][j].getAgents()) {
					
					IFuture<ISetStartService> persona= agent.getComponentFeature(IRequiredServicesFeature.class).searchService(ISetStartService.class, person);
					  
						//	System.out.println("Tu Ubicacion es: "+c.getX()+" - "+c.getY()+" Agente: "+person.getName());
						   persona.addResultListener( new IResultListener<ISetStartService>(){

							@Override
							public void resultAvailable(ISetStartService result) {
	
									 result.setStart(true);
							}

							@Override
							public void exceptionOccurred(Exception exception) {
								
							}
							   
						   });
					   
				}
			}
		 } 

	}


	/**
	 * Metodo para Asignar a los transeuntes en una Coordenada al Azar
	 */
	public void asignarCoordenadasIniciales(){	
		
		System.out.println("Asignandondo Coordenadas Iniciales...");
		/**
		 * Instanciamos Las esctrucutras del mapa con su respectivo ArrayList
		 */
		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				this.mapa[i][j]= new EstructuraPuntoMapa();
			}
		}
		
		
		/**
		 * Asignacion coordenadas edificios, segun ubicacion elegida en la configuracion
		 */
		for (int i = 0; i < cidsEdifices.size(); i++) {	
			Coordenada c = Traslator.getTraslator().getCoordenada(edConfig[i].getUbicacion());
			this.mapa[c.getX()][c.getY()].getAgents().add(cidsEdifices.get(i));
		}
		
		/**
		 * Asignacion coordenadas a transeutes de manera aleatoria
		 */
		for (int i = 0; i < cidsPerson.size(); i++) {
			this.mapa[Random.getIntRandom(0, Traslator.getTraslator().getTamaño().getX())]
					 [Random.getIntRandom(0, Traslator.getTraslator().getTamaño().getY())]
					 .getAgents().add(cidsPerson.get(i));
		}
		
		
	}
	
	/**
	 * Metodo para enviar las coordenadas inciales a los agentes transeuntes
	 */
	public void sendCoordenadasIniciales(){
    
		 System.out.println("Enviado Ubicacion A los agentes");

	 for (int i = 0; i < this.mapa.length; i++) {
		for (int j = 0; j < this.mapa[i].length; j++) {
			
			for (IComponentIdentifier person : this.mapa[i][j].getAgents()) {
				
				IFuture<ISetBeliefPersonService> persona= agent.getComponentFeature(IRequiredServicesFeature.class).searchService(ISetBeliefPersonService.class, person);
						
					   persona.addResultListener( new IResultListener<ISetBeliefPersonService>(){

						@Override
						public void resultAvailable(ISetBeliefPersonService result) {
           
							 Coordenada c= getCoordenada(person);
						//	 System.out.println("Tu Ubicacion es: "+c.getX()+" - "+c.getY()+" Agente: "+person.getName());
							 if(c!=null){
								 result.setUbicacion(c);
							 }
							
						}

						@Override
						public void exceptionOccurred(Exception exception) {
							
						}
						   
					   });
				   
			}
		}
	 } 

	}
	
	
	/**
	 * Método para Obtener la coordenada de un agente especifico
	 * @param cid
	 * @return Coordenada
	 */
	public Coordenada getCoordenada(IComponentIdentifier cid){

		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {			
				if(this.mapa[i][j].getAgents().contains(cid)){
					return new Coordenada(i,j);
				}
			}
			
		}
		
		return null;
	}
	



	@Override
	public boolean changePosition(Coordenada nueva, IComponentIdentifier cid) {
		
		System.out.println("Cambiando posicion de:");
        
		 if(validateInMap(nueva)){
			   	Coordenada antigua = getCoordenada(cid);
				 this.mapa[antigua.getX()][antigua.getY()].getAgents().remove(cid);
				 this.mapa[nueva.getX()][nueva.getY()].getAgents().add(cid);
				 
				
				 for (IComponentIdentifier c : this.mapa[nueva.getX()][nueva.getY()].getAgents()) {
					 System.out.println(c.getLocalName());
				 }
			
				 return true;
		 }else{
			 
			 System.out.println("Error en la direccion");
			 return false;
		 }
		
	
		 
	
		 
	}
	
	/**
	 * Método que valida si una posicion existe en el mapa o no.
	 * @param Coordenada c
	 * @return boolean
	 */
    private boolean validateInMap(Coordenada c){
	//	System.out.println(c.getX()+" "+this.mapa.length);
	//	System.out.println(c.getY()+" "+this.mapa[0].length);
    	if(c.getX()>=this.mapa.length||c.getY()>= this.mapa[0].length||c.getX()<0||c.getY()<0) return false;
    	
    	return true;
    }
    
    

	
	/**
	 *  Called when the agent is terminated.
	 */
	@AgentKilled
	public void killed()
	{
	
	}
	
   
	@OnOpen
	 public void open(Session session) {
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
	 public void handleMessage(String json, Session session) throws IOException, EncodeException {
		 session.getBasicRemote().sendText(json);
		
	 }
	 
	 
	 
	 
}
