package sisobeem.capabilitys;

import static sisobeem.artifacts.Log.getLog;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Capability;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.model.MProcessableElement.ExcludeMode;
import jadex.bridge.IInternalAccess;

@Capability
public class ResguardarseCapability {

	/**
	 * Meta  buscar persona que necesiten ayuda
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class Resguardarse {

		
		public Resguardarse() {
			// System.out.println(agent.getComponentIdentifier().getLocalName());
            getLog().setDebug(" Entró a la capacidad de resguardarse");
            MovimientoOff();
		}
		
		
		/**
		 * Plan que modifica el contexto caminar
		 */

		@Plan
		protected void MovimientoOff() {
			SetContextCaminar(false);
		
		}

	}
	
	
	/**
	 * Get the contextCaminar
	 */
	@Belief
	public native Boolean getContextCaminar();

	/**
	 * Set contextCaminar
	 * @param Boolean
	 */
	@Belief
	public native void SetContextCaminar(Boolean valor);
}
