package sisobeem.agent.enviroment;

import java.util.ArrayList;

import jadex.bridge.IComponentIdentifier;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;

@Agent
public class PlantAgentBDI extends EnviromentAgentBDI{

	IComponentIdentifier cidEdifice;
	
	@AgentCreated
	public void init()
	{
		 System.out.println("Agente :"+agent.getComponentIdentifier().getLocalName()+" creado");
		// Accedemos a los argumentos del agente
    	this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();
    	
    	//Obtenemos los cid de las personas en la Zona
        cidsPerson = (ArrayList<IComponentIdentifier>) arguments.get("cidsPerson");
        
        
			System.out.println("personas en esta piso :"+cidsPerson.size());
		
    }
	
	@AgentBody
	public void body(){
	
	}


}


