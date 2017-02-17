package sisobeem.agent.emergency;

import jadex.bridge.IComponentIdentifier;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import sisobeem.agent.person.PersonAgentBDI;

@Agent
@Description("SaludAgentBDI: encargado de mejorar la salud de los heridos")
@RequiredServices({ 
	

})
@ProvidedServices({ 
	
})
public class SaludAgentBDI extends PersonAgentBDI{


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
	public void Team(IComponentIdentifier parner) {
		
	}


	@Override
	public void TomaDeDecisiones() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public int getSalud() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
