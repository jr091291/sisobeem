/**
 * 
 */
package sisobeem.agent.person;

import java.util.Map;

import jadex.bdiv3.annotation.Belief;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.Binding;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;


/**
 * Abstrae el comportamiento de una persona
 * @author Erley
 *
 */
@Agent

/**
@RequiredServices({
	@RequiredService(name="IRegisterInEnviromentService", type=IRegisterInEnviromentService.class, multiple=true,
		binding=@Binding(dynamic=true, scope=Binding.SCOPE_PLATFORM))
})

**/
public abstract class PersonAgentBDI {
    
	@Agent 
	protected IInternalAccess agent;
	

    //Argumentos
    Map <String,Object> arguments;
	
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
	
	public Coordenada getPosition(){
		return myPosition;
		
	}
	
	/**
	 *  Get the agent.
	 *  @return The agent.
	 */
	public IInternalAccess getAgent()
	{
		return agent;
	}
	
	
	public void registrar(){
		
		
		
		//BuildSimulation.getInstance().registrarPersona(agent.getComponentIdentifier());
		
		/*	    
		if(this.cidEnviroment==null)
		{
		  System.out.println("No se encuentra el identificador de este componente");
		}else{
			
			agent.getComponentFeature(IRequiredServicesFeature.class).searchService(IRegisterInEnviromentService.class, this.cidEnviroment)
			.addResultListener(new DefaultResultListener<IRegisterInEnviromentService>()
		{
			public void resultAvailable(IRegisterInEnviromentService enviroment)
			{
				System.out.println("Me estoy registrando : "+agent.getComponentIdentifier().getLocalName());
				enviroment.registerToMe(agent.getComponentIdentifier());
			}
		});	
		    
		}
		*/
	}
	
}
