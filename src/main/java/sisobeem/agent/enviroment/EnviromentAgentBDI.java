package sisobeem.agent.enviroment;

import java.util.ArrayList;
import java.util.Map;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.Description;



@Agent
@Description("Abstrae el comportamiento de un ambiente")
public abstract class EnviromentAgentBDI {

	@Agent 
	protected IInternalAccess agent;
	
	/** The bdi api. */
	@AgentFeature
	protected IBDIAgentFeature bdi;
    
	   
	@Belief
	Boolean start;
	
	//Argumentos
    Map <String,Object> arguments;
	
    //listadoAgentes
    ArrayList<IComponentIdentifier> cidsPerson ;
    
	@Belief
	protected double intensidadSismo;
	
  
	public IInternalAccess getAgent()
	{
		return agent;
	}
	

	

}
