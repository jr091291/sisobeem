/**
 * 
 */
package sisobeem.agent.person;

import java.util.ArrayList;
import java.util.Map;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Capability;
import jadex.bdiv3.annotation.Mapping;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.capabilitys.EvacuarCapability;
import sisobeem.capabilitys.FindPersonHelpCapability;
import sisobeem.capabilitys.IdentificarZonasSegurasCapability;
import sisobeem.capabilitys.MoveCapability;
import sisobeem.capabilitys.MoveCapability.Aleatorio;
import sisobeem.capabilitys.ResguardarseCapability;
import sisobeem.services.personServices.IGetInformationService;
import sisobeem.services.personServices.IReceptorMensajesService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;
import sisobeem.services.plantServices.ISetBelifePlantService;
import sisobeem.services.zoneServices.IMapaService;
import sisobeem.utilities.Random;
import static sisobeem.artifacts.Log.getLog;

/**
 * Abstrae el comportamiento de una persona
 * @author Erley
 *
 */


@SuppressWarnings("unused")
@Agent
@Description("Abstrae el comportamiento de una persona")
@RequiredServices({
	
})
@ProvidedServices({
    @ProvidedService(type=ISetStartService.class),
    @ProvidedService(type=IGetInformationService.class),
    @ProvidedService(type=ISetBeliefPersonService.class),
    @ProvidedService(type=IReceptorMensajesService.class)})  
public abstract class PersonAgentBDI implements ISetBeliefPersonService,ISetStartService,IReceptorMensajesService,IGetInformationService {
    
	@Belief
	Boolean start;
	
	@Agent 
	public IInternalAccess agent;
	

   /**
    * Creencias
    */
    Map <String,Object> arguments;
	
    @Belief
    IComponentIdentifier cidZone ;
    
    @Belief
    IComponentIdentifier cidEdifice ;
   
    @Belief
    IComponentIdentifier cidPlant ;
    
	// Emociones
	@Belief
	double confianza,miedo,tristeza,enojo;
	
	//Caracteristicas internas
	@Belief 
	int edad,pshyco,salud,formacion,gregarismo;
	
	@Belief 
	double liderazgo,conocimientoZona,riesgo;
	
	@Belief 
	Coordenada myPosition;
	
	@Belief
	Coordenada myDestiny;

	@Belief
	protected Boolean contextCaminar;
	
	@Belief
	int velocidad;
	
	@Belief
	protected double intensidadSismo;
	
    
	public String estado;
	
	@Belief
	protected ArrayList<IComponentIdentifier> cidsPeopleHelp;
	
	/**
	 * Capacidades
	 */
	@Capability(beliefmapping=@Mapping(target="myPosition", value = "myPosition"))
	protected MoveCapability move = new MoveCapability();
	
	@Capability(beliefmapping=@Mapping(target="myDestiny", value = "myDestiny"))
	protected IdentificarZonasSegurasCapability IdentificarZonas = new IdentificarZonasSegurasCapability();
	
	@Capability(beliefmapping=@Mapping(target="contextCaminar", value = "contextCaminar"))
	protected ResguardarseCapability resguardarse = new ResguardarseCapability();
	
	@Capability(beliefmapping=@Mapping(target="cidsPeopleHelp", value = "cidsPeopleHelp"))
	protected FindPersonHelpCapability FindPersonHelpCapability = new FindPersonHelpCapability();
	
	@Capability
	protected EvacuarCapability EvacuarEdificio = new EvacuarCapability();

	/**
	 *  Get the agent.
	 *  @return The agent.
	 */
	public IInternalAccess getAgent()
	{
		return agent;
		
	}
	
    
	/**
	 * Método que inicializa las creencias de la persona
	 */
	public void iniciarCreencias(){
		
		this.velocidad=1;
		// Accedemos a los argumentos del agente
    	this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();
		//Creencias Enviadas en la configuracion
		this.edad = (int) this.arguments.get("edad");
		this.conocimientoZona = (double) this.arguments.get("conocimientoZona");
		this.salud = (int) this.arguments.get("salud");
		this.liderazgo = (double) this.arguments.get("liderazgo");
		
		//Creencias estado base normal
		this.confianza = 3;
		this.miedo = 0;
		this.tristeza=0;
		this.enojo=0;
		this.pshyco=0;
		this.formacion = Random.getIntRandom(0, 5);
		this.gregarismo = Random.getIntRandom(0, 5);
		this.riesgo = 0;
		this.estado="Nomal";
		
		
		
	}
	
	/**
	 * Metodo get
	 * @return
	 */
	public Coordenada getPosition(){
		return myPosition;
		
	}
	
	
	/**
	 *  Start, Metodo para Iniciar la busqueda de objetivos en el agente.
	 */
	@Plan(trigger=@Trigger(factchangeds="start"))
	public void start()
	{   
		
		if(this.cidPlant==null){
			//getLog().setDebug("Estoy en la calle");
			
			contextCaminar = true;
			
			getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(IdentificarZonas.new FindZonaSegura(this.getAgent(),this.cidZone));
     	}else{
     		//getLog().setDebug("Estoy en un edificio");
      		//getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(FindPersonHelpCapability.new FindPerson(this.getAgent(),this.cidPlant));
          
     		

     	}
		
		
		
	}
	
	@Plan(trigger=@Trigger(factchangeds="myDestiny"))
	public void nuevoDestino()
	{   
		//getLog().setDebug("Nuevo destino Recibido");
		solicitarRuta();
		
	}
	
	
	/**
	 * Set Beliefs
	 */
	@Override
	public void setUbicacion(Coordenada coordenada) {
		this.myPosition = coordenada;
		//System.out.println(agent.getComponentIdentifier().getLocalName()+", Mi posicion es: "+this.myPosition.getX()+" - "+this.myPosition.getY());
		this.iniciarCreencias();
		//System.out.println("Agente :"+agent.getComponentIdentifier().getLocalName()+" OK");
	}


	@Override
	public void setEdifice(IComponentIdentifier cidEdificio) {
		this.cidEdifice = cidEdificio;
	}


	@Override
	public void setPlant(IComponentIdentifier cidPlant) {
		this.cidPlant = cidPlant;
		
	}
	
	@Override
	public void setStart(Boolean s) {
		this.start = true;
	}

	@Override
	public void setZone(IComponentIdentifier zone) {
		cidZone = zone;
	}

	@Override
	public void setSismo(double intensidad) {
		this.intensidadSismo=intensidad;
		//getLog().setDebug(""+intensidad);
	}
	
	
	/**
	 * Método que solicita una ruta si existe la creencia myDestiny
	 */
	public void solicitarRuta(){
	
	  if(this.myDestiny!=null&&this.cidZone!=null){
			IFuture<IMapaService> zone = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IMapaService.class, this.cidZone);
			zone.addResultListener(new IResultListener<IMapaService>() {

				@Override
				public void resultAvailable(IMapaService result) {
					result.getRuta(getAgent().getComponentIdentifier(), myDestiny);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});
	  }

	}
	
	
	
	
	

	

	
}
