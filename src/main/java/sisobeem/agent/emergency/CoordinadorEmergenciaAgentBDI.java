package sisobeem.agent.emergency;

import java.util.ArrayList;
import java.util.Map;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import sisobeem.services.personServices.ISetStartService;

@Agent
@Description("CoordinadorEmergenciaAgentBDI: encargado de coordinar los elementos para atender la emergencia")
@RequiredServices({ 
	

})
@ProvidedServices({ 
	   @ProvidedService(type=ISetStartService.class)
})
public class CoordinadorEmergenciaAgentBDI implements ISetStartService{

	
	@Belief
	Boolean start;
	@Agent
	protected IInternalAccess agent;

	/** The bdi api. */
	@AgentFeature
	protected IBDIAgentFeature bdi;
	

	ArrayList<IComponentIdentifier> cidsBusqueda,cidsSeguridad,cidsSalud;
	
	// Argumentos
	Map<String, Object> arguments;
		
		
	/**
	 * Configuraciones Iniciales
	 */
	@AgentCreated
	public void init()
	{   
		// Accedemos a los argumentos del agente
		this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();
		// Obtenemos los cid de las personas en la Zona
		cidsBusqueda = (ArrayList<IComponentIdentifier>) arguments.get("cidsBusqueda");
		cidsSeguridad = (ArrayList<IComponentIdentifier>) arguments.get("cidsSeguridad");
		cidsSalud = (ArrayList<IComponentIdentifier>) arguments.get("cidsSalud");
		
				
	}
	
	
	/**
	 * Cuerpo Principal del agente
	 */
	@AgentBody
	public void body()
	{
	       	
	   
	}


	@Override
	public void setStart(Boolean s) {
			start = s;	
	}

}
