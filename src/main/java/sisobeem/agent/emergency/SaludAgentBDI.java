package sisobeem.agent.emergency;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import sisobeem.agent.person.PersonAgentBDI;
import sisobeem.artifacts.Coordenada;
import sisobeem.capabilitys.MoveCapability.Aleatorio;

@Agent
@Description("SaludAgentBDI: encargado de mejorar la salud de los heridos")
@RequiredServices({ 
	

})
@ProvidedServices({ 
	
})
public class SaludAgentBDI extends PersonAgentBDI{

	
	String tipo = "salud";
	/**
	 * Configuraciones Iniciales
	 */
	@AgentCreated
	public void init()
	{   
		this.myPosition  = new Coordenada(5,5);
		
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
		  if(this.cidZone!=null){
		    	System.out.println("COORDINADOR TRATANDO DE CAMINAR: "+this.getAgent().getComponentIdentifier().getLocalName());
				getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), 5, this.getPosition(), this.cidZone,this.tipo));
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
            sisobeem.capabilitys.TeamCapability.EnviarRuta.class}))
    public void endgoals() {
        this.TomaDeDecisiones();
    }

	
}
