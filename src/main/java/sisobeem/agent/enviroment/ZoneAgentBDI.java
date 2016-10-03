package sisobeem.agent.enviroment;

import java.util.ArrayList;
import jadex.bdiv3.annotation.Belief;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.service.annotation.Service;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.EstructuraPuntoMapa;
import sisobeem.artifacts.Random;
import sisobeem.artifacts.Traslator;
import sisobeem.artifacts.sisobeem.config.Bounds;
import sisobeem.artifacts.sisobeem.config.EdificesConfig;
import sisobeem.artifacts.sisobeem.config.SimulationConfig;
import sisobeem.services.ISetUbicacionInicialService;

@Agent
@Service
@RequiredServices({
	@RequiredService(name="ISetUbicacionInicialService", type=ISetUbicacionInicialService.class)
})
public class ZoneAgentBDI extends EnviromentAgentBDI {

	ArrayList<IComponentIdentifier> cidsEdifices = new ArrayList<IComponentIdentifier>();
	EstructuraPuntoMapa [][] mapa;
	
	/**
	 * Datos del sismo
	 */
	@Belief
    SimulationConfig simulacionConfig;
	EdificesConfig[] edConfig;


	@AgentCreated
	public void init() {
		System.out.println("Agente " + agent.getComponentIdentifier().getLocalName() + " creado");

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
		
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[i].length; j++) {
				this.mapa[i][j] = new EstructuraPuntoMapa();
			}
		}
	  	
	}
	
	public void AsignarCoordenadasIniciales(){	
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
	 * Metodo para enviar las coordenadas inciales a los agentes edificios y transeuntes
	 */
	public void sendCoordenadasIniciales(){
		/**
					
			agent.getComponentFeature(IRequiredServicesFeature.class).searchService(IRegisterInEnviromentService.class, this.cidEnviroment)
			.addResultListener(new DefaultResultListener<IRegisterInEnviromentService>()
		{
			public void resultAvailable(IRegisterInEnviromentService enviroment)
			{
				System.out.println("Me estoy registrando : "+agent.getComponentIdentifier().getLocalName());
				enviroment.registerToMe(agent.getComponentIdentifier());
			}
		});	
		    
		**/
	}
	
	
	

	@AgentBody
	public void body() {

		System.out.println("Mis agentes");
		for (IComponentIdentifier cid : cidsPerson) {
			System.out.println(cid.getLocalName());
		}
		
		
		System.out.println("Mis edificios");
		for (IComponentIdentifier cid : cidsEdifices) {
			System.out.println(cid.getLocalName());
		}


	}

}
