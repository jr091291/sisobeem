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
import sisobeem.artifacts.Coordenada;
import sisobeem.services.enviromentService.IComunicarMensajesService;
import sisobeem.services.personServices.ISetBeliefPersonService;

@Capability
public class TeamCapability {
	
	
	
	/**
	 *  Conformacion de grupos
	 * @author Erley
	 *
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeDeTeam {
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;

		public MensajeDeTeam(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			sendMensaje(this.agent,this.enviroment);
		}
		
		public MensajeDeTeam(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			sendMensaje(this.agent,this.enviroment);
		}
		
		
		@Plan
		protected void sendMensaje(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.Team(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void sendMensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.Team(agent.getComponentIdentifier(), receptor);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	
	/**
	 * Meta que envia la ruta a los miembros de mi team
	 * @author Erley
	 *
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class EnviarRuta {
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;
		
		@GoalParameter
		ArrayList<Coordenada> ruta;

		public EnviarRuta(IInternalAccess agent,IComponentIdentifier enviroment, ArrayList<Coordenada> ruta) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.ruta = ruta;
			sendMensaje(this.agent,this.enviroment,this.ruta);
		}
		
		
		
		@Plan
		protected void sendMensaje(IInternalAccess agent,IComponentIdentifier enviroment,  ArrayList<Coordenada> ruta) {
			
			for (IComponentIdentifier person : getTeam()) {
				
				IFuture<ISetBeliefPersonService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
						.searchService(ISetBeliefPersonService.class, person);

				zoneService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

					@Override
					public void resultAvailable(ISetBeliefPersonService result) {
						result.setRute(ruta);
					}

					@Override
					public void exceptionOccurred(Exception exception) {
	                     getLog().setError(exception.getMessage());
					}

				});
			}

		}
		


		
	}
	
	
	
	
	

	/**
	 * Get the contextCaminar
	 */
	@Belief
	public native ArrayList<IComponentIdentifier> getTeam();

	/**
	 * Set contextCaminar
	 * @param Boolean
	 */
	@Belief
	public native void SetTeam(ArrayList<IComponentIdentifier> team);
	

}
