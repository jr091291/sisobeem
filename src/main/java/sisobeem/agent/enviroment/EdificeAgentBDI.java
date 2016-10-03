package sisobeem.agent.enviroment;

import java.util.ArrayList;

import jadex.bdiv3.annotation.Belief;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Traslator;
import sisobeem.artifacts.sisobeem.config.Ubicacion;
import sisobeem.services.ISetUbicacionInicialService;

@Agent
@ProvidedServices(@ProvidedService(type=ISetUbicacionInicialService.class))
public class EdificeAgentBDI extends EnviromentAgentBDI implements ISetUbicacionInicialService{
	
	ArrayList<IComponentIdentifier> cidsPlants  = new ArrayList<IComponentIdentifier>();
	
	@Belief
	Coordenada myPosition;
	
	@SuppressWarnings("unchecked")
	@AgentCreated
	public void init()
	{
		 System.out.println("Agente :"+agent.getComponentIdentifier().getLocalName()+" creado");
		// Accedemos a los argumentos del agente
	    this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();
				
		//Obtenemos los cid de las personas en la Zona
		cidsPlants = (ArrayList<IComponentIdentifier>) arguments.get("cidsPlants");
		
		System.out.println("Este edificio cuenta con : "+cidsPlants.size()+"Pisos");
	}

	@AgentBody
	public void body(){
	
	}
    
	
	@Override
	public void setUbicacionInicialService(Ubicacion ubicacion) {
                this.myPosition = Traslator.getTraslator().getCoordenada(ubicacion);
	}
}


