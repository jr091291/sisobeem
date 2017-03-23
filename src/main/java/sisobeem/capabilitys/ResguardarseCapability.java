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
import sisobeem.services.edificeServices.IEvacuarService;
import sisobeem.services.zoneServices.ISetInformationService;

@Capability
public class ResguardarseCapability {

	/**
	 * Meta  buscar persona que necesiten ayuda
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class Resguardarse {

		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		public Resguardarse(IInternalAccess agent, IComponentIdentifier enviroment) {
			// System.out.println(agent.getComponentIdentifier().getLocalName());
          //  getLog().setDebug(" Entró a la capacidad de resguardarse");
            this.agent = agent;
            this.enviroment = enviroment;
			MovimientoOff(this.agent,this.enviroment);
		}
		
		
		/**
		 * Plan que modifica el contexto caminar
		 */

		@Plan
		protected void MovimientoOff(IInternalAccess agent, IComponentIdentifier enviroment) {
			SetContextResguardarse(true);
			
			IFuture<ISetInformationService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetInformationService.class, enviroment);

			pisoService.addResultListener(new IResultListener<ISetInformationService>() {

				@Override
				public void resultAvailable(ISetInformationService result) {
					//getLog().setDebug("Solicitando cambiar de piso al edificio");
					result.setResguardo(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});
		
		}

	}
	
	
	/**
	 * Get the contextCaminar
	 */
	@Belief
	public native Boolean getContextResguardarse();

	/**
	 * Set contextCaminar
	 * @param Boolean
	 */
	@Belief
	public native void SetContextResguardarse(Boolean valor);
}
