package sisobeem.capabilitys;

import static sisobeem.artifacts.Log.getLog;

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
import sisobeem.services.enviromentService.IComunicarMensajesService;

@Capability
public class ComunicarseCapability {
	
	
	/**
	 * Mensajes de ayuda
	 * @author Erley
	 *
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeAyuda {
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;

		public MensajeAyuda(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			sendMensaje(this.agent,this.enviroment);
		}
		
		
		@Plan
		protected void sendMensaje(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.AyudaMsj(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	
	
	/**
	 * Mensajes de calma
	 * @author Erley
	 *
	 */
	
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeDeCalma {
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;

		public MensajeDeCalma(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			MensajeGrupal(this.agent,this.enviroment);
		}
		
		public MensajeDeCalma(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor ) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			MensajeEspecifico(this.agent,this.enviroment,this.receptor);
		}
		
		
		@Plan
		protected void MensajeGrupal(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.CalmaMsj(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void MensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.CalmaMsj(agent.getComponentIdentifier(), receptor);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	

	/**
	 * Mensajes de Confianza
	 * @author Erley
	 *
	 */
	
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeDeConfianza {
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;

		public MensajeDeConfianza(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			MensajeGrupal(this.agent,this.enviroment);
		}
		
		public MensajeDeConfianza(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor ) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			MensajeEspecifico(this.agent,this.enviroment,this.receptor);
		}
		
		
		@Plan
		protected void MensajeGrupal(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.ConfianzaMsj(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void MensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.ConfianzaMsj(agent.getComponentIdentifier(), receptor);				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	
	
	/**
	 * Mensajes de Frsutracion
	 * @author Erley
	 *
	 */
	
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeDeFrustracion{
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;

		public MensajeDeFrustracion(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			MensajeGrupal(this.agent,this.enviroment);
		}
		
		public MensajeDeFrustracion(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor ) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			MensajeEspecifico(this.agent,this.enviroment,this.receptor);
		}
		
		
		@Plan
		protected void MensajeGrupal(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.FrustracionMsj(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void MensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.FrustracionMsj(agent.getComponentIdentifier(), receptor);			}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	
	

	/**
	 * Mensajes de Hostilidad
	 * @author Erley
	 *
	 */
	
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeDeHostilidad{
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;

		public MensajeDeHostilidad(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			MensajeGrupal(this.agent,this.enviroment);
		}
		
		public MensajeDeHostilidad(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor ) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			MensajeEspecifico(this.agent,this.enviroment,this.receptor);
		}
		
		
		@Plan
		protected void MensajeGrupal(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.HostilMsj(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void MensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.HostilMsj(agent.getComponentIdentifier(), receptor);			}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	

	/**
	 * Mensajes de Panico
	 * @author Erley
	 *
	 */
	
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeDePanico{
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;

		public MensajeDePanico(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			MensajeGrupal(this.agent,this.enviroment);
		}
		
		public MensajeDePanico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor ) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			MensajeEspecifico(this.agent,this.enviroment,this.receptor);
		}
		
		
		@Plan
		protected void MensajeGrupal(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.PanicoMsj(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void MensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.PanicoMsj(agent.getComponentIdentifier(), receptor);		}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	
	
	/**
	 * Mensajes de Primeros Aux
	 * @author Erley
	 *
	 */
	
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeDePrimerosAux{
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;

		public MensajeDePrimerosAux(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			MensajeGrupal(this.agent,this.enviroment);
		}
		
		public MensajeDePrimerosAux(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor ) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			MensajeEspecifico(this.agent,this.enviroment,this.receptor);
		}
		
		
		@Plan
		protected void MensajeGrupal(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.PrimeroAuxMsj(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void MensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.PrimeroAuxMsj(agent.getComponentIdentifier(), receptor);		}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	
	
	/**
	 * Mensajes de Resguardo
	 * @author Erley
	 *
	 */
	
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeDeResguardo{
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;

		public MensajeDeResguardo(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			MensajeGrupal(this.agent,this.enviroment);
		}
		
		public MensajeDeResguardo(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor ) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			MensajeEspecifico(this.agent,this.enviroment,this.receptor);
		}
		
		
		@Plan
		protected void MensajeGrupal(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.ResguardoMsj(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void MensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.ResguardoMsj(agent.getComponentIdentifier(), receptor);	}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	
	
	/**
	 * Mensajes de Motivacion
	 * @author Erley
	 *
	 */
	
	@Goal(excludemode = ExcludeMode.Never)
	public class MensajeDeMotivacion{
		
		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		IComponentIdentifier enviroment;
		
		@GoalParameter
		IComponentIdentifier receptor;

		public MensajeDeMotivacion(IInternalAccess agent,IComponentIdentifier enviroment) {
			this.agent = agent;
			this.enviroment = enviroment;
			MensajeGrupal(this.agent,this.enviroment);
		}
		
		public MensajeDeMotivacion(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor ) {
			this.agent = agent;
			this.enviroment = enviroment;
			this.receptor = receptor;
			MensajeEspecifico(this.agent,this.enviroment,this.receptor);
		}
		
		
		@Plan
		protected void MensajeGrupal(IInternalAccess agent,IComponentIdentifier enviroment) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.Motivacion(agent.getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}
		
		@Plan
		protected void MensajeEspecifico(IInternalAccess agent,IComponentIdentifier enviroment, IComponentIdentifier receptor) {
			
			IFuture<IComunicarMensajesService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IComunicarMensajesService.class, enviroment);

			zoneService.addResultListener(new IResultListener<IComunicarMensajesService>() {

				@Override
				public void resultAvailable(IComunicarMensajesService result) {
					result.Motivacion(agent.getComponentIdentifier(), receptor);;	}

				@Override
				public void exceptionOccurred(Exception exception) {
                     getLog().setError(exception.getMessage());
				}

			});

		}

		
	}
	
	
	
	
	
	
}
