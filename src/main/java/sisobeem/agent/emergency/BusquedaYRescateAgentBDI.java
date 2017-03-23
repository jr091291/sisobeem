package sisobeem.agent.emergency;

import static sisobeem.artifacts.Log.getLog;
import java.util.ArrayList;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import sisobeem.agent.person.PersonAgentBDI;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.print.pojo.EmergencyPojo;
import sisobeem.capabilitys.ComunicarseCapability.MensajeAyuda;
import sisobeem.capabilitys.IdentificarZonasSegurasCapability.FindZonaSegura;
import sisobeem.capabilitys.MoveCapability.Aleatorio;
import sisobeem.capabilitys.MoveCapability.rute;
import sisobeem.capabilitys.SuicidioCapability.HacerNada;
import sisobeem.services.coordinadorService.ISetBeliefService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;
import sisobeem.services.zoneServices.IMapaService;
import sisobeem.services.zoneServices.ISetInformationService;
import sisobeem.utilities.Random;

@Agent
@Description("BusquedaYRescateAgentBDI: encargado de rescatar personas atrapadas")
@RequiredServices({ 
	

})
@ProvidedServices({ 
	 @ProvidedService(type=ISetBeliefService.class),
})
public class BusquedaYRescateAgentBDI extends PersonAgentBDI implements ISetBeliefService{
	

	String tipo = "bomberos";
	
	Coordenada[]puntosSeguros;
	
	ArrayList<IComponentIdentifier> personasAyudadas;
	//IComponentIdentifier cidZone;
	/**
	 * Configuraciones Iniciales
	 */
	@AgentCreated
	public void init()
	{   
		this.myPosition  = new Coordenada(5,5);
		personasAyudadas = new ArrayList<IComponentIdentifier>();
	}
	
	
	/**
	 * Cuerpo Principal del agente
	 */
	@AgentBody
	public void body()
	{
		this.cidZone = null;
	       	
	   
	}
	


	@Override
	public void AyudaMsj(IComponentIdentifier cidPersonHelp) {
		
		
	   
		IFuture<ISetBeliefPersonService> persona = getAgent().getComponentFeature(IRequiredServicesFeature.class)
				.searchService(ISetBeliefPersonService.class, cidPersonHelp);

		persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

			@Override
			public void resultAvailable(ISetBeliefPersonService result) {

				result.setDestiny((puntosSeguros[Random.getIntRandom(0, puntosSeguros.length-1)]));

			}

			@Override
			public void exceptionOccurred(Exception exception) {
				//getLog().setFatal(exception.getMessage());
			}

		});
		
		if(!personasAyudadas.contains(cidPersonHelp)){
			personasAyudadas.add(cidPersonHelp);
			System.out.println("Busqueda: Persona ayudada");
		}
	
		//personasAyudadas.add(cidPersonHelp);
		sendAction(cidPersonHelp);
	}
	
	private void sendAction(IComponentIdentifier a){
		
		IFuture<ISetInformationService> persona = getAgent().getComponentFeature(IRequiredServicesFeature.class)
				.searchService(ISetInformationService.class, cidZone);

		persona.addResultListener(new IResultListener<ISetInformationService>() {

			@Override
			public void resultAvailable(ISetInformationService result) {

				result.setPersonaAyudada(a);

			}

			@Override
			public void exceptionOccurred(Exception exception) {
				//getLog().setFatal(exception.getMessage());
			}

		});
	}


	@Override
	public void CalmaMsj() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void ConfianzaMsj() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void FrustracionMsj() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void HostilMsj() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void PanicoMsj() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void PrimeroAuxMsj(IComponentIdentifier cidPersonAux) {
	 
		IFuture<ISetBeliefPersonService> persona = getAgent().getComponentFeature(IRequiredServicesFeature.class)
				.searchService(ISetBeliefPersonService.class, cidPersonAux);

		persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

			@Override
			public void resultAvailable(ISetBeliefPersonService result) {

				result.setDestiny((puntosSeguros[Random.getIntRandom(0, puntosSeguros.length-1)]));
				result.curar();

			}

			@Override
			public void exceptionOccurred(Exception exception) {
				//getLog().setFatal(exception.getMessage());
			}

		});
		
		
		if(!personasAyudadas.contains(cidPersonAux)){
			personasAyudadas.add(cidPersonAux);
		}
		
		//personasAyudadas.add(cidPersonAux);
		sendAction(cidPersonAux);
	}


	@Override
	public void ResguardoMsj() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void MotivacionMsj() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public String getEstado() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setZone(IComponentIdentifier zone) {
		cidZone = zone;
	}


	@Override
	public void TomaDeDecisiones() {
		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(1000).get();
		  if(this.cidZone!=null){ // Está en el zone
			  
				if (this.rute == null) {
				//	getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": No hago nada por que no tengo ruta");
			    	  getAgent().getComponentFeature(IBDIAgentFeature.class)
						.dispatchTopLevelGoal(this.suicidio.new HacerNada());
				} else {
					if(this.rute[0]==null){
						getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Buscando punto seguro para regresar");

						getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
								this.IdentificarZonas.new FindZonaSegura(this.getAgent(), this.cidZone));
				    
					}else{
						//	getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+ ": Moviendome en ruta");
							getAgent().getComponentFeature(IBDIAgentFeature.class)
									.dispatchTopLevelGoal(super.move.new rute(this.getAgent(), 5,
											this.getPosition(), cidZone, this.rute[0], this.tipo));

							EliminarPositionRoute(0);
					}
			
				} 
		  }else{
			  getAgent().getComponentFeature(IBDIAgentFeature.class)
				.dispatchTopLevelGoal(this.suicidio.new HacerNada());
		  }
	}


	@Override
	public int getSalud() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void Team(IComponentIdentifier parner, double liderazgo) {
		// TODO Auto-generated method stub
		
	}
	
	@Plan(trigger=@Trigger(factchangeds="myDestiny"))
	public void nuevoDestino()
	{   
		//getLog().setDebug("Destino recibido");
		IFuture<IMapaService> persona = getAgent().getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IMapaService.class, cidZone);

		persona.addResultListener(new IResultListener<IMapaService>() {

			@Override
			public void resultAvailable(IMapaService result) {

			rute=result.getRuta(agent.getComponentIdentifier(),myPosition, myDestiny, 3);
               //  for (Coordenada a : rute) {
				//	System.out.println(getAgent().getComponentIdentifier().getLocalName()+": "+a.getX()+" - "+a.getY());
				//}
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				//getLog().setFatal(exception.getMessage());
			}

		});
		
		TomaDeDecisiones();
		
		
		
		
		
	}


	@Override
	public void setPuntosSeguros(Coordenada[] puntos) {
	     this.puntosSeguros = puntos;
		
	}


	@Override
	public void setDestiny(Coordenada c) {
		this.myDestiny = c;
		
		//System.out.println("Busqueda: Destino recibido: "+c.getX()+": "+c.getY());
		
	}
	
	@Override
	public EmergencyPojo getEstadisticas() {

			EmergencyPojo data = new EmergencyPojo("estadistica", "busqueda");
			data.setPersonasAyudadas(this.personasAyudadas.size());
		
		
		return data;
	}

	
	

}
