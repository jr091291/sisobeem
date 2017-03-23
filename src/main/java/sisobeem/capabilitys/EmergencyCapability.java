package sisobeem.capabilitys;

import java.util.ArrayList;

import jadex.bdiv3.annotation.Capability;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.model.MProcessableElement.ExcludeMode;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import sisobeem.artifacts.Coordenada;
import sisobeem.services.enviromentService.IComunicarMensajesService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.zoneServices.IGetInformationZoneService;
import sisobeem.services.zoneServices.IMapaService;

@Capability
public class EmergencyCapability {
          
	/**
	 * Mensajes de ayuda
	 * @author Erley
	 *
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class CurarHeridos {
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier herido;
		
	

		public CurarHeridos(IInternalAccess agent,IComponentIdentifier ag) {
			this.agent = agent;
			this.herido = ag;
			sendMensaje(this.agent,this.herido);
		}
		
		
		@Plan
		protected void sendMensaje(IInternalAccess agent,IComponentIdentifier enviroment) {
			agent.getComponentFeature(IExecutionFeature.class).waitForDelay(5000).get();
			IFuture<ISetBeliefPersonService> persona =agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, enviroment);

			persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {

					result.curar();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					//getLog().setFatal(exception.getMessage());
				}

			});

		}

		
	}
	
	
}
