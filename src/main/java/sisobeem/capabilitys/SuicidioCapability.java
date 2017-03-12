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
import sisobeem.services.plantServices.IEvacuarPisoService;

@Capability
public class SuicidioCapability {

	@Goal(excludemode = ExcludeMode.Never)
	public class SaltarDelEdificio {

		@GoalParameter
		IInternalAccess agent;

		@GoalParameter
		IComponentIdentifier cidPiso;
		
		public SaltarDelEdificio(IInternalAccess agent, IComponentIdentifier piso) {
			// System.out.println(agent.getComponentIdentifier().getLocalName());
          //  getLog().setDebug(" Entró a la capacidad de resguardarse");
			
			this.agent = agent;
			this.cidPiso = piso;
			Saltar(this.agent,piso);
		}
	

		@Plan
		protected void Saltar(IInternalAccess agent, IComponentIdentifier piso) {
				       			
			//System.out.println(conocimientoZona+" "+cidPiso.getLocalName()+" "+cidEdifice.getLocalName());
			IFuture<IEvacuarPisoService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IEvacuarPisoService.class, piso);

			pisoService.addResultListener(new IResultListener<IEvacuarPisoService>() {

				@Override
				public void resultAvailable(IEvacuarPisoService result) {
					SetSalud(-3);
					result.Suicidar(agent.getComponentIdentifier());
					//getLog().setDebug("Agente Suicidado");
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});
		
		}

	}
	

	@Goal(excludemode = ExcludeMode.Never)
	public class HacerNada {

		@GoalParameter
		IInternalAccess agent;

		@GoalParameter
		IComponentIdentifier cidPiso;
		
		public HacerNada() {
		Saltar();
		}
	

		@Plan
		protected void Saltar() {
				       			
			//System.out.println(conocimientoZona+" "+cidPiso.getLocalName()+" "+cidEdifice.getLocalName());

		}

	}

	@Belief
	public native int getSalud();

	@Belief
	public native void SetSalud(int salud);
}
