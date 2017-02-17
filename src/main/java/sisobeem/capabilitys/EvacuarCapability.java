package sisobeem.capabilitys;

import static sisobeem.artifacts.Log.getLog;

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
import sisobeem.services.edificeServices.IEvacuarService;

/**
 * Capacidad de cambiar de un ambiente a otro
 * 
 * @author Erley
 *
 */
@Capability
public class EvacuarCapability {

	/**
	 * Meta buscar persona que necesiten ayuda
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class Evacuar {

		@GoalParameter
		IInternalAccess agent;

		@GoalParameter
		double conocimientoZona;
		
		int tiempo = 4000; // Un segundo para evacuar el edificio

		@GoalParameter
		IComponentIdentifier cidPiso;

		@GoalParameter
		IComponentIdentifier cidEdifice;

		public Evacuar(IInternalAccess agent, double conocimientoZona, IComponentIdentifier cidPiso,
				IComponentIdentifier cidEdifice) {
			// System.out.println(agent.getComponentIdentifier().getLocalName());
			//getLog().setDebug(" Entró a la capacidad de Evacuar");
			this.agent = agent;
			this.conocimientoZona = conocimientoZona;
			this.cidPiso = cidPiso;
			this.cidEdifice = cidEdifice;
			CambiarDePiso();
		}
		
		public Evacuar(IInternalAccess agent, double conocimientoZona, IComponentIdentifier cidPiso,
				IComponentIdentifier cidEdifice, IComponentIdentifier lider) {
			// System.out.println(agent.getComponentIdentifier().getLocalName());
			//getLog().setDebug(" Entró a la capacidad de Evacuar");
			this.agent = agent;
			this.conocimientoZona = 2;
			this.cidPiso = cidPiso;
			this.cidEdifice = cidEdifice;
			CambiarDePiso();
		}

		/**
		 * Plan que consulta los cids de las personas que necesitan ayuda
		 */

		@Plan
		protected void CambiarDePiso() {
			
			agent.getComponentFeature(IExecutionFeature.class).waitForDelay(tiempo).get();
			
			//System.out.println(conocimientoZona+" "+cidPiso.getLocalName()+" "+cidEdifice.getLocalName());
			IFuture<IEvacuarService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IEvacuarService.class, cidEdifice);

			pisoService.addResultListener(new IResultListener<IEvacuarService>() {

				@Override
				public void resultAvailable(IEvacuarService result) {
					//getLog().setDebug("Solicitando cambiar de piso al edificio");
					result.cambiarDePiso(conocimientoZona, cidPiso, agent.getComponentIdentifier());
					
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});
		}
	}

}
