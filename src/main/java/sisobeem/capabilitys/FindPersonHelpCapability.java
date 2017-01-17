package sisobeem.capabilitys;

import static sisobeem.artifacts.Log.getLog;

import java.util.ArrayList;

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
import sisobeem.services.enviromentService.IGetPersonHelpService;

@Capability
public class FindPersonHelpCapability {
	
	/**
	 * Meta  buscar persona que necesiten ayuda
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class FindPerson {

		@GoalParameter
		IInternalAccess agent;
	    
		@GoalParameter
		IComponentIdentifier enviroment;
		

		public FindPerson(IInternalAccess agent,IComponentIdentifier enviroment) {
			// System.out.println(agent.getComponentIdentifier().getLocalName());
            getLog().setDebug(" Entró a la capacidad");
		     
			this.agent = agent;
			this.enviroment = enviroment;
		
			findPerson(agent, enviroment);

		}
		
		
		/**
		 * Plan que consulta los cids de las personas que necesitan ayuda
		 */

		@Plan
		protected void findPerson(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IGetPersonHelpService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IGetPersonHelpService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IGetPersonHelpService>() {

				@Override
				public void resultAvailable(IGetPersonHelpService result) {
					setCidsPeopleHelp(result.getPeopleHelp());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

	}
	
	
	/**
	 * Get the cidPeopleHelp
	 */
	@Belief
	public native ArrayList<IComponentIdentifier> getCidsPeopleHelp();

	/**
	 * Set cidPeopleHelp
	 * @param peopleNeedHelp
	 */
	@Belief
	public native void setCidsPeopleHelp(ArrayList<IComponentIdentifier> peopleNeedHelp);

}
