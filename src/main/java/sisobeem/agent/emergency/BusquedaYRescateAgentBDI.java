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
import sisobeem.services.personServices.ISetBeliefPersonService;

@Agent
@Description("BusquedaYRescateAgentBDI: encargado de rescatar personas atrapadas")
@RequiredServices({ 
	

})
@ProvidedServices({ 
	
})
public class BusquedaYRescateAgentBDI extends PersonAgentBDI {
	

	//String tipo = "busqueda";
	//IComponentIdentifier cidZone;
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
		this.cidZone = null;
	       	
	   
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
	public int getSalud() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void Team(IComponentIdentifier parner, double liderazgo) {
		// TODO Auto-generated method stub
		
	}
	

}
