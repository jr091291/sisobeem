package sisobeem.agent.person;

import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Argument;
import jadex.micro.annotation.Arguments;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import sisobeem.artifacts.Traslator;
import sisobeem.artifacts.sisobeem.config.Ubicacion;
import sisobeem.services.ISetUbicacionInicialService;

@Agent

@Description("Agente vicil, encargados de vivir la experiencia del terremoto.")
@ProvidedServices(@ProvidedService(type=ISetUbicacionInicialService.class))  
public class CivilAgentBDI extends PersonAgentBDI implements ISetUbicacionInicialService {
	
    
	 
        	//-------- methods --------S

	@AgentCreated
	public void init()
	{   
		// System.out.println("Agente :"+agent.getComponentIdentifier().getLocalName()+" creado");
	    
	}
	@AgentBody
	public void body()
	{
		
		
	}
	@Override
	public void setUbicacionInicialService(Ubicacion ubicacion) {
		this.myPosition = Traslator.getTraslator().getCoordenada(ubicacion);
		
	}
	
	

	
	
}
