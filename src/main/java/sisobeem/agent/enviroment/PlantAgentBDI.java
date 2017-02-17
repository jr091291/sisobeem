package sisobeem.agent.enviroment;

import static sisobeem.artifacts.Log.getLog;

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
import sisobeem.services.personServices.IGetInformationService;
import sisobeem.services.personServices.IReceptorMensajesService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;
import sisobeem.services.personServices.IsetDerrumbeService;
import sisobeem.services.plantServices.IDerrumbePlantService;
import sisobeem.services.plantServices.IEvacuarPisoService;
import sisobeem.services.plantServices.ISetBelifePlantService;
import sisobeem.services.zoneServices.IGetInformationZoneService;

@Agent
@Description("Abstrae el comportamiento de una Piso")
@RequiredServices({ @RequiredService(name = "ISetEnviromentService", type = ISetBeliefPersonService.class),
	                @RequiredService(name = "IsetDerrumbeService", type = IsetDerrumbeService.class)
	})
@ProvidedServices({ @ProvidedService(name = "IEvacuarPisoService",type = IEvacuarPisoService.class),
	                @ProvidedService(name = "ISetStartService",type = ISetStartService.class),
	                @ProvidedService(name = "IDerrumbePlantService",type = IDerrumbePlantService.class),
	                @ProvidedService(name = "ISetBelifePlantService", type = ISetBelifePlantService.class) })
public class PlantAgentBDI extends EnviromentAgentBDI implements ISetBelifePlantService,ISetStartService,IEvacuarPisoService,IDerrumbePlantService {

	@Belief
	IComponentIdentifier cidEdifice;
	@Belief
	IComponentIdentifier cidZone;

	@SuppressWarnings("unchecked")
	@AgentCreated
	public void init() {
		// Accedemos a los argumentos del agente
		this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();

		// Obtenemos los cid de las personas en la Zona
		cidsPerson = (ArrayList<IComponentIdentifier>) arguments.get("cidsPerson");

		sendBeliefToPerson();

	}

	@AgentBody
	public void body() {

	}

	/**
	 * Metodo para enviar los enviroment a la persona
	 */
	public void sendBeliefToPerson() {

		for (IComponentIdentifier person : this.cidsPerson) {

			IFuture<ISetBeliefPersonService> personService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			// System.out.println("Tu Ubicacion es: "+c.getX()+" - "+c.getY()+"
			// Agente: "+person.getName());
			personService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setPlant(getAgent().getComponentIdentifier());
					result.setEdifice(getCidEdifice());
					result.setZone(getCidZone());

				}

				@Override
				public void exceptionOccurred(Exception exception) {

				}

			});

		}
	}

	@Override
	public void setEdifice(IComponentIdentifier cidEdificio) {
		this.cidEdifice = cidEdificio;

	}

	@Override
	public void setZone(IComponentIdentifier cidZone) {
		this.cidZone = cidZone;
		sendBeliefToPerson();
	}

	public IComponentIdentifier getCidEdifice() {
		return cidEdifice;
	}

	public void setCidEdifice(IComponentIdentifier cidEdifice) {
		this.cidEdifice = cidEdifice;
	}

	public IComponentIdentifier getCidZone() {
		return cidZone;
	}

	public void setCidZone(IComponentIdentifier cidZone) {
		this.cidZone = cidZone;
	}

	@Override
	public void setSismo(double intensidad) {

		this.intensidadSismo = intensidad;

	}

	@Plan(trigger = @Trigger(factchangeds = "intensidadSismo"))
	public void sendIntensidadSismoPerson() {

		for (IComponentIdentifier person : this.cidsPerson) {

			IFuture<ISetBeliefPersonService> personService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			// System.out.println("Tu Ubicacion es: "+c.getX()+" - "+c.getY()+"
			// Agente: "+person.getName());
			personService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setSismo(intensidadSismo);
				}

				@Override
				public void exceptionOccurred(Exception exception) {

				}

			});

		}
	}

	/**
	 * Servicios de comunicacion de mensajes en rango de escucha
	 */
	@Override
	public void AyudaMsj(IComponentIdentifier emisor) {

		getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Ayuda");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.AyudaMsj(emisor);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}

	}

	@Override
	public void PrimeroAuxMsj(IComponentIdentifier emisor) {

		getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de PrimeroAux");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.PrimeroAuxMsj(emisor);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}

	}

	@Override
	public void CalmaMsj(IComponentIdentifier emisor) {
		
		getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Calma");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.CalmaMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}

	}

	@Override
	public void ConfianzaMsj(IComponentIdentifier emisor) {
		
		getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Confianza");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.ConfianzaMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}

	}

	@Override
	public void FrustracionMsj(IComponentIdentifier emisor) {
		
		getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Frustracion");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.FrustracionMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}

	}

	@Override
	public void HostilMsj(IComponentIdentifier emisor) {
		
		getLog().setInfo(emisor.getLocalName() + ": Envia mensaje Hostil");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.HostilMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}

	}

	@Override
	public void PanicoMsj(IComponentIdentifier emisor) {
		
		getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Panico");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.PanicoMsj();
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}

	}

	@Override
	public void ResguardoMsj(IComponentIdentifier emisor) {
		
		getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Resguardo");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.ResguardoMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}

	}

	@Override
	public void Motivacion(IComponentIdentifier emisor) {
		
		getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Motivacion");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.MotivacionMsj();

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}

	}
	
	@Override
	public void Team(IComponentIdentifier emisor) {
		getLog().setInfo(emisor.getLocalName() + ": Armando team");
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.Team(emisor);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}
	}


	@Override
	public void setStart(Boolean s) {

		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<ISetStartService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetStartService.class, receptor);

			persona.addResultListener(new IResultListener<ISetStartService>() {

				@Override
				public void resultAvailable(ISetStartService result) {

					result.setStart(true);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}
		
	}

	@Override
	public void Evacuar(IComponentIdentifier agent) {
		
		if(this.cidsPerson.contains(agent)){
		//	getLog().setDebug("Elimando agente"+agent.getLocalName()+" de mi piso");
			this.cidsPerson.remove(agent);
		}
				
	}

	@Override
	public void Adicionar(IComponentIdentifier agent) {
		if(!this.cidsPerson.contains(agent)){
			
			this.cidsPerson.add(agent);
			
			/**
			 * Cambiamos la creencia del nuevo piso en el agente
			 */
			IFuture<ISetBeliefPersonService> pisoService = getAgent().getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class,agent);

			pisoService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setPlant(getAgent().getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					  getLog().setError(exception.getMessage());
				}

			});
		}
		
	}

	@Override
	public void Derrumbar(int daño) {
		
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IsetDerrumbeService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IsetDerrumbeService.class, receptor);

			persona.addResultListener(new IResultListener<IsetDerrumbeService>() {

				@Override
				public void resultAvailable(IsetDerrumbeService result) {

					result.recibirDerumbe(daño);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}
		
	}
	
	@Override
	public ArrayList<IComponentIdentifier> getPeopleHelp(IComponentIdentifier agent) {
		// TODO Auto-generated method stub
		
		System.out.println("Generando Listado");
		ArrayList<IComponentIdentifier> listado = new ArrayList<IComponentIdentifier>();
		
	    // Codigo para conseguir a los agentes que necesiten ayuda
		
		for (IComponentIdentifier receptor : cidsPerson) {
			IFuture<IGetInformationService> persona = getAgent().getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IGetInformationService.class, receptor);

			persona.addResultListener(new IResultListener<IGetInformationService>() {

				@Override
				public void resultAvailable(IGetInformationService result) {

					if(result.getSalud()<70){
						listado.add(receptor);
					}

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
		}
		
		//listado.add(this.agent.getComponentIdentifier());
		return listado;
	}


	
}
