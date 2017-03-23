package sisobeem.agent.emergency;

import static sisobeem.artifacts.Log.getLog;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import java.util.zip.ZipInputStream;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentFeature;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.print.pojo.EmergencyPojo;
import sisobeem.core.simulation.SimulationConfig;
import sisobeem.services.coordinadorService.ISetBeliefService;
import sisobeem.services.coordinadorService.ISetZone;
import sisobeem.services.personServices.EmergencyEstadisticasService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;
import sisobeem.services.zoneServices.IGetInformationZoneService;
import sisobeem.services.zoneServices.IMapaService;
import sisobeem.services.zoneServices.ISetInformationService;
import sisobeem.utilities.Random;

@Agent
@Description("CoordinadorEmergenciaAgentBDI: encargado de coordinar los elementos para atender la emergencia")
@RequiredServices({

})
@ProvidedServices({ @ProvidedService(type = ISetStartService.class), @ProvidedService(type = ISetZone.class) })
public class CoordinadorEmergenciaAgentBDI implements ISetStartService, ISetZone {

	@Belief
	Boolean start;
	@Agent
	protected IInternalAccess agent;

	Coordenada dimMapa;

	private EmergencyPojo salud, seguridad, busqueda;

	/** The bdi api. */
	@AgentFeature
	protected IBDIAgentFeature bdi;

	@Belief
	IComponentIdentifier cidZone;

	ArrayList<IComponentIdentifier> cidsBusqueda, cidsSeguridad, cidsSalud;

	ArrayList<Coordenada> puntosSeguros;

	ArrayList<Coordenada> puntosInseguros;
	ArrayList<Coordenada> puntosBusqueda;

	// Stack<Coordenada> pilaSeguros = new Stack<Coordenada>();
	// Stack<Coordenada> pilaInSeguros = new Stack<Coordenada>();
	// Stack<Coordenada> pilaBusqueda = new Stack<Coordenada>();
	// Coordenada[] puntosBusqueda = new Coordenada[4];
	int contadorPuntosSeguros;
	int contadorPuntosInSeguros;
	int contadorPuntosBusqueda;
	
	int duracion;

	// Argumentos
	Map<String, Object> arguments;

	ArrayList<IComponentIdentifier> total;

	/**
	 * Configuraciones Iniciales
	 */
	@SuppressWarnings("unchecked")
	@AgentCreated
	public void init() {
		// Accedemos a los argumentos del agente
		this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();
		// Obtenemos los cid de las personas en la Zona
		cidsBusqueda = (ArrayList<IComponentIdentifier>) arguments.get("cidsBusqueda");
		cidsSeguridad = (ArrayList<IComponentIdentifier>) arguments.get("cidsSeguridad");
		cidsSalud = (ArrayList<IComponentIdentifier>) arguments.get("cidsSalud");
		this.total = new ArrayList<IComponentIdentifier>();
		this.total.addAll(cidsBusqueda);
		this.total.addAll(cidsSalud);
		this.total.addAll(cidsSeguridad);

		contadorPuntosSeguros = 0;
		contadorPuntosInSeguros = 0;
		contadorPuntosBusqueda = 0;

		this.salud = new EmergencyPojo(this.agent.getComponentIdentifier().getLocalName(), "salud");

		this.seguridad = new EmergencyPojo(this.agent.getComponentIdentifier().getLocalName(), "seguridad");

		this.busqueda = new EmergencyPojo(this.agent.getComponentIdentifier().getLocalName(), "busqueda");

	}

	/**
	 * Cuerpo Principal del agente
	 */
	@AgentBody
	public void body() {

	}

	@Override
	public void setStart(Boolean s) {
		start = s;
		setStartToAgents();

		getLog().setDebug("Coordinador Emergencia Iniciado...");

		ConsultarDimensionesMapa();
		this.getPuntosSeguros();
		this.getPuntosInseguros();

		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(3000).get();

		if (this.puntosInseguros != null) {

			setPuntosSegurosToAgents();

			agent.getComponentFeature(IExecutionFeature.class).waitForDelay(3000).get();
			OrganizarBusqueda();
			OrganizarSeguridad();
			OrganizarSalud();

		} else {
			System.err.println("NULLLLLLLLLLLL");
		}

		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(duracion*1000).get();
		getEstadisticas();
		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(2000).get();
		SendEstadisticas();

	}

	public void getEstadisticas() {
		getEstadisticasSalud();
		getEstadisticasSeguridad();
		getEstadisticasBusqueda();
	}

	private void getEstadisticasSalud() {
		for (IComponentIdentifier a : cidsSalud) {

			IFuture<EmergencyEstadisticasService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(EmergencyEstadisticasService.class, a);

			persona.addResultListener(new IResultListener<EmergencyEstadisticasService>() {

				@Override
				public void resultAvailable(EmergencyEstadisticasService result) {

					EmergencyPojo r = result.getEstadisticas();

					salud.setPacientes(r.getPacientes() + salud.getPacientes());
					salud.setPersonasAtendidas(r.getPersonasAtendidas() + salud.getPersonasAtendidas());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error al capturar estadisticas de salud");
				}

			});
		}
	}

	private void getEstadisticasSeguridad() {
		for (IComponentIdentifier a : cidsSeguridad) {

			IFuture<EmergencyEstadisticasService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(EmergencyEstadisticasService.class, a);

			persona.addResultListener(new IResultListener<EmergencyEstadisticasService>() {

				@Override
				public void resultAvailable(EmergencyEstadisticasService result) {

					EmergencyPojo r = result.getEstadisticas();

					seguridad.setPersonasAyudadas(r.getPersonasAyudadas() + seguridad.getPersonasAyudadas());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error al capturar estadisticas de Seguridad");
				}

			});
		}
	}

	private void getEstadisticasBusqueda() {

		busqueda.setPuntosCubiertos(cidsBusqueda.size());
		for (IComponentIdentifier a : cidsBusqueda) {

			IFuture<EmergencyEstadisticasService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(EmergencyEstadisticasService.class, a);

			persona.addResultListener(new IResultListener<EmergencyEstadisticasService>() {

				@Override
				public void resultAvailable(EmergencyEstadisticasService result) {

					EmergencyPojo r = result.getEstadisticas();

					busqueda.setPersonasAyudadas(r.getPersonasAyudadas() + busqueda.getPersonasAyudadas());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error al capturar estadisticas de Busqueda");
				}

			});
		}
	}
	
	
	public void SendEstadisticas(){
		
		//salud
		IFuture<ISetInformationService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(ISetInformationService.class, cidZone);

		persona.addResultListener(new IResultListener<ISetInformationService>() {

			@Override
			public void resultAvailable(ISetInformationService result) {

			  result.setEstadisticasEmergencia(salud);
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setWarn(exception.getMessage());
				getLog().setWarn("Error al enviar estadisticas al zone");
			}

		});
		
		
		//busqueda
				IFuture<ISetInformationService> a = agent.getComponentFeature(IRequiredServicesFeature.class)
						.searchService(ISetInformationService.class, cidZone);

				a.addResultListener(new IResultListener<ISetInformationService>() {

					@Override
					public void resultAvailable(ISetInformationService result) {

					  result.setEstadisticasEmergencia(busqueda);
					}

					@Override
					public void exceptionOccurred(Exception exception) {
						getLog().setWarn(exception.getMessage());
						getLog().setWarn("Error al enviar estadisticas al zone");
					}

				});
				
				//seguridad
				IFuture<ISetInformationService> b = agent.getComponentFeature(IRequiredServicesFeature.class)
						.searchService(ISetInformationService.class, cidZone);

				b.addResultListener(new IResultListener<ISetInformationService>() {

					@Override
					public void resultAvailable(ISetInformationService result) {

					  result.setEstadisticasEmergencia(seguridad);
					}

					@Override
					public void exceptionOccurred(Exception exception) {
						getLog().setWarn(exception.getMessage());
						getLog().setWarn("Error al enviar estadisticas al zone");
					}

				});
	}

	public void ConsultarDimensionesMapa() {

		IFuture<IMapaService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IMapaService.class, cidZone);

		persona.addResultListener(new IResultListener<IMapaService>() {

			@Override
			public void resultAvailable(IMapaService result) {

				dimMapa = result.getTamañoMapa();

				llenarPilaPuntosBusqueda();
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setWarn(exception.getMessage());
			}

		});
	}

	public void OrganizarSeguridad() {

		for (IComponentIdentifier a : cidsSeguridad) {

			IFuture<ISetBeliefPersonService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, a);

			persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {

					if (contadorPuntosInSeguros == puntosInseguros.size()) {
						contadorPuntosInSeguros = 0;
					}
					Coordenada x = puntosInseguros.get(contadorPuntosInSeguros);

					result.setDestiny(x);
					contadorPuntosInSeguros++;

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error organizar seguridad");
				}

			});
		}

	}

	private void OrganizarSalud() {
		// int cont= 0;
		// int num = this.puntosSeguros.size();
		for (IComponentIdentifier a : cidsSalud) {

			IFuture<ISetBeliefPersonService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, a);

			persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {

					if (contadorPuntosSeguros == puntosSeguros.size()) {
						contadorPuntosSeguros = 0;
					}
					Coordenada x = puntosSeguros.get(contadorPuntosSeguros);

					result.setDestiny(x);
					contadorPuntosSeguros++;
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error organizar salud");
				}

			});

		}

	}

	private void OrganizarBusqueda() {

		// int cont= 0;
		for (IComponentIdentifier a : cidsBusqueda) {

			IFuture<ISetBeliefPersonService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, a);

			// cont++;
			persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {

					if (contadorPuntosBusqueda == puntosBusqueda.size()) {
						contadorPuntosBusqueda = 0;
					}

					Coordenada x = puntosBusqueda.get(contadorPuntosBusqueda);
					result.setDestiny(x);
					contadorPuntosBusqueda++;

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error organizar busqueda");
				}

			});

		}

	}

	private void setPuntosSegurosToAgents() {
		Coordenada[] p = ArrayListToArray(puntosSeguros);
		for (IComponentIdentifier a : cidsSalud) {
			IFuture<ISetBeliefService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefService.class, a);
			persona.addResultListener(new IResultListener<ISetBeliefService>() {

				@Override
				public void resultAvailable(ISetBeliefService result) {

					result.setPuntosSeguros(p);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error al enviar puntos seguros salud");
				}

			});
		}
		
		for (IComponentIdentifier a : cidsBusqueda) {
			IFuture<ISetBeliefService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefService.class, a);
			persona.addResultListener(new IResultListener<ISetBeliefService>() {

				@Override
				public void resultAvailable(ISetBeliefService result) {

					result.setPuntosSeguros(p);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error al enviar puntos seguros busqueda");
				}

			});
		}
		
		for (IComponentIdentifier a : cidsSeguridad) {
			IFuture<ISetBeliefService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefService.class, a);
			persona.addResultListener(new IResultListener<ISetBeliefService>() {

				@Override
				public void resultAvailable(ISetBeliefService result) {

					result.setPuntosSeguros(p);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error al enviar puntos seguros seguridad");
				}

			});
		}

	}

	public Coordenada[] ArrayListToArray(ArrayList<Coordenada> lista) {
		Coordenada[] a = new Coordenada[lista.size()];
		int i = 0;
		for (Coordenada agent : lista) {
			a[i] = agent;
			i++;
		}

		return a;
	}

	public void getPuntosSeguros() {

		IFuture<IGetInformationZoneService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IGetInformationZoneService.class, cidZone);
		persona.addResultListener(new IResultListener<IGetInformationZoneService>() {

			@Override
			public void resultAvailable(IGetInformationZoneService result) {

				puntosSeguros = result.getPuntosSeguros();
				llenarPilaPuntosSeguros(puntosSeguros);
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setWarn(exception.getMessage());
				getLog().setWarn("Error al traer los puntos seguross");
			}

		});
	}

	private void llenarPilaPuntosSeguros(ArrayList<Coordenada> s) {

		/**
		 * int cont =0; for (IComponentIdentifier agent : cidsSalud) { if(cont
		 * == s.size()){ cont =0; pilaSeguros.push(s.get(cont)); cont++; }else{
		 * pilaSeguros.push(s.get(cont)); cont++; } }
		 * 
		 */
	}

	private void llenarPilaPuntosInnSeguros(ArrayList<Coordenada> s) {

		/*
		 * int cont =0; for (IComponentIdentifier agent : cidsSeguridad) {
		 * if(cont == s.size()){ cont =0; pilaInSeguros.push(s.get(cont));
		 * cont++; }else{ pilaInSeguros.push(s.get(cont)); cont++; } }+
		 */
	}

	private void llenarPilaPuntosBusqueda() {

		ArrayList<Coordenada> lista = new ArrayList<Coordenada>();
		lista.add(new Coordenada(this.dimMapa.getX() - 1, this.dimMapa.getY() - 1));
		lista.add(new Coordenada(this.dimMapa.getX() - 5, 5));
		lista.add(new Coordenada((this.dimMapa.getX() - 1) / 2, (this.dimMapa.getY() - 1) / 2));
		lista.add(new Coordenada(5, this.dimMapa.getY() - 5));
		/*
		 * int cont =0; for (IComponentIdentifier agent : cidsBusqueda) {
		 * if(cont == lista.size()){ cont =0; pilaSeguros.push(lista.get(cont));
		 * cont++; }else{ pilaSeguros.push(lista.get(cont)); cont++; } }
		 */

		puntosBusqueda = lista;

	}

	public void getPuntosInseguros() {

		IFuture<IGetInformationZoneService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IGetInformationZoneService.class, cidZone);
		persona.addResultListener(new IResultListener<IGetInformationZoneService>() {

			@Override
			public void resultAvailable(IGetInformationZoneService result) {

				puntosInseguros = result.getPuntosInseguros();
				// llenarPilaPuntosInnSeguros(puntosInseguros);
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setWarn(exception.getMessage());
			}

		});
	}

	private void setStartToAgents() {

		for (IComponentIdentifier person : this.cidsBusqueda) {

			IFuture<ISetStartService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetStartService.class, person);
			persona.addResultListener(new IResultListener<ISetStartService>() {

				@Override
				public void resultAvailable(ISetStartService result) {

					result.setStart(true);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error al Star agents busqueda");
				}

			});

		}

		for (IComponentIdentifier person : this.cidsSalud) {

			IFuture<ISetStartService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetStartService.class, person);
			persona.addResultListener(new IResultListener<ISetStartService>() {

				@Override
				public void resultAvailable(ISetStartService result) {

					result.setStart(true);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error al Star agents salud");
				}

			});

		}

		for (IComponentIdentifier person : this.cidsSeguridad) {

			IFuture<ISetStartService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetStartService.class, person);
			persona.addResultListener(new IResultListener<ISetStartService>() {

				@Override
				public void resultAvailable(ISetStartService result) {

					result.setStart(true);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn(exception.getMessage());
					getLog().setWarn("Error al Star agents Seguridad");
				}

			});

		}
	}

	private void sendBelief() {
		sendBeliefToBusqueda();
		sendBeliefToSalud();
		sendBeliefToSeguridad();
	}

	private void sendBeliefToBusqueda() {

		for (IComponentIdentifier person : this.cidsBusqueda) {

			IFuture<ISetBeliefPersonService> personService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			personService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setZone(cidZone);
					result.setToCoordinador(agent.getComponentIdentifier());

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn("Error al enviar el zone a busqued");
				}

			});

		}
	}

	private void sendBeliefToSalud() {

		for (IComponentIdentifier person : this.cidsSalud) {

			IFuture<ISetBeliefPersonService> personService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			personService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setZone(cidZone);
					result.setToCoordinador(agent.getComponentIdentifier());

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn("Error al enviar el zone a salud");
				}

			});

		}
	}

	private void sendBeliefToSeguridad() {

		for (IComponentIdentifier person : this.cidsSeguridad) {

			IFuture<ISetBeliefPersonService> personService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			personService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setZone(cidZone);
					result.setToCoordinador(agent.getComponentIdentifier());

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setWarn("Error al enviar el zone a seguridad");
				}

			});

		}
	}

	@Override
	public ArrayList<IComponentIdentifier> setZone(IComponentIdentifier zone) {
		this.cidZone = zone;

		sendBelief();

		ArrayList<IComponentIdentifier> agents = new ArrayList<IComponentIdentifier>();

		agents.addAll(this.cidsSeguridad);
		agents.addAll(this.cidsSalud);
		agents.addAll(this.cidsBusqueda);

		return agents;
	}

	@Override
	public void duracion(int dura) {
		this.duracion = dura;
		
	}

}
