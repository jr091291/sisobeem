/**
 * 
 */
package sisobeem.agent.person;

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
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Random;
import sisobeem.capabilitys.MoveCapability;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;


/**
 * Abstrae el comportamiento de una persona
 * @author Erley
 *
 */


@Agent
@Description("Abstrae el comportamiento de una persona")
@RequiredServices({

})
@ProvidedServices({
    @ProvidedService(type=ISetStartService.class),
    @ProvidedService(type=ISetBeliefPersonService.class)})  
public abstract class PersonAgentBDI implements ISetBeliefPersonService,ISetStartService {
    
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
	

	public String estado;
	
	/**
	 * Capacidades
	 */
	@Capability(beliefmapping=@Mapping(target="myPosition", value = "myPosition"))
	protected MoveCapability move = new MoveCapability();
	
	

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
		//System.out.println("Entró en el trigger");
		if(this.cidPlant==null){
    		getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(move.new Aleatorio(this.getAgent(),1, this.getPosition(),cidZone));
    	}
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
	
	

	
}
