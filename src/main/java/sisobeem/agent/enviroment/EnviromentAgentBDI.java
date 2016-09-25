package sisobeem.agent.enviroment;

import jadex.bridge.IInternalAccess;
import jadex.micro.annotation.Agent;


@Agent
public abstract class EnviromentAgentBDI {

	@Agent 
	protected IInternalAccess agent;
	

	public IInternalAccess getAgent()
	{
		return agent;
	}

}
