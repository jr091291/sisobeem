package sisobeem.agent.enviroment;

import static sisobeem.artifacts.Log.getLog;

import java.util.ArrayList;
import java.util.Map;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.services.enviromentService.IComunicarMensajesService;
import sisobeem.services.enviromentService.IGetPersonHelpService;
import sisobeem.services.personServices.IReceptorMensajesService;


@Agent
@ProvidedServices({
	                @ProvidedService(name = "IGetPersonHelpService",type = IGetPersonHelpService.class),
	             //   @ProvidedService(name = "ITeamService",type = ITeamService.class),
	                @ProvidedService(name = "IComunicarMensajesService",type = IComunicarMensajesService.class)})
@RequiredServices({ @RequiredService(name = "IReceptorMensajesService", type = IReceptorMensajesService.class)})
@Description("Abstrae el comportamiento de un ambiente")
public abstract class EnviromentAgentBDI implements IComunicarMensajesService,IGetPersonHelpService {

	@Agent
	protected IInternalAccess agent;

	/** The bdi api. */
	@AgentFeature
	protected IBDIAgentFeature bdi;

	@Belief
	Boolean start;

	// Argumentos
	Map<String, Object> arguments;

	// listadoAgentes
	ArrayList<IComponentIdentifier> cidsPerson;

	@Belief
	protected double intensidadSismo;
	
	
	
	//Estadisticas
	ArrayList<IComponentIdentifier> contMsgAyuda;
	ArrayList<IComponentIdentifier> contMsgDeCalma;
	ArrayList<IComponentIdentifier> contMsgDeConfianza;
	ArrayList<IComponentIdentifier>contMsgFrsutracion;
	ArrayList<IComponentIdentifier> contMsgHostilidad;
	ArrayList<IComponentIdentifier> contMsgPanico;
	ArrayList<IComponentIdentifier> contMsgPrimerosAux;
	ArrayList<IComponentIdentifier> contMsgResguardo;
	ArrayList<IComponentIdentifier> contMsgMotivacion;
	
	ArrayList<IComponentIdentifier> cidPersonDead;



	public IInternalAccess getAgent() {
		return agent;
	}

	
	/**
	 * Servicios de envio de mensajes a agentes especificos
	 */
	
	@Override
	public void CalmaMsj(IComponentIdentifier emisor, IComponentIdentifier receptor) {

		if (this.cidsPerson.contains(emisor) || this.cidsPerson.contains(receptor)) {

			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {
					getLog().setInfo("Enviando mensaje de calma de: " + emisor.getLocalName() + " para:"
							+ receptor.getLocalName());
					result.CalmaMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		} else {

			getLog().setInfo("Mensaje no enviado, ya que no se encuentra al agente Emisor: " + emisor.getLocalName()
					+ " ó al agente: " + receptor.getLocalName());
		}

	}

	@Override
	public void ConfianzaMsj(IComponentIdentifier emisor, IComponentIdentifier receptor) {

		if (this.cidsPerson.contains(emisor) || this.cidsPerson.contains(receptor)) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {
					getLog().setInfo("Enviando mensaje de Confianza de: " + emisor.getLocalName() + " para:"
							+ receptor.getLocalName());
					result.ConfianzaMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		} else {

			getLog().setInfo("Mensaje no enviado, ya que no se encuentra al agente Emisor: " + emisor.getLocalName()
					+ " ó al agente: " + receptor.getLocalName());
		}

	}

	@Override
	public void FrustracionMsj(IComponentIdentifier emisor, IComponentIdentifier receptor) {

		if (this.cidsPerson.contains(emisor) || this.cidsPerson.contains(receptor)) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {
					getLog().setInfo("Enviando mensaje de Frustracion de: " + emisor.getLocalName() + " para:"
							+ receptor.getLocalName());
					result.FrustracionMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		} else {

			getLog().setInfo("Mensaje no enviado, ya que no se encuentra al agente Emisor: " + emisor.getLocalName()
					+ " ó al agente: " + receptor.getLocalName());
		}

	}

	@Override
	public void HostilMsj(IComponentIdentifier emisor, IComponentIdentifier receptor) {

		if (this.cidsPerson.contains(emisor) || this.cidsPerson.contains(receptor)) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {
					getLog().setInfo("Enviando mensaje Hostil de: " + emisor.getLocalName() + " para:"
							+ receptor.getLocalName());
					result.HostilMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});

		} else {

			getLog().setInfo("Mensaje no enviado, ya que no se encuentra al agente Emisor: " + emisor.getLocalName()
					+ " ó al agente: " + receptor.getLocalName());
		}
	}

	@Override
	public void PanicoMsj(IComponentIdentifier emisor, IComponentIdentifier receptor) {

		if (this.cidsPerson.contains(emisor) || this.cidsPerson.contains(receptor)) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {
					getLog().setInfo("Enviando mensaje Panico de: " + emisor.getLocalName() + " para:"
							+ receptor.getLocalName());
					result.PanicoMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		} else {

			getLog().setInfo("Mensaje no enviado, ya que no se encuentra al agente Emisor: " + emisor.getLocalName()
					+ " ó al agente: " + receptor.getLocalName());
		}

	}

	@Override
	public void PrimeroAuxMsj(IComponentIdentifier emisor, IComponentIdentifier receptor) {
		
		if (this.cidsPerson.contains(emisor) || this.cidsPerson.contains(receptor)) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {
					getLog().setInfo("Enviando mensaje PrimeroAux de: " + emisor.getLocalName() + " para:"
							+ receptor.getLocalName());
					result.PrimeroAuxMsj(emisor);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		} else {

			getLog().setInfo("Mensaje no enviado, ya que no se encuentra al agente Emisor: " + emisor.getLocalName()
					+ " ó al agente: " + receptor.getLocalName());
		}

	}

	@Override
	public void ResguardoMsj(IComponentIdentifier emisor, IComponentIdentifier receptor) {
		
		if (this.cidsPerson.contains(emisor) || this.cidsPerson.contains(receptor)) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {
					getLog().setInfo("Enviando mensaje Resguardo de: " + emisor.getLocalName() + " para:"
							+ receptor.getLocalName());
					result.ResguardoMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		} else {

			getLog().setInfo("Mensaje no enviado, ya que no se encuentra al agente Emisor: " + emisor.getLocalName()
					+ " ó al agente: " + receptor.getLocalName());
		}

	}

	@Override
	public void Motivacion(IComponentIdentifier emisor, IComponentIdentifier receptor) {
		
		if (this.cidsPerson.contains(emisor) || this.cidsPerson.contains(receptor)) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {
					getLog().setInfo("Enviando mensaje Motivacion de: " + emisor.getLocalName() + " para:"
							+ receptor.getLocalName());
					result.MotivacionMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		} else {

			getLog().setInfo("Mensaje no enviado, ya que no se encuentra al agente Emisor: " + emisor.getLocalName()
					+ " ó al agente: " + receptor.getLocalName());
		}

	}
	
	@Override
	public void Team(IComponentIdentifier emisor, IComponentIdentifier receptor) {
		if (this.cidsPerson.contains(emisor) || this.cidsPerson.contains(receptor)) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {
					getLog().setInfo("Enviando mensaje Team de: " + emisor.getLocalName() + " para:"
							+ receptor.getLocalName());
				//	result.Team(emisor);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		} else {

			getLog().setInfo("Mensaje no enviado, ya que no se encuentra al agente Emisor: " + emisor.getLocalName()
					+ " ó al agente: " + receptor.getLocalName());
		}

	}

	
	



}
