package sisobeem.capabilitys;

import static sisobeem.artifacts.Log.getLog;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Capability;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.model.MProcessableElement.ExcludeMode;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import sisobeem.artifacts.Coordenada;
import sisobeem.services.zoneServices.IGetDestinyService;


@Capability
public class IdentificarZonasSegurasCapability {

	
	/**
	 * Meta  buscar persona que necesiten ayuda
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class FindZonaSegura {

		@GoalParameter
		IInternalAccess agent;
	    
		@GoalParameter
		IComponentIdentifier enviroment;
		

		public FindZonaSegura(IInternalAccess agent,IComponentIdentifier enviroment) {
			// System.out.println(agent.getComponentIdentifier().getLocalName());
           // getLog().setDebug(" Entró a la capacidad");
		     
			this.agent = agent;
			this.enviroment = enviroment;
		
			BuscarDestino(agent, enviroment);

		}
		
		
		/**
		 * Plan que consultar un destino seguro
		 */

		@Plan
		protected void BuscarDestino(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IGetDestinyService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IGetDestinyService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IGetDestinyService>() {

				@Override
				public void resultAvailable(IGetDestinyService result) {
					setMyDestiny(result.getDestiny());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

	}
	
	
	/**
	 * Get the myDestiny
	 */
	@Belief
	public native Coordenada getMyDestiny();

	/**
	 * Set myDestiny
	 * @param myDestiny
	 */
	@Belief
	public native void setMyDestiny(Coordenada myDestiny);
}
