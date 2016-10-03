package sisobeem.agent.enviroment;

import java.util.ArrayList;
import java.util.Map;

import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.micro.annotation.Agent;



@Agent
public abstract class EnviromentAgentBDI {

	@Agent 
	protected IInternalAccess agent;
	
	//Argumentos
    Map <String,Object> arguments;
	
    //listadoAgentes
    ArrayList<IComponentIdentifier> cidsPerson ;
	
  
	public IInternalAccess getAgent()
	{
		return agent;
	}
	
	

}
