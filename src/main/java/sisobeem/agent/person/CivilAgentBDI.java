package sisobeem.agent.person;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedServices;
import sisobeem.capabilitys.MoveCapability.Aleatorio;
import sisobeem.utilities.Random;

@Agent
@Description("Agente vicil, encargados de vivir la experiencia del terremoto.")
@ProvidedServices({
  })  
public class CivilAgentBDI extends PersonAgentBDI  {
	

	/**
	 * Configuraciones Iniciales
	 */
	@AgentCreated
	public void init()
	{   
		
		
	}
	
	
	/**
	 * Cuerpo Principal del agente
	 */
	@AgentBody
	public void body()
	{
	       	
	   
	}
	
	
	
	/**
	 *  Caminar, Metodo para activar la capacidad de caminar
	 *  Metodo para caminar, se activa por medio de un contexto.
	 */
	@Plan(trigger=@Trigger(factchangeds="contextCaminar"))
	public void caminar()
	{   
		this.velocidad = Random.getIntRandom(1, 5);
  		getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(),this.velocidad, this.getPosition(),cidZone));
	  		
	}
	
	/**
	 *  Cuando termino de dar caminar
	 */
	@Plan(trigger=@Trigger(goalfinisheds=Aleatorio.class))
	public void  caminarEnd()
	{   
         if(contextCaminar){
        	 caminar();
         }
	}
	
	/**
	 *  Cuando termino de dar caminar
	 */
	@Plan(trigger=@Trigger(goalfinisheds=sisobeem.capabilitys.EvacuarCapability.Evacuar.class))
	public void  Evacuar()
	{   
		if(cidEdifice!=null&&cidPlant!=null){
			getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(EvacuarEdificio.new Evacuar(this.getAgent(),this.conocimientoZona,this.cidPlant,this.cidEdifice));
		}
	}

	/*
	@Plan(trigger=@Trigger(goalfinisheds={sisobeem.capabilitys.EvacuarCapability.Evacuar.class,}))
	public void  Evacuar2()
	{   
		System.out.println("Terminó");
	}
	

	*/
	/**
	 * Acciones al recibir distintos tipos de mensajes
	 */

	@Override
	public void AyudaMsj(IComponentIdentifier cidPersonHelp) {
		
	}


	@Override
	public void CalmaMsj() {
		
		if(this.vivo){
			this.miedo = this.miedo - 1;
			this.enojo = this.enojo -1;
		}
		
		
	}


	@Override
	public void ConfianzaMsj() {
		if(this.vivo){
			this.confianza = this.confianza +1;
			this.gregarismo = this.gregarismo +1;
		}
		
	}


	@Override
	public void FrustracionMsj() {
		
		if(this.vivo){
			this.confianza = this.confianza -1;
			this.enojo = this.enojo +1;
			this.tristeza = this.tristeza +1;
		}
		
	}


	@Override
	public void HostilMsj() {
		if(this.vivo){
			this.enojo = this.enojo+2;
		}
	}


	@Override
	public void PanicoMsj() {
		if(this.vivo){
			this.miedo = this.miedo+1;
			this.confianza = this.confianza-1;
		}
		
	}


	@Override
	public void PrimeroAuxMsj(IComponentIdentifier  cidPersonAux) {
		
		
	}


	@Override
	public void ResguardoMsj() {
		
		if(this.vivo){
			this.contextCaminar = false;
		}
	}


	@Override
	public void MotivacionMsj() {
		if(this.vivo){
			this.miedo = this.miedo -1;
			this.tristeza = this.tristeza -1;
			this.confianza = this.confianza +1;	
		}
	}


	@Override
	public String getEstado() {
		return this.estado;
	}




	



	
	
}
