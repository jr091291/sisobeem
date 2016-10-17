package sisobeem.agent.person;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedServices;

@Agent
@Description("Agente vicil, encargados de vivir la experiencia del terremoto.")
@ProvidedServices({
  })  
public class CivilAgentBDI extends PersonAgentBDI  {
	

	/**
	 * Configuraciones Iniciales
	 */
	@AgentCreated
	public void init()
	{   
		
		
	}
	
	
	/**
	 * Cuerpo Principal del agente
	 */
	@AgentBody
	public void body()
	{
	     
	    	
	   
	}

	/**
	 *  Repetir movimiento aletarorio
	 */
	@Plan(trigger=@Trigger(factchangeds="myPosition"))
	public void Start()
	{   
		//System.out.println("Entró en el trigger");
		if(this.cidPlant==null){
    		getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(),1, this.getPosition(),super.cidZone));
    	}
	}


	
	
}
