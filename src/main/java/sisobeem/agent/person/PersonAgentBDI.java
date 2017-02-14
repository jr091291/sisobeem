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
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.capabilitys.EvacuarCapability;
import sisobeem.capabilitys.FindPersonHelpCapability;
import sisobeem.capabilitys.IdentificarZonasSegurasCapability;
import sisobeem.capabilitys.MoveCapability;
import sisobeem.capabilitys.MoveCapability.Aleatorio;
import sisobeem.capabilitys.ResguardarseCapability;
import sisobeem.capabilitys.SuicidioCapability;
import sisobeem.capabilitys.TeamCapability;
import sisobeem.services.edificeServices.IEvacuarService;
import sisobeem.services.personServices.ITeamService;
import sisobeem.services.personServices.IGetInformationService;
import sisobeem.services.personServices.IReceptorMensajesService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;
import sisobeem.services.personServices.IsetDerrumbeService;
import sisobeem.services.plantServices.IEvacuarPisoService;
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
	@RequiredService(name = "IEvacuarService", type = IEvacuarService.class)
})
@ProvidedServices({
    @ProvidedService(type=ISetStartService.class),
    @ProvidedService(type=IGetInformationService.class),
    @ProvidedService(type=ISetBeliefPersonService.class),
    @ProvidedService(type=IReceptorMensajesService.class),
    @ProvidedService(type=ITeamService.class),
    @ProvidedService(type=IsetDerrumbeService.class)
    })  
public abstract class PersonAgentBDI implements ISetBeliefPersonService,ISetStartService,IReceptorMensajesService,IGetInformationService,IsetDerrumbeService,ITeamService {
    
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
    
    @Belief
    IComponentIdentifier coordinador ;
    
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
	protected ArrayList<Coordenada> rute;
	
	@Belief
	Coordenada myDestiny;

	@Belief
	protected Boolean contextCaminar;
	
	@Belief
	int velocidad;
	
	@Belief
	protected double intensidadSismo;
	
    @Belief
    protected Boolean vivo = true;
	public String estado;
	
	@Belief
	protected ArrayList<IComponentIdentifier> cidsPeopleHelp;
	
	@Belief
	protected ArrayList<IComponentIdentifier> team;
	
	@Belief
	protected Boolean contextTeam = false;
	
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
	
	@Capability(beliefmapping=@Mapping(target="team", value = "team"))
	protected TeamCapability grupo = new TeamCapability();
	
	@Capability
	protected EvacuarCapability EvacuarEdificio = new EvacuarCapability();

	@Capability(beliefmapping=@Mapping(target="salud", value = "salud"))
	protected SuicidioCapability suicidio = new SuicidioCapability();
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
		
		
		//this.vivo = true;
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
		
		//getLog().setDebug(this.getAgent().getComponentIdentifier().getLocalName()+" : "+this.conocimientoZona);
		
		if(this.cidPlant==null){
			//getLog().setDebug("Estoy en la calle");
			
			//contextCaminar = true;
			
			//getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(IdentificarZonas.new FindZonaSegura(this.getAgent(),this.cidZone));
     	}else{
     	    //	getLog().setDebug("Estoy en un edificio");
      		//getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(EvacuarEdificio.new Evacuar(this.getAgent(),this.conocimientoZona,this.cidPlant,this.cidEdifice));
          
     	//	getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(Suicidio.new SaltarDelEdificio(this.getAgent(),this.cidPlant));

     	}
		
		
		
	}
	
	@Plan(trigger=@Trigger(factchangeds="myDestiny"))
	public void nuevoDestino()
	{   
		//getLog().setDebug("Entro");
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
	
	  if(this.myDestiny!=null&&this.cidZone!=null&&this.vivo==true){
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
	
	
	@Override
	public void recibirDerumbe(int daño) {
	     this.salud = this.salud - daño;
	}
	
	@Plan(trigger=@Trigger(factchangeds="salud"))
	private void ValidarVida(){
		if(this.salud<1){
			this.vivo=false;
			getLog().setInfo("EL agente: "+getAgent().getComponentIdentifier().getLocalName()+" está muerto");
		}
	}
	

	@Override
	public void setToCoordinador(IComponentIdentifier coordinador) {
		this.coordinador = coordinador;
	}
	

	@Override
	public double getLiderazgo() {
		return liderazgo;
	}

	
	@Override
	public String getEstado() {
		return this.estado;
	}


	@Override
	public void entrar(IComponentIdentifier agente) {
		if(!this.team.contains(agente))this.team.add(agente);
	}


	@Override
	public void salir(IComponentIdentifier agente) {
		if(this.team.contains(agente))this.team.remove(agente);
	}


	@Override
	public void setRute(ArrayList<Coordenada> rute) {
		// TODO Auto-generated method stub
		
	}

	
	
	

	

	
}
