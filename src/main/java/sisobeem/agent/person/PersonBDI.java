/**
 * 
 */
package sisobeem.agent.person;

import jadex.bdiv3.annotation.Belief;
import jadex.bridge.IInternalAccess;
import jadex.micro.annotation.Agent;
import sisobeem.artifacts.Coordenada;

/**
 * Abstrae el comportamiento de una persona
 * @author Erley
 *
 */
@Agent
public abstract class PersonBDI {
    
	@Agent 
	protected IInternalAccess agent;
		
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
	
	
	
}
