package sisobeem.agent.emergency;

import static sisobeem.artifacts.Log.getLog;

import java.util.ArrayList;
import java.util.Map;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.services.coordinadorService.ISetZone;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;
import sisobeem.services.zoneServices.IGetInformationZoneService;

@Agent
@Description("CoordinadorEmergenciaAgentBDI: encargado de coordinar los elementos para atender la emergencia")
@RequiredServices({ 
	

})
@ProvidedServices({ 
	   @ProvidedService(type=ISetStartService.class),
	   @ProvidedService(type=ISetZone.class)
})
public class CoordinadorEmergenciaAgentBDI implements ISetStartService, ISetZone{

	
	@Belief
	Boolean start;
	@Agent
	protected IInternalAccess agent;
	
	

	/** The bdi api. */
	@AgentFeature
	protected IBDIAgentFeature bdi;
	
	@Belief 
	IComponentIdentifier cidZone;
	

	ArrayList<IComponentIdentifier> cidsBusqueda,cidsSeguridad,cidsSalud;
	
	ArrayList<Coordenada> puntosSeguros;
	
	ArrayList<Coordenada> puntosInseguros;
	
	
	// Argumentos
	Map<String, Object> arguments;
		
		
	/**
	 * Configuraciones Iniciales
	 */
	@SuppressWarnings("unchecked")
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
			setStartToAgents();
			
			getLog().setDebug("Coordinador Emergencia Iniciado...");
			
			this.getPuntosSeguros();
			this.getPuntosInseguros();
			
			agent.getComponentFeature(IExecutionFeature.class).waitForDelay(4000).get();
			
			for (Coordenada c : puntosSeguros) {
				System.out.println(c.getX()+"-"+c.getY());
			}
			
			System.out.println("*****************************************************");
			
			for (Coordenada c : puntosInseguros) {
				System.out.println(c.getX()+"-"+c.getY());
			}
	}
	
	public void getPuntosSeguros(){
		
		IFuture<IGetInformationZoneService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IGetInformationZoneService.class, cidZone);
		persona.addResultListener(new IResultListener<IGetInformationZoneService>() {

			@Override
			public void resultAvailable(IGetInformationZoneService result) {

				puntosSeguros = result.getPuntosSeguros();
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setWarn(exception.getMessage());
			}

		});
	}
	
	
	public void getPuntosInseguros(){
		
		IFuture<IGetInformationZoneService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IGetInformationZoneService.class, cidZone);
		persona.addResultListener(new IResultListener<IGetInformationZoneService>() {

			@Override
			public void resultAvailable(IGetInformationZoneService result) {

				puntosInseguros = result.getPuntosInseguros();
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setWarn(exception.getMessage());
			}

		});
	}
	
	
	private void setStartToAgents(){

		for (IComponentIdentifier person : this.cidsBusqueda) {

			IFuture<ISetStartService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetStartService.class, person);
			persona.addResultListener(new IResultListener<ISetStartService>() {

				@Override
				public void resultAvailable(ISetStartService result) {

					result.setStart(true);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
				}

			});

		}
		
		for (IComponentIdentifier person : this.cidsSalud) {

			IFuture<ISetStartService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetStartService.class, person);
			persona.addResultListener(new IResultListener<ISetStartService>() {

				@Override
				public void resultAvailable(ISetStartService result) {

					result.setStart(true);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
				}

			});

		}
		
		for (IComponentIdentifier person : this.cidsSeguridad) {

			IFuture<ISetStartService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetStartService.class, person);
			persona.addResultListener(new IResultListener<ISetStartService>() {

				@Override
				public void resultAvailable(ISetStartService result) {

					result.setStart(true);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
				}

			});

		}
	}
	
	private void sendBelief(){
		sendBeliefToBusqueda();
		sendBeliefToSalud();
		sendBeliefToSeguridad();
	}
	
    private void sendBeliefToBusqueda(){
		
		for (IComponentIdentifier person : this.cidsBusqueda) {

			IFuture<ISetBeliefPersonService> personService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			personService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setZone(cidZone);
					result.setToCoordinador(agent.getComponentIdentifier());

				}

				@Override
				public void exceptionOccurred(Exception exception) {

				}

			});

		}
	}
	
	private void sendBeliefToSalud(){
		
		for (IComponentIdentifier person : this.cidsSalud) {

			IFuture<ISetBeliefPersonService> personService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			personService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setZone(cidZone);
					result.setToCoordinador(agent.getComponentIdentifier());

				}

				@Override
				public void exceptionOccurred(Exception exception) {

				}

			});

		}
	}
	
	private void sendBeliefToSeguridad(){
		
		for (IComponentIdentifier person : this.cidsSeguridad) {

			IFuture<ISetBeliefPersonService> personService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			personService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setZone(cidZone);
					result.setToCoordinador(agent.getComponentIdentifier());

				}

				@Override
				public void exceptionOccurred(Exception exception) {

				}

			});

		}
	}

	@Override
	public ArrayList<IComponentIdentifier> setZone(IComponentIdentifier zone) {
	    this.cidZone = zone;
	    
	    sendBelief();
	    
	    ArrayList<IComponentIdentifier> agents = new ArrayList<IComponentIdentifier>();
	    
	    agents.addAll(this.cidsSeguridad);
	    agents.addAll(this.cidsSalud);
	    agents.addAll(this.cidsBusqueda);
	    
	    return agents;
	}
	

	

}
