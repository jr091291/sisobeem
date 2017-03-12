package sisobeem.agent.enviroment;

import static sisobeem.artifacts.Log.getLog;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.annotation.Service;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.AgentKilled;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Cronometro;
import sisobeem.artifacts.EstructuraPuntoMapa;
import sisobeem.artifacts.Mensajero;
import sisobeem.artifacts.Ubicacion;
import sisobeem.artifacts.print.EdificeAction;
import sisobeem.artifacts.print.MoveAction;
import sisobeem.artifacts.print.PersonAction;
import sisobeem.artifacts.print.PuntoAction;
import sisobeem.artifacts.print.RouteAction;
import sisobeem.artifacts.print.pojo.EdificePojo;
import sisobeem.artifacts.print.pojo.MovePojo;
import sisobeem.artifacts.print.pojo.PersonPojo;
import sisobeem.artifacts.print.pojo.PuntoPojo;
import sisobeem.artifacts.print.pojo.RoutePojo;
import sisobeem.core.simulation.EdificesConfig;
import sisobeem.core.simulation.EmergencyConfig;
import sisobeem.core.simulation.PersonsConfig;
import sisobeem.core.simulation.SimulationConfig;
import sisobeem.interfaces.ISismo;
import sisobeem.services.coordinadorService.ISetZone;
import sisobeem.services.edificeServices.IGetEstadisticasService;
import sisobeem.services.edificeServices.ISetBeliefEdificeService;
import sisobeem.services.personServices.IGetInformationService;
import sisobeem.services.personServices.IReceptorMensajesService;
import sisobeem.services.personServices.ISetBeliefPersonService;
import sisobeem.services.personServices.ISetStartService;
import sisobeem.services.zoneServices.IAdicionarPersonasService;
import sisobeem.services.zoneServices.IGetDestinyService;
import sisobeem.services.zoneServices.IGetInformationZoneService;
import sisobeem.services.zoneServices.IMapaService;
import sisobeem.services.zoneServices.ISetInformationService;
import sisobeem.utilities.Random;
import sisobeem.utilities.Sismo;
import sisobeem.utilities.Traslator;
import sisobeem.websocket.client.zoneClientEndpoint;

@Agent
@Description("Zone: Ambiente Superior, Mapa.")
@Service
@RequiredServices({ @RequiredService(name = "ISetUbicacionService", type = ISetBeliefPersonService.class),
		@RequiredService(name = "ISetBeliefEdificeService", type = ISetBeliefEdificeService.class),
		@RequiredService(name = "ISetZone", type = ISetZone.class),
		@RequiredService(name = "ISetStartService", type = ISetStartService.class) })
@ProvidedServices({ @ProvidedService(name = "IMapaService", type = IMapaService.class),
		@ProvidedService(name = "IAdicionarPersonasService", type = IAdicionarPersonasService.class),
		@ProvidedService(name = "IGetInformationZoneService", type = IGetInformationZoneService.class),
		@ProvidedService(name = "ISetInformationService", type = ISetInformationService.class),
		@ProvidedService(name = "IGetDestinyService", type = IGetDestinyService.class) })
public class ZoneAgentBDI extends EnviromentAgentBDI implements IMapaService, ISismo, IAdicionarPersonasService,
		IGetDestinyService, IGetInformationZoneService, ISetInformationService {

	/**
	 * Contextos
	 */
	@Belief
	public Boolean contextSendMsg;

	int contador = 0;
	

	/**
	 * Bandeja de mensajes
	 */
	Map<String, String> bandejaMsg = new HashMap<String, String>(); // Bandeja
																	// de
																	// mensajes
	
	ArrayList<RoutePojo> rutasDisponibles = new ArrayList<RoutePojo>();
	

	/**
	 * Motores + clientPoint
	 */
	private Mensajero m1, m2, m3, m4;
	private zoneClientEndpoint clientEndPoint, clientEndPoint2, clientEndPoint3, clientEndPoint4, clientEndPoint5,
			clientEndPoint6, clientEndPoint7, clientEndPoint8, clientEndPoint9, clientEndPoint10, clientEndPoint11,
			clientEndPoint12, clientEndPoint13, clientEndPoint14, clientEndPoint15, clientEndPoint16, clientEndPoint17,
			clientEndPoint18, clientEndPoint19, clientEndPoint20, clientEndPoint21, clientEndPoint22, clientEndPoint23,
			clientEndPoint24, clientEndPoint25, clientEndPoint26, clientEndPoint27, clientEndPoint28, clientEndPoint29,
			clientEndPoint30, clientEndPoint31, clientEndPoint32, clientEndPoint33, clientEndPoint34, clientEndPoint35,
			clientEndPoint36, clientEndPoint37, clientEndPoint38, clientEndPoint39, clientEndPoint40;

	/**
	 * Creencias
	 */

	@Belief
	EstructuraPuntoMapa[][] mapa;

	@Belief
	IComponentIdentifier coordinador;

	@Belief
	private Sismo sismo;

	private ArrayList<IComponentIdentifier> cidsEdifices;

	@Belief
	private Cronometro cronometro;

	/**
	 * Configuracion de la simulacion
	 */
	@Belief
	SimulationConfig simulacionConfig;
	EdificesConfig[] edConfig;

	/**
	 * Método para inicializar creencias
	 */
	@SuppressWarnings("unchecked")
	@AgentCreated
	public void init() {
		
		this.contMsgAyuda= new ArrayList<IComponentIdentifier>();
		this.contMsgDeCalma=new ArrayList<IComponentIdentifier>();
		this.contMsgDeConfianza= new ArrayList<IComponentIdentifier>();
		this.contMsgFrsutracion=new ArrayList<IComponentIdentifier>();
		this.contMsgHostilidad=new ArrayList<IComponentIdentifier>();
		this.contMsgPanico= new ArrayList<IComponentIdentifier>();
		this.contMsgPrimerosAux= new ArrayList<IComponentIdentifier>();
		this.contMsgResguardo= new ArrayList<IComponentIdentifier>();
		this.contMsgMotivacion= new ArrayList<IComponentIdentifier>();

		getLog().setInfo("Agente " + agent.getComponentIdentifier().getLocalName() + " creado");

		this.cidsEdifices = new ArrayList<IComponentIdentifier>();

		// Accedemos a los argumentos del agente
		this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();

		// Obtenemos los cid de las personas en la Zona
		cidsPerson = (ArrayList<IComponentIdentifier>) arguments.get("cidsPerson");

		// Obtenemos los cid de las edificaciones
		cidsEdifices = (ArrayList<IComponentIdentifier>) arguments.get("cidsEdifices");
		
		//Inicializamos el listado de agentes muertos
		this.cidPersonDead = new ArrayList<IComponentIdentifier>();

		// Datos de la simulacion
		this.simulacionConfig = (SimulationConfig) this.arguments.get("simulacionConfig");

		this.coordinador = (IComponentIdentifier) this.arguments.get("cidEmergencia");

		this.edConfig = (EdificesConfig[]) this.arguments.get("edificesConfig");

		/**
		 * Creacion del mapa
		 */
		try {
			this.mapa = new EstructuraPuntoMapa[(int) this.arguments.get("x")][(int) this.arguments.get("y")];
		} catch (Exception e) {
			getLog().setError("Mapa demasiado grande");
		}

		/**
		 * Configurando los sockets y los mensajeros
		 */
		this.connectServer();
		createMensajeros();

		/**
		 * Configurando los agentes
		 */
		asignarCoordenadasIniciales();
		sendCoordenadasIniciales();
		setMetoPerson();
		setMetoEdifices();
		sendMeToCoordinator();

		/**
		 * Iniciando agentes
		 */
		startAgents();

	}

	@AgentBody
	public void body() {

		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(4000).get();
		contextSendMsg = true;
		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(4000).get();

		// Cronometro crono = new Cronometro();
		// crono.iniciarCronometro();

		// Iniciamos el sismo
		this.iniciarSismo();
		// Iniciamos mecanismos de atencion de emergencias
		agent.getComponentFeature(IExecutionFeature.class)
				.waitForDelay((this.simulacionConfig.getDuracionSismo() * 1000)).get();
		this.startCoordinadorEmergencia();
		
		agent.getComponentFeature(IExecutionFeature.class)
		.waitForDelay(((this.simulacionConfig.getDuracion()- this.simulacionConfig.getDuracionSismo())* 1000)).get();
		getEstadisticas();

	}
	
	private void getEstadisticas(){
		for (IComponentIdentifier cid : cidsEdifices) {
			IFuture<IGetEstadisticasService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IGetEstadisticasService.class, cid);

			persona.addResultListener(new IResultListener<IGetEstadisticasService>() {

				@Override
				public void resultAvailable(IGetEstadisticasService result) {
					EdificePojo d = result.getEstiditicas();
					
					
					System.out.println("Estadisticas del edificio: "+d.getIdAgent());					
					System.out.println("Derrumbado : "+d.getDerrumbado());
					System.out.println("Msj Ayuda : "+d.getMsgAyuda());
					System.out.println("Msj Calma : "+d.getMsgDeCalma());
					System.out.println("Msj Confianza : "+d.getMsgDeConfianza());
					System.out.println("Msj Frsutracion : "+d.getMsgFrsutracion());
					System.out.println("Msj Hostilidad : "+d.getMsgHostilidad());
					System.out.println("Msj Motivacion : "+d.getMsgMotivacion());
					System.out.println("Msj Panico : "+d.getMsgPanico());
					System.out.println("Msj Primeros Aux : "+d.getMsgPrimerosAux());
					System.out.println("Msj Resguardo : "+d.getMsgResguardo());
				
					System.out.println("Atrapados : "+d.getPersonasAtrapadas());
					System.out.println("Muertes totales : "+d.getPersonasMuertas());
					System.out.println("Suicidios : "+d.getSuicidios());
					
					EdificeAction info = new EdificeAction("estadistica",d);
					bandejaMsg.put(agent.getComponentIdentifier().getLocalName() + contador, info.toJson());
                    contador++;
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});

		}
		
		 EdificePojo datos = new EdificePojo(getAgent().getComponentIdentifier().getLocalName(),Traslator.getTraslator().getUbicacion(new Coordenada(0,0)) , "estadisticas"); 
			
			//	datos.setPersonasMuertas(this.cidPersonDead.size());
			
					datos.setMsgAyuda(this.contMsgAyuda.size());
					datos.setMsgDeCalma(this.contMsgDeCalma.size());
					datos.setMsgDeConfianza(this.contMsgDeConfianza.size());
					datos.setMsgFrsutracion(this.contMsgFrsutracion.size());
					datos.setMsgHostilidad(this.contMsgHostilidad.size());
					datos.setMsgMotivacion(this.contMsgMotivacion.size());
					datos.setMsgPanico(this.contMsgPanico.size());
					datos.setMsgPrimerosAux(this.contMsgPrimerosAux.size());
					datos.setMsgResguardo(this.contMsgResguardo.size());
		
					EdificeAction info = new EdificeAction("estadistica",datos);
					bandejaMsg.put(agent.getComponentIdentifier().getLocalName() + contador, info.toJson());
                    contador++;
		
		System.out.println("********************************************************************");
		System.out.println("Estadisticas del edificio: "+agent.getComponentIdentifier().getLocalName());					
		//System.out.println("Derrumbado : "+result.getEstiditicas().getDerrumbado());
		System.out.println("Msj Ayuda : "+this.contMsgAyuda.size());
		System.out.println("Msj Calma : "+this.contMsgDeCalma.size());
		System.out.println("Msj Confianza : "+this.contMsgDeConfianza.size());
		System.out.println("Msj Frsutracion : "+this.contMsgFrsutracion.size());
		System.out.println("Msj Hostilidad : "+this.contMsgHostilidad.size());
		System.out.println("Msj Motivacion : "+this.contMsgMotivacion.size());
		System.out.println("Msj Panico : "+this.contMsgPanico.size());
		System.out.println("Msj Primeros Aux : "+this.contMsgPrimerosAux.size());
		System.out.println("Msj Resguardo : "+this.contMsgResguardo.size());

	}

	/**
	 * Metodo que instancia los 4 mensajeros establecidos
	 */
	public void createMensajeros() {

		m1 = new Mensajero(0, this.cidsPerson.size() / 4, this.cidsPerson, bandejaMsg, clientEndPoint, clientEndPoint2,
				clientEndPoint3, clientEndPoint4, clientEndPoint5, clientEndPoint6, clientEndPoint7, clientEndPoint8,
				clientEndPoint9, clientEndPoint10);

		m2 = new Mensajero(this.cidsPerson.size() / 4, this.cidsPerson.size() / 2, this.cidsPerson, bandejaMsg,
				clientEndPoint11, clientEndPoint12, clientEndPoint13, clientEndPoint14, clientEndPoint15,
				clientEndPoint16, clientEndPoint17, clientEndPoint18, clientEndPoint19, clientEndPoint20);

		m3 = new Mensajero(this.cidsPerson.size() / 2, this.cidsPerson.size() * 3 / 4, this.cidsPerson, bandejaMsg,
				clientEndPoint21, clientEndPoint22, clientEndPoint23, clientEndPoint24, clientEndPoint25,
				clientEndPoint26, clientEndPoint27, clientEndPoint28, clientEndPoint29, clientEndPoint30);

		m4 = new Mensajero(this.cidsPerson.size() * 3 / 4, this.cidsPerson.size(), this.cidsPerson, bandejaMsg,
				clientEndPoint31, clientEndPoint32, clientEndPoint33, clientEndPoint34, clientEndPoint35,
				clientEndPoint36, clientEndPoint37, clientEndPoint38, clientEndPoint39, clientEndPoint40);
		
	//	repartidor = new RepartidorDeRutas(this.getAgent(),this.rutasDisponibles,this.cidsPerson,this.coordinador);

	}

	/**
	 * Método que activa o desactiva los motores de enviados de mensaje segun el
	 * contextSendMsg cambie
	 */
	@Plan(trigger = @Trigger(factchangeds = "contextSendMsg"))
	public void controladorCentroDeMensajes() {

		getLog().setInfo("Enviando mensajes a la vista");
		if (contextSendMsg) {
			m1.iniciar();
			m2.iniciar();
			m3.iniciar();
			m4.iniciar();
		//	repartidor.iniciar();
		} else {
			m1.parar();
			m2.parar();
			m3.parar();
			m4.parar();
		//	repartidor.parar();
		}
	}

	/**
	 * Metodo para conectar al socket de comunicacion con la vista
	 */
	public void connectServer() {
		try {
			clientEndPoint = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex"));
			clientEndPoint2 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex2"));
			clientEndPoint3 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex3"));
			clientEndPoint4 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex4"));
			clientEndPoint5 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex5"));
			clientEndPoint6 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex6"));
			clientEndPoint7 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex7"));
			clientEndPoint8 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex8"));
			clientEndPoint9 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex9"));
			clientEndPoint10 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex10"));
			clientEndPoint11 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex11"));
			clientEndPoint12 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex12"));
			clientEndPoint13 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex13"));
			clientEndPoint14 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex14"));
			clientEndPoint15 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex15"));
			clientEndPoint16 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex16"));
			clientEndPoint17 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex17"));
			clientEndPoint18 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex18"));
			clientEndPoint19 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex19"));
			clientEndPoint20 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex20"));
			clientEndPoint21 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex21"));
			clientEndPoint22 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex22"));
			clientEndPoint23 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex23"));
			clientEndPoint24 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex24"));
			clientEndPoint25 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex25"));
			clientEndPoint26 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex26"));
			clientEndPoint27 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex27"));
			clientEndPoint28 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex28"));
			clientEndPoint29 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex29"));
			clientEndPoint30 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex30"));
			clientEndPoint31 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex31"));
			clientEndPoint32 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex32"));
			clientEndPoint33 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex33"));
			clientEndPoint34 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex34"));
			clientEndPoint35 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex35"));
			clientEndPoint36 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex36"));
			clientEndPoint37 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex37"));
			clientEndPoint38 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex38"));
			clientEndPoint39 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex39"));
			clientEndPoint40 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex40"));

			// add listener
			clientEndPoint.addMessageHandler(new zoneClientEndpoint.MessageHandler() {
				public void handleMessage(String message) {
					
					/*
					Gson gson = new Gson();
				 	JsonParser parser = new JsonParser();
				    JsonArray array = parser.parse(message).getAsJsonArray();
				    IComponentIdentifier sim = gson.fromJson(array.get(0), IComponentIdentifier.class);
				    Ubicacion[] edi = gson.fromJson(array.get(1), Ubicacion[].class );
				 */
				    
					//System.out.println(message);
					//System.out.println("Ruta agregada!");
					Gson gson = new Gson();
					RoutePojo route = gson.fromJson(message, RoutePojo.class);
					//rutasDisponibles.add(route);
					
					ArrayList<Coordenada> ruta = new ArrayList<Coordenada>();

					for (Ubicacion u : route.getCoordenadas()) {
						ruta.add(Traslator.traductor.getCoordenada(u));
					}
                     
					IComponentIdentifier ag = getAgent(route.getAgent());
					if(ag!=null){
						System.out.println("Existe este agente!");
						IFuture<ISetBeliefPersonService> zoneService = getAgent()
								.getComponentFeature(IRequiredServicesFeature.class)
								.searchService(ISetBeliefPersonService.class, ag);

						zoneService.addResultListener(new IResultListener<ISetBeliefPersonService>() {

							@Override
							public void resultAvailable(ISetBeliefPersonService result) {
								System.out.println("Ruta enviada a :"+ag.getLocalName());
								result.setRute(ruta);
							}

							@Override
							public void exceptionOccurred(Exception exception) {
								getLog().setError(exception.getMessage());
							}

						});
					}
				}
			});

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

		/**
		 * Metodo para conseguir el Identificador de un agente por su nombre
		 * @param name
		 * @return
		 */
     public IComponentIdentifier getAgent(String name){
			
			for (IComponentIdentifier p : this.cidsPerson) {
				if(p.getLocalName().equals(name)){
					return p;
				}
			}
			
			if(this.coordinador.getLocalName().equals(name)){
				return this.coordinador;
			}
			return null;
			
		}
	/**
	 * Método para enviar mensajes a la vista
	 * 
	 * @param mensaje
	 */
	public void sendMensaje(zoneClientEndpoint cliendtEndPoint, String mensaje) {
		cliendtEndPoint.sendMessage(mensaje);
	}

	/**
	 * Metodo para enviar el cidZone a los agentes transeuntes
	 */
	private void setMetoPerson() {

		getLog().setInfo("Asignando creencias a los Personas");
		for (IComponentIdentifier person : this.cidsPerson) {

			IFuture<ISetBeliefPersonService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					// System.out.println("Asignando Zone a Persona");
					result.setZone(getAgent().getComponentIdentifier());

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});

		}
	}

	/**
	 * Metodo para enviar el cidZone a los agentes Edificios
	 */
	private void setMetoEdifices() {

		getLog().setInfo("Asignando creencias a los edificios");

		for (IComponentIdentifier person : this.cidsEdifices) {

			IFuture<ISetBeliefEdificeService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefEdificeService.class, person);

			persona.addResultListener(new IResultListener<ISetBeliefEdificeService>() {

				@Override
				public void resultAvailable(ISetBeliefEdificeService result) {
					// System.out.println("Asignando Zone al edificio");
					result.setZone(getAgent().getComponentIdentifier());

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});

		}
	}

	/**
	 * Metodo para enviar el cidZone al coordinador de la emrgencia
	 */
	private void sendMeToCoordinator() {

		IFuture<ISetZone> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(ISetZone.class, this.coordinador);

		persona.addResultListener(new IResultListener<ISetZone>() {

			@Override
			public void resultAvailable(ISetZone result) {

				result.setZone(agent.getComponentIdentifier());
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setWarn(exception.getMessage());
			}

		});
	}

	/**
	 * Metodo para enviar la señal de busqueda de objetivos
	 */
	private void startAgents() {
		getLog().setInfo("Start Agents");
		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {

				for (IComponentIdentifier person : this.mapa[i][j].getAgents()) {

					IFuture<ISetStartService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
							.searchService(ISetStartService.class, person);

					// System.out.println("Tu Ubicacion es: "+c.getX()+" -
					// "+c.getY()+" Agente: "+person.getName());
					persona.addResultListener(new IResultListener<ISetStartService>() {

						@Override
						public void resultAvailable(ISetStartService result) {

							result.setStart(true);
						}

						@Override
						public void exceptionOccurred(Exception exception) {
							getLog().setWarn(exception.getMessage());
						}

					});

				}
			}
		}

	}

	private void startCoordinadorEmergencia() {

		// System.out.println("Entro");
		IFuture<ISetStartService> coordinador = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(ISetStartService.class, this.coordinador);

		coordinador.addResultListener(new IResultListener<ISetStartService>() {

			@Override
			public void resultAvailable(ISetStartService result) {

				result.setStart(true);
			}

			@Override
			public void exceptionOccurred(Exception exception) {
				getLog().setWarn(exception.getMessage());
			}

		});
	}

	/**
	 * Metodo para Asignar a los transeuntes en una Coordenada al Azar
	 */
	public void asignarCoordenadasIniciales() {

		getLog().setInfo("Asignandondo Coordenadas Iniciales...");
		/**
		 * Instanciamos Las esctrucutras del mapa con su respectivo ArrayList
		 */
		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				this.mapa[i][j] = new EstructuraPuntoMapa();
			}
		}

		/**
		 * Asignacion coordenadas edificios, segun ubicacion elegida en la
		 * configuracion
		 */
		for (int i = 0; i < cidsEdifices.size(); i++) {
			Coordenada c = Traslator.getTraslator().getCoordenada(edConfig[i].getUbicacion());
			this.mapa[c.getX()][c.getY()].getAgents().add(cidsEdifices.get(i));
		}

		/**
		 * Asignacion coordenadas a transeutes de manera aleatoria
		 */
		for (int i = 0; i < cidsPerson.size(); i++) {
			this.mapa[Random.getIntRandom(0, this.mapa.length - 1)][Random.getIntRandom(0, this.mapa[0].length - 1)]
					.getAgents().add(cidsPerson.get(i));
		}

	}

	/**
	 * Metodo para enviar las coordenadas inciales a los agentes transeuntes
	 */
	public void sendCoordenadasIniciales() {

		getLog().setInfo("Enviado Ubicacion A los agentes");

		for (IComponentIdentifier person : this.cidsPerson) {

			IFuture<ISetBeliefPersonService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {

					Coordenada c = getCoordenada(person);
					// System.out.println("Tu Ubicacion es: "+c.getX()+"
					// - "+c.getY()+" Agente: "+person.getName());
					if (c != null) {
						result.setUbicacion(c);
					}

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});

		}
	}

	/**
	 * Método para Obtener la coordenada de un agente especifico
	 * 
	 * @param cid
	 * @return Coordenada
	 */
	public Coordenada getCoordenada(IComponentIdentifier cid) {

		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				if (this.mapa[i][j].getAgents().contains(cid)) {
					return new Coordenada(i, j);
				}
			}

		}

		return null;
	}

	@Override
	public boolean changePosition(Coordenada nueva, IComponentIdentifier cid) {

		// System.out.println("Cambiando posicion de:"+cid.getLocalName());

		if (validateInMap(nueva)) {
			Coordenada antigua = getCoordenada(cid);
			synchronized (this.mapa) {
				this.mapa[antigua.getX()][antigua.getY()].getAgents().remove(cid);
				this.mapa[nueva.getX()][nueva.getY()].getAgents().add(cid);
			}

			// for (IComponentIdentifier c :
			// this.mapa[nueva.getX()][nueva.getY()].getAgents()) {
			// System.out.println(c.getLocalName());
			// }

			// contador++;

			//

			MoveAction info = new MoveAction("move",
					new MovePojo(cid.getLocalName(), Traslator.getTraslator().getUbicacion(nueva)));
			this.bandejaMsg.put(cid.getLocalName(), info.toJson());
			// System.out.println(this.bandejaMsg.size());
			// sendMensaje();

			return true;
		} else {

			// System.out.println("Error en la direccion");
			return false;
		}

	}

	/**
	 * Método que valida si una posicion existe en el mapa o no.
	 * 
	 * @param Coordenada
	 *            c
	 * @return boolean
	 */
	private boolean validateInMap(Coordenada c) {
		// System.out.println(c.getX()+" "+this.mapa.length);
		// System.out.println(c.getY()+" "+this.mapa[0].length);
		if (c.getX() >= this.mapa.length || c.getY() >= this.mapa[0].length || c.getX() < 0 || c.getY() < 0)
			return false;

		return true;

	}

	/**
	 * Método para la creacion e inicializacion del sismo
	 */
	private void iniciarSismo() {

		int duracion = simulacionConfig.getDuracionSismo();
		int intensidad = simulacionConfig.getIntencidad();

		Sismo terremoto = new Sismo(duracion, intensidad, this);

		getLog().setInfo("Iniciando terremoto");
		terremoto.Start();

	}

	/**
	 * Metodo para enviar la intensidad del sismo a los agentes Edificios
	 */
	private void setIntensidadSismoToEdifices(double intensidad) {
		// System.out.println("enviando info sobre el sismo");
		for (IComponentIdentifier cidAgent : this.cidsEdifices) {

			IFuture<ISetBeliefEdificeService> edifice = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefEdificeService.class, cidAgent);

			edifice.addResultListener(new IResultListener<ISetBeliefEdificeService>() {

				@Override
				public void resultAvailable(ISetBeliefEdificeService result) {
					// System.out.println("Asignando Zone al edificio");
					result.setSismo(intensidad);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});

		}
	}

	/**
	 * Metodo para enviar la intensidad del sismo a los agentes transeuntes
	 */
	public void setIntensidadSismoToPerson(double intesidad) {

		// System.out.println("Enviado intensidas A los agentes");

		for (IComponentIdentifier person : this.cidsPerson) {

			IFuture<ISetBeliefPersonService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBeliefPersonService.class, person);

			persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

				@Override
				public void resultAvailable(ISetBeliefPersonService result) {
					result.setSismo(intesidad);
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setError(exception.getMessage());
				}

			});

		}
	}

	/**
	 * Método que genera el daño en la matriz general
	 * 
	 * @param intesidad
	 */
	public void generateDamage(double intesidad) {

		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				this.mapa[i][j].setDaño(Random.getDoubleRandom(1, intesidad));
			}
		}
	}

	/**
	 * Called when the agent is terminated.
	 */
	@AgentKilled
	public void killed() {

	}

	@Override
	public void setIntensidadSismo(double intensidad) {
		agent.getExternalAccess().scheduleStep(iAccess -> {
			intensidadSismo = intensidad;
			// now you are on the component's thread
			return Future.DONE;

		});
		// System.out.println("modificando intensidad: "+intensidad);
	}

	@Plan(trigger = @Trigger(factchangeds = "intensidadSismo"))
	public void notificarIntensidadSismo() {
		setIntensidadSismoToEdifices(this.intensidadSismo);
		setIntensidadSismoToPerson(this.intensidadSismo);
		generateDamage(this.intensidadSismo);
	}

	/**
	 * Método que devuelve un listado con los agentes en el rango de escucha del
	 * emisor
	 * 
	 * @param emisor
	 * @return ArrayList<IComponentIdentifier> agents
	 */
	public ArrayList<IComponentIdentifier> getAgentsInMyRange(IComponentIdentifier emisor) {

		return new ArrayList<IComponentIdentifier>();

	}

	/**
	 * Servicios de comunicacion de mensajes en rango de escucha
	 */
	@Override
	public void AyudaMsj(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
        this.contMsgAyuda.add(emisor);
		//getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Ayuda ");

		for (IComponentIdentifier receptor : listado) {

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

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
        this.contMsgPrimerosAux.add(emisor);
	//	getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de PrimerosAux ");

		for (IComponentIdentifier receptor : listado) {

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

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
        this.contMsgDeCalma.add(emisor);
	//	getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Calma ");

		for (IComponentIdentifier receptor : listado) {

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

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
         this.contMsgDeConfianza.add(emisor);
	//	getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Confianza ");

		for (IComponentIdentifier receptor : listado) {

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

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
        this.contMsgFrsutracion.add(emisor);
		//getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Frustracion ");

		for (IComponentIdentifier receptor : listado) {

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

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
        this.contMsgHostilidad.add(emisor);
		//getLog().setInfo(emisor.getLocalName() + ": Envia mensaje Hostil");

		for (IComponentIdentifier receptor : listado) {

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

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
         this.contMsgPanico.add(emisor);
		//getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Panico ");

		for (IComponentIdentifier receptor : listado) {

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

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
        this.contMsgResguardo.add(emisor);
	//	getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Resguardo ");

		for (IComponentIdentifier receptor : listado) {

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

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
       this.contMsgMotivacion.add(emisor);
	//	getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Motivacion ");

		for (IComponentIdentifier receptor : listado) {

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
	public void Team(IComponentIdentifier emisor , double liderazgo) {
		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);

	//	getLog().setInfo(emisor.getLocalName() + ": armado team ");

		for (IComponentIdentifier receptor : listado) {

			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.Team(emisor,liderazgo);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});

		}

		
	}


	@Override
	public void derrumbarEdifice(IComponentIdentifier cidEdifice) {
		Coordenada edificio = this.getCoordenada(cidEdifice);

		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				if ((i <= edificio.getX() - 8) && (i >= edificio.getX() + 8) && (j <= edificio.getY() - 8)
						&& (j >= edificio.getY() + 8)) {
					this.mapa[i][j].setDaño(9);
				}
			}
		}

		EdificeAction info = new EdificeAction("derrumbe", new EdificePojo(cidEdifice.getLocalName(),
				Traslator.getTraslator().getUbicacion(edificio), "derrumbe"));
		this.bandejaMsg.put(cidEdifice.getLocalName(), info.toJson());
	}

	@Override
	public void AdicionarPersonService(IComponentIdentifier person) {
		if (!this.cidsPerson.contains(person)) {
			getLog().setDebug("Nuevo agente recibido por el zone: " + person.getLocalName());
			this.cidsPerson.add(person);
		}
	}

	@Override
	public void getRuta(IComponentIdentifier agent, Coordenada destino) {
		Coordenada inicio = this.getCoordenada(agent);

		RouteAction info = new RouteAction("route", new RoutePojo(Traslator.getTraslator().getUbicacion(inicio),
				Traslator.getTraslator().getUbicacion(destino), agent.getLocalName()));

		this.bandejaMsg.put(agent.getLocalName(), info.toJson());

		//getLog().setDebug("Enviando mensaje con la peticion de la ruta");
	}

	@Override
	public Coordenada getDestiny(IComponentIdentifier agent) {
        ArrayList<Coordenada > sitiosSeguros = getPuntosSeguros();
        
       return sitiosSeguros.get(Random.getIntRandom(0, sitiosSeguros.size()-1));
        
	}

	@Override
	public ArrayList<Coordenada> getPuntosSeguros() {

		ArrayList<Coordenada> lista = new ArrayList<Coordenada>();

		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				if (this.mapa[i][j].getDaño() < 3) {
					lista.add(new Coordenada(i, j));
				}
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Coordenada> depurada = (ArrayList<Coordenada>) reducirPuntos(lista).clone();

		sendPuntosSeguros(depurada);

		return depurada;
	}

	private void sendPuntosSeguros(ArrayList<Coordenada> puntosSeguros) {

		for (Coordenada coordenada : puntosSeguros) {
			contador++;
			PuntoAction info = new PuntoAction("punto",
					new PuntoPojo(Traslator.getTraslator().getUbicacion(coordenada), "seguro"));
			this.bandejaMsg.put(agent.getComponentIdentifier().getLocalName() + contador, info.toJson());
		}

	}

	@Override
	public ArrayList<Coordenada> getPuntosInseguros() {

		ArrayList<Coordenada> lista = new ArrayList<Coordenada>();

		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				if (this.mapa[i][j].getDaño() > 6) {
					lista.add(new Coordenada(i, j));
				}
			}
		}

		@SuppressWarnings("unchecked")
		ArrayList<Coordenada> depurada = (ArrayList<Coordenada>) reducirPuntos(lista).clone();
		sendPuntosInseguros(depurada);
		return depurada;
	}

	private void sendPuntosInseguros(ArrayList<Coordenada> pInseguro) {

		for (Coordenada coordenada : pInseguro) {
			contador++;
			PuntoAction info = new PuntoAction("punto",
					new PuntoPojo(Traslator.getTraslator().getUbicacion(coordenada), "inseguro"));
			this.bandejaMsg.put(agent.getComponentIdentifier().getLocalName() + contador, info.toJson());
		}

	}

	/**
	 * Meotodo para reducir puntos cercanos
	 * 
	 * @param lista
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Coordenada> reducirPuntos(ArrayList<Coordenada> lista) {

		ArrayList<Coordenada> listaf = new ArrayList<Coordenada>();

		int tamaño = this.mapa.length;

		int cantidadPuntos = Random.getIntRandom(5, 10);

		int salto = tamaño / cantidadPuntos;

		int humbral = 0;

		int dindel = humbral + salto;

		for (int i = 0; i < cantidadPuntos; i++) {

			for (int j = 0; j < lista.size(); j++) {
				if (lista.get(j).getX() >= humbral && lista.get(j).getX() <= dindel && lista.get(j).getY() >= humbral
						&& lista.get(j).getY() <= dindel) {
					humbral = humbral + salto;
					dindel = humbral + salto;
					listaf.add(lista.get(j));
					j = lista.size();
				}
			}

		}

		return (ArrayList<Coordenada>) listaf.clone();

	}

	@Override
	public void setDead(IComponentIdentifier agent) {
     		PersonAction info = new PersonAction("muerte", new PersonPojo(agent.getLocalName(),
				Traslator.getTraslator().getUbicacion(this.getCoordenada(agent)), "muerte"));
		this.bandejaMsg.put(agent.getLocalName(), info.toJson());
	}

	@Override
	public ArrayList<IComponentIdentifier> getPeopleHelp(IComponentIdentifier agent) {
		// TODO Auto-generated method stub
		
		ArrayList<IComponentIdentifier> agents = this.getAgentsInMyRange(agent);
		//System.out.println("Generando Listado");
		ArrayList<IComponentIdentifier> listado = new ArrayList<IComponentIdentifier>();
		
	    // Codigo para conseguir a los agentes que necesiten ayuda
		for (IComponentIdentifier a : agents) {

			IFuture<IGetInformationService> persona = getAgent().getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IGetInformationService.class, a);

			persona.addResultListener(new IResultListener<IGetInformationService>() {

				@Override
				public void resultAvailable(IGetInformationService result){
					if(result.getSalud()<70){
						listado.add(a);
					}

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});

		}


		return listado;
	}

	@Override
	public void setEstadisticasEdifice(EdificePojo info) {

	}


	

}
