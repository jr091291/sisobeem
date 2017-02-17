package sisobeem.capabilitys;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Capability;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.model.MProcessableElement.ExcludeMode;

@Capability
public class ResguardarseCapability {

	/**
	 * Meta  buscar persona que necesiten ayuda
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class Resguardarse {

		
		public Resguardarse() {
			// System.out.println(agent.getComponentIdentifier().getLocalName());
          //  getLog().setDebug(" Entró a la capacidad de resguardarse");
            MovimientoOff();
		}
		
		
		/**
		 * Plan que modifica el contexto caminar
		 */

		@Plan
		protected void MovimientoOff() {
			SetContextResguardarse(true);
		
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
