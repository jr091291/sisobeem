package sisobeem.agent.enviroment;

import java.util.ArrayList;

import jadex.bdiv3.annotation.Belief;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.plantServices.ISetBelifePlantService;


@Agent
@Description("Abstrae el comportamiento de una Piso")
@RequiredServices({
	@RequiredService(name="ISetEnviromentService", type=ISetBeliefPersonService.class)
})
@ProvidedServices({
	@ProvidedService(name="ISetBelifePlantService", type =ISetBelifePlantService.class),
})
public class PlantAgentBDI extends EnviromentAgentBDI implements ISetBelifePlantService{

    @Belief
	IComponentIdentifier cidEdifice;
    @Belief
	IComponentIdentifier cidZone;
	
	@SuppressWarnings("unchecked")
	@AgentCreated
	public void init()
	{
		// Accedemos a los argumentos del agente
    	this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();
    	
    	//Obtenemos los cid de las personas en la Zona
        cidsPerson = (ArrayList<IComponentIdentifier>) arguments.get("cidsPerson");
        
        sendBeliefToPerson();
        	
    }
	
	@AgentBody
	public void body(){
	
	}
	

	/**
	 * Metodo para enviar los enviroment a la persona
	 */
	public void sendBeliefToPerson(){
    
			
			for (IComponentIdentifier person : this.cidsPerson) {
				
				IFuture<ISetBeliefPersonService> personService= agent.getComponentFeature(IRequiredServicesFeature.class).searchService(ISetBeliefPersonService.class, person);
				  
					//	System.out.println("Tu Ubicacion es: "+c.getX()+" - "+c.getY()+" Agente: "+person.getName());
					   personService.addResultListener( new IResultListener<ISetBeliefPersonService>(){
						
						@Override
						public void resultAvailable(ISetBeliefPersonService result) { 
								 result.setPlant(getAgent().getComponentIdentifier());
								 result.setEdifice(getCidEdifice());
								 result.setZone(getCidZone());
							
						}
						
						@Override
						public void exceptionOccurred(Exception exception) {

						}
						   
					   });
				   
			}
		}

	@Override
	public void setEdifice(IComponentIdentifier cidEdificio) {
		this.cidEdifice = cidEdificio;
		
	}

	@Override
	public void setZone(IComponentIdentifier cidZone) {
		this.cidZone = cidZone;
		sendBeliefToPerson();
	}


	public IComponentIdentifier getCidEdifice() {
		return cidEdifice;
	}

	public void setCidEdifice(IComponentIdentifier cidEdifice) {
		this.cidEdifice = cidEdifice;
	}

	public IComponentIdentifier getCidZone() {
		return cidZone;
	}

	public void setCidZone(IComponentIdentifier cidZone) {
		this.cidZone = cidZone;
	}


}


