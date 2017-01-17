package sisobeem.agent.enviroment;

import java.util.ArrayList;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Ubicacion;
import sisobeem.services.edificeServices.IEvacuarService;
import sisobeem.services.edificeServices.IGetSalidasService;
import sisobeem.services.edificeServices.ISetBeliefEdificeService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;
import sisobeem.services.plantServices.IEvacuarPisoService;
import sisobeem.services.plantServices.ISetBelifePlantService;
import sisobeem.services.zoneServices.IAdicionarPersonasService;
import sisobeem.utilities.Traslator;

import static sisobeem.artifacts.Log.getLog;

@Agent
@Description("Agente Edificio: encargado de simular el ambiente edificio")
@RequiredServices({ @RequiredService(name = "ISetBelifePlantService", type = ISetBelifePlantService.class),
		@RequiredService(name = "IEvacuarPisoService", type = IEvacuarPisoService.class),
		@RequiredService(name = "IAdicionarPersonasService", type = IAdicionarPersonasService.class)

})
@ProvidedServices({ @ProvidedService(name = "IEvacuarService", type = IEvacuarService.class),
		@ProvidedService(name = "IGetSalidasService", type = IGetSalidasService.class),
		@ProvidedService(name = "ISetStartService", type = ISetStartService.class),
		@ProvidedService(name = "ISetBelifeEdificeService", type = ISetBeliefEdificeService.class) })
public class EdificeAgentBDI extends EnviromentAgentBDI
		implements ISetBeliefEdificeService, ISetStartService, IGetSalidasService, IEvacuarService {

	ArrayList<IComponentIdentifier> cidsPlants;

	@Belief
	IComponentIdentifier cidZone;

	@Belief
	Coordenada myPosition;

	@Belief
	int salidas;

	@Belief
	float resistencia;

	@SuppressWarnings("unchecked")
	@AgentCreated
	public void init() {
		this.cidsPlants = new ArrayList<IComponentIdentifier>();
		// Accedemos a los argumentos del agente
		this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();

		// Obtenemos los cid de las personas en la Zona
		cidsPlants = (ArrayList<IComponentIdentifier>) arguments.get("cidsPlants");

		this.salidas = (int) arguments.get("salidas");
		this.resistencia = (float) arguments.get("resistencia");
		this.myPosition = Traslator.getTraslator().getCoordenada((Ubicacion) arguments.get("ubicacion"));

	}

	@AgentBody
	public void body() {

	}

	/**
	 * Metodo para enviar las coordenadas inciales a los agentes pisos
	 */
	public void sendEnviromentToPlants() {

		// System.out.println("Edifice: Enviando Creecias A los agentes");
		for (IComponentIdentifier piso : this.cidsPlants) {

			IFuture<ISetBelifePlantService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBelifePlantService.class, piso);

			// System.out.println("Tu Ubicacion es: "+c.getX()+" - "+c.getY()+"
			// Agente: "+person.getName());
			pisoService.addResultListener(new IResultListener<ISetBelifePlantService>() {

				@Override
				public void resultAvailable(ISetBelifePlantService result) {
					result.setEdifice(getAgent().getComponentIdentifier());
					result.setZone(cidZone);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});

		}
	}

	/**
	 * Metodo para enviar la intensidad del sismo a los agentes
	 */
	@Plan(trigger = @Trigger(factchangeds = "intensidadSismo"))
	public void sendIntensidadSismoPlants() {

		// getLog().setDebug("Edifice: Enviando intensidad sismo A los
		// agentes");
		for (IComponentIdentifier piso : this.cidsPlants) {

			IFuture<ISetBelifePlantService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBelifePlantService.class, piso);

			pisoService.addResultListener(new IResultListener<ISetBelifePlantService>() {

				@Override
				public void resultAvailable(ISetBelifePlantService result) {
					result.setSismo(intensidadSismo);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});

		}

		if (intensidadSismo > resistencia) {
			Derrumbar();
		}

	}

	private void Derrumbar() {

		IFuture<ISetStartService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(ISetStartService.class, cidZone);

		pisoService.addResultListener(new IResultListener<ISetStartService>() {

			@Override
			public void resultAvailable(ISetStartService result) {
				result.setStart(true);
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setError(exception.getMessage());
			}

		});

	}

	@Override
	public void setZone(IComponentIdentifier zone) {
		this.cidZone = zone;
		// Cuando se recibe el cidZone se envía a los agentes pisos
		sendEnviromentToPlants();
	}

	@Override
	public void setSismo(double intensidad) {
		this.intensidadSismo = intensidad;

		// System.out.println("temblando con intensidad de "+intensidad);
	}

	/**
	 * Servicios de comunicacion de mensajes en rango de escucha
	 */
	@Override
	public void AyudaMsj(IComponentIdentifier emisor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void PrimeroAuxMsj(IComponentIdentifier emisor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void CalmaMsj(IComponentIdentifier emisor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ConfianzaMsj(IComponentIdentifier emisor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void FrustracionMsj(IComponentIdentifier emisor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void HostilMsj(IComponentIdentifier emisor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void PanicoMsj(IComponentIdentifier emisor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ResguardoMsj(IComponentIdentifier emisor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void Motivacion(IComponentIdentifier emisor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setStart(Boolean s) {
		// getLog().setDebug("Edifice: Enviando start a los pisos");
		for (IComponentIdentifier piso : this.cidsPlants) {

			IFuture<ISetStartService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetStartService.class, piso);

			pisoService.addResultListener(new IResultListener<ISetStartService>() {

				@Override
				public void resultAvailable(ISetStartService result) {
					result.setStart(true);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});

		}
	}

	@Override
	public int getSalidasDisponibles(double conocimientoDeLaZona) {
		if (conocimientoDeLaZona > 50) {
			return salidas;
		}

		return 0;
	}

	@Override
	public void cambiarDePiso(double ConocimientoDeLaZona, IComponentIdentifier pisoActual,
			IComponentIdentifier agent) {

		int numPiso = getNumeroPiso(pisoActual);
		IComponentIdentifier pisoProximo;
		if (ConocimientoDeLaZona > 50) {

			pisoProximo = this.cidsPlants.get(numPiso - 2);

			if (numPiso > 1) {

				RemoveAgentPiso(pisoActual, agent);
				AdicionarAgentPiso(pisoProximo, agent);

			} else {

				EnviarAgenteAlAmbienteZone(pisoProximo, agent);

			}
		}

	}

	/**
	 * Método que invoca un servicio para remover un agente de un piso
	 * 
	 * @param piso
	 * @param person
	 */
	public void RemoveAgentPiso(IComponentIdentifier piso, IComponentIdentifier person) {

		IFuture<IEvacuarPisoService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IEvacuarPisoService.class, piso);

		pisoService.addResultListener(new IResultListener<IEvacuarPisoService>() {

			@Override
			public void resultAvailable(IEvacuarPisoService result) {
				result.Evacuar(person);
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setError(exception.getMessage());
			}

		});
	}

	/**
	 * Metodo que invoca un servicio para adicionar un agente a un piso
	 * 
	 * @param piso
	 * @param person
	 */
	public void AdicionarAgentPiso(IComponentIdentifier piso, IComponentIdentifier person) {
		IFuture<IEvacuarPisoService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IEvacuarPisoService.class, piso);

		pisoService.addResultListener(new IResultListener<IEvacuarPisoService>() {

			@Override
			public void resultAvailable(IEvacuarPisoService result) {
				result.Adicionar(person);
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setError(exception.getMessage());
			}

		});
	}

	/**
	 * Metodo para evacuar una persona que se dirige al ambiente Zone
	 * 
	 * @param piso
	 * @param person
	 */
	public void EnviarAgenteAlAmbienteZone(IComponentIdentifier piso, IComponentIdentifier person) {

		// Primero Adcionamos la persona al listado de Zone
		IFuture<IAdicionarPersonasService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IAdicionarPersonasService.class, piso);

		pisoService.addResultListener(new IResultListener<IAdicionarPersonasService>() {

			@Override
			public void resultAvailable(IAdicionarPersonasService result) {
				result.AdicionarPersonService(person);
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setError(exception.getMessage());
			}

		});

		// Lo eliminamos del piso
		RemoveAgentPiso(piso, person);

		// Cambiamos las creencias del agente: cidPiso = null, cidEdifice = null
		IFuture<ISetBeliefPersonService> pisoS = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(ISetBeliefPersonService.class, person);

		pisoS.addResultListener(new IResultListener<ISetBeliefPersonService>() {

			@Override
			public void resultAvailable(ISetBeliefPersonService result) {
				result.setEdifice(null);
				result.setPlant(null);
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setError(exception.getMessage());
			}

		});

	}

	/**
	 * Método para consultar el numero del piso en el edificio
	 * 
	 * @param piso
	 * @return
	 */
	public int getNumeroPiso(IComponentIdentifier piso) {

		int contador = 0;

		for (IComponentIdentifier cidPiso : cidsPerson) {
			contador++;

			if (cidPiso.equals(piso)) {
				return contador;
			}

		}
		return 0;

	}

}
