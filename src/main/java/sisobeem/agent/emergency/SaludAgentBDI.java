package sisobeem.agent.emergency;

import static sisobeem.artifacts.Log.getLog;
import java.util.ArrayList;

import jadex.bdiv3.annotation.Capability;
import jadex.bdiv3.annotation.Mapping;
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
import sisobeem.capabilitys.MoveCapability;
import sisobeem.capabilitys.ComunicarseCapability.MensajeDeMotivacion;
import sisobeem.capabilitys.EmergencyCapability;
import sisobeem.capabilitys.MoveCapability.Aleatorio;
import sisobeem.capabilitys.MoveCapability.rute;
import sisobeem.capabilitys.SuicidioCapability.HacerNada;
import sisobeem.services.coordinadorService.ISetBeliefService;
import sisobeem.services.personServices.IGetInformationService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.zoneServices.IGetInformationZoneService;
import sisobeem.services.zoneServices.IMapaService;

@Agent
@Description("SaludAgentBDI: encargado de mejorar la salud de los heridos")
@RequiredServices({ 
	

})
@ProvidedServices({ 
	 @ProvidedService(type=ISetBeliefService.class),
})
public class SaludAgentBDI extends PersonAgentBDI implements ISetBeliefService {

	@Capability
	protected EmergencyCapability emergency = new EmergencyCapability();
	
	ArrayList<IComponentIdentifier> pacientes;
	ArrayList<IComponentIdentifier> atendidos;
	Coordenada[]puntosSeguros;
	String tipo = "salud";
	/**
	 * Configuraciones Iniciales
	 */
	@AgentCreated
	public void init()
	{   
		this.myPosition  = new Coordenada(5,50);
		this.pacientes = new ArrayList<IComponentIdentifier>();
		this.atendidos = new ArrayList<IComponentIdentifier>();
		
	}
	
	
	/**
	 * Cuerpo Principal del agente
	 */
	@AgentBody
	public void body()
	{
	       	
	   
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
					//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": No hago nada por que no tengo ruta");
			    	  getAgent().getComponentFeature(IBDIAgentFeature.class)
						.dispatchTopLevelGoal(this.suicidio.new HacerNada());
				} else {
					if(this.rute[0]==null){
							//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Estoy en mi destino");

				    	this.consultarPersonasHeridas();
				    	
				    	if(this.pacientes.size()>0){
				    		
				    		getAgent().getComponentFeature(IBDIAgentFeature.class)
							.dispatchTopLevelGoal(emergency.new CurarHeridos(this.getAgent(),this.pacientes.get(0)));
				    		atendidos.add(this.pacientes.get(0));
				    		pacientes.remove(0);
				    		
				    	}else{
				    		 getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(this.suicidio.new HacerNada());
				    	}
					}else{
						//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+ ": Moviendome en ruta");
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
	public void AyudaMsj(IComponentIdentifier cidPersonHelp) {
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
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
	public int getSalud() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void Team(IComponentIdentifier parner, double liderazgo) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Plan(trigger = @Trigger(goalfinisheds ={sisobeem.capabilitys.ComunicarseCapability.MensajeAyuda.class,
            sisobeem.capabilitys.ComunicarseCapability.MensajeDeFrustracion.class,
            sisobeem.capabilitys.ComunicarseCapability.MensajeDeHostilidad.class,
            sisobeem.capabilitys.ComunicarseCapability.MensajeDePanico.class,
            sisobeem.capabilitys.ComunicarseCapability.MensajeDeCalma.class,
            sisobeem.capabilitys.ComunicarseCapability.MensajeDeConfianza.class,
            sisobeem.capabilitys.ComunicarseCapability.MensajeDeMotivacion.class,
            sisobeem.capabilitys.ComunicarseCapability.MensajeDePrimerosAux.class,
            sisobeem.capabilitys.ComunicarseCapability.MensajeDeResguardo.class,
            sisobeem.capabilitys.EvacuarCapability.Evacuar.class,
            sisobeem.capabilitys.FindPersonHelpCapability.FindPerson.class,
            sisobeem.capabilitys.FindSalidasDisponiblesCapability.salidas.class,
            sisobeem.capabilitys.IdentificarZonasSegurasCapability.FindZonaSegura.class,
            sisobeem.capabilitys.MoveCapability.Aleatorio.class,
            sisobeem.capabilitys.MoveCapability.rute.class,
            sisobeem.capabilitys.ResguardarseCapability.Resguardarse.class,
            sisobeem.capabilitys.SuicidioCapability.SaltarDelEdificio.class,
            sisobeem.capabilitys.SuicidioCapability.HacerNada.class,
            sisobeem.capabilitys.TeamCapability.MensajeDeTeam.class,
            sisobeem.capabilitys.TeamCapability.AddPersonNeedHelp.class,
            sisobeem.capabilitys.EmergencyCapability.CurarHeridos.class,
            sisobeem.capabilitys.TeamCapability.EnviarRuta.class}))
    public void endgoals() {
        this.TomaDeDecisiones();
    }


	@Override
	public void setDestiny(Coordenada c) {
		this.myDestiny = c;

	//	System.out.println("Salud: Destino recibido: "+c.getX()+": "+c.getY());
		//System.out.println("Busqueda: Destino recibido: "+c.getX()+": "+c.getY());

	}
	
	@Plan(trigger=@Trigger(factchangeds="myDestiny"))
	public void nuevoDestino()
	{   
		//getLog().setDebug("Entro");
		IFuture<IMapaService> persona = getAgent().getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IMapaService.class, cidZone);

		persona.addResultListener(new IResultListener<IMapaService>() {

			@Override
			public void resultAvailable(IMapaService result) {

			rute=result.getRuta(agent.getComponentIdentifier(),myPosition, myDestiny, 3);
	
		//	String x ="";
			//for (Coordenada a : rute) {
				//x=x+": "+a.getX()+" - "+a.getY()+" ** ";
		//	}
			//System.out.println(getAgent().getComponentIdentifier().getLocalName()+x);

			}

			@Override
			public void exceptionOccurred(Exception exception) {
				//getLog().setFatal(exception.getMessage());
			}

		});
		
		TomaDeDecisiones();
		
		
		
		
		
	}
	
	
	private void consultarPersonasHeridas(){

		IFuture<IGetInformationZoneService> persona = getAgent().getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IGetInformationZoneService.class, cidZone);

		persona.addResultListener(new IResultListener<IGetInformationZoneService>() {

			@Override
			public void resultAvailable(IGetInformationZoneService result) {

				ArrayList<IComponentIdentifier>lista = result.getAgentsHeridos(agent.getComponentIdentifier());
				
				for (IComponentIdentifier a : lista) {
					if(!pacientes.contains(a)){
						pacientes.add(a);
						System.out.println("ENFERMO!");
					}
				}

			}

			@Override
			public void exceptionOccurred(Exception exception) {
				//getLog().setFatal(exception.getMessage());
			}

		});

	}

	
	@Override
	public EmergencyPojo getEstadisticas() {

			EmergencyPojo data = new EmergencyPojo("estadistica", "seguridad");
			data.setPacientes(this.pacientes.size());
			data.setPersonasAtendidas(this.atendidos.size());
		
		
		return data;
	}



	@Override
	public void setPuntosSeguros(Coordenada[] puntos) {
	     this.puntosSeguros = puntos;
		
	}

	
}
