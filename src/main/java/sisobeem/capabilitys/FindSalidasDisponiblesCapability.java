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
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import sisobeem.services.edificeServices.IEvacuarService;
import sisobeem.services.edificeServices.IGetSalidasService;

@Capability
public class FindSalidasDisponiblesCapability {
	
	@Goal(excludemode = ExcludeMode.Never)
	public class salidas {

		@GoalParameter
		IInternalAccess agent;

		@GoalParameter
		double conocimientoZona;
		
	
		@GoalParameter
		IComponentIdentifier cidEdifice;

		public salidas(IInternalAccess agent, double conocimientoZona,
				IComponentIdentifier cidEdifice) {
			// System.out.println(agent.getComponentIdentifier().getLocalName());
			//getLog().setDebug(" Entró a la capacidad de Evacuar");
			this.agent = agent;
			this.conocimientoZona = conocimientoZona;
			this.cidEdifice = cidEdifice;
			ConsultarSalidas();
		}


		@Plan
		protected void ConsultarSalidas() {
		
			//System.out.println(conocimientoZona+" "+cidPiso.getLocalName()+" "+cidEdifice.getLocalName());
			IFuture<IGetSalidasService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IGetSalidasService.class, cidEdifice);

			pisoService.addResultListener(new IResultListener<IGetSalidasService>() {

				@Override
				public void resultAvailable(IGetSalidasService result) {
					//getLog().setDebug("Solicitando cambiar de piso al edificio");
				 result.getSalidasDisponibles(conocimientoZona);
					
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
	public native int getSalidasDisponibles();

	/**
	 * Set contextCaminar
	 * @param Boolean
	 */
	@Belief
	public native void SetSalidasDisponibles(int salidas);
	
}
