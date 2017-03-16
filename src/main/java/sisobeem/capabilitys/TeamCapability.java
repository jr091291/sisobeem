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
		
		@GoalParameter
		double liderazgo ;

		public MensajeDeTeam(IInternalAccess agent,IComponentIdentifier enviroment, double liderazgo) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.liderazgo = liderazgo;
			sendMensaje(this.agent,this.enviroment, this.liderazgo);
		}
		
		public MensajeDeTeam(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor,double liderazgo) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			this.liderazgo = liderazgo;
			sendMensaje(this.agent,this.enviroment, this.liderazgo);
		}
		
		
		@Plan
		protected void sendMensaje(IInternalAccess agent,IComponentIdentifier enviroment, double liderazgo) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.Team(agent.getComponentIdentifier(),liderazgo);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                   //  getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void sendMensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor, double liderazgo) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.Team(agent.getComponentIdentifier(), receptor);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                   //  getLog().setError(exception.getMessage());
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
	                     //getLog().setError(exception.getMessage());
					}

				});
			}

		}
		


		
	}
	
	

	/**
	 *  Conformacion de grupos
	 * @author Erley
	 *
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class AddPersonNeedHelp {
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;
		
		@GoalParameter
		double liderazgo;
		
		@GoalParameter
		IComponentIdentifier[] listado;

		public AddPersonNeedHelp(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier[] listado, double liderazgo) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.listado = listado;
			this.liderazgo = liderazgo;
			sendMensaje(this.agent,this.enviroment,this.listado,this.liderazgo);
		}
		
	
		@Plan
		protected void sendMensaje(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier[] listado, double liderazgo) {
			
		  for (IComponentIdentifier a : listado) {
			
				IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
						.searchService(IComunicarMensajesService.class, a);

				zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

					@Override
					public void resultAvailable(IComunicarMensajesService result) {
						result.Team(agent.getComponentIdentifier(),liderazgo);
					}

					@Override
					public void exceptionOccurred(Exception exception) {
	                 //    getLog().setError(exception.getMessage());
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
