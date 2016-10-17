package sisobeem.agent.enviroment;

import java.util.ArrayList;
import java.util.Map;
import jadex.bdiv3.annotation.Belief;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.Description;



@Agent
@Description("Abstrae el comportamiento de un ambiente")
public abstract class EnviromentAgentBDI {

	@Agent 
	protected IInternalAccess agent;
    
	   
	@Belief
	Boolean start;
	
	//Argumentos
    Map <String,Object> arguments;
	
    //listadoAgentes
    ArrayList<IComponentIdentifier> cidsPerson ;
	
  
	public IInternalAccess getAgent()
	{
		return agent;
	}
	

	

}
