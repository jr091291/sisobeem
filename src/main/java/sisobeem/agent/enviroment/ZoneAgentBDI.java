package sisobeem.agent.enviroment;

import static sisobeem.artifacts.Log.getLog;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.xalan.xsltc.compiler.sym;

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
import sisobeem.artifacts.print.EmergencyAction;
import sisobeem.artifacts.print.FinEstadisticaAction;
import sisobeem.artifacts.print.MoveAction;
import sisobeem.artifacts.print.PersonAction;
import sisobeem.artifacts.print.PuntoAction;
import sisobeem.artifacts.print.RouteAction;
import sisobeem.artifacts.print.SismoAction;
import sisobeem.artifacts.print.pojo.EdificePojo;
import sisobeem.artifacts.print.pojo.EmergencyPojo;
import sisobeem.artifacts.print.pojo.FinEstadisticaPojo;
import sisobeem.artifacts.print.pojo.MovePojo;
import sisobeem.artifacts.print.pojo.PersonPojo;
import sisobeem.artifacts.print.pojo.PuntoPojo;
import sisobeem.artifacts.print.pojo.RoutePojo;
import sisobeem.artifacts.print.pojo.SismoPojo;
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
import sisobeem.services.personServices.IsetDerrumbeService;
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
	ArrayList<Coordenada> sitiosSeguros;

	ArrayList<Coordenada> sitiosInseguros;
	Boolean inicioSismo;

	Boolean finSismo;

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
	private Mensajero m1, m2, m3, m4,m5,m6;
	private zoneClientEndpoint clientEndPoint, clientEndPoint2, clientEndPoint3, clientEndPoint4, clientEndPoint5,
			clientEndPoint6, clientEndPoint7, clientEndPoint8, clientEndPoint9, clientEndPoint10, clientEndPoint11,
			clientEndPoint12, clientEndPoint13, clientEndPoint14, clientEndPoint15, clientEndPoint16, clientEndPoint17,
			clientEndPoint18, clientEndPoint19, clientEndPoint20, clientEndPoint21, clientEndPoint22, clientEndPoint23,
			clientEndPoint24, clientEndPoint25, clientEndPoint26, clientEndPoint27, clientEndPoint28, clientEndPoint29,
			clientEndPoint30, clientEndPoint31, clientEndPoint32, clientEndPoint33, clientEndPoint34, clientEndPoint35,
			clientEndPoint36, clientEndPoint37, clientEndPoint38, clientEndPoint39, clientEndPoint40,
			clientEndPonintZONE, clientEndPoint41, clientEndPoint42, clientEndPoint43, clientEndPoint44,
			clientEndPoint45, clientEndPoint46, clientEndPoint47, clientEndPoint48, clientEndPoint49, clientEndPoint50,
			clientEndPoint51, clientEndPoint52, clientEndPoint53, clientEndPoint54, clientEndPoint55, clientEndPoint56,
			clientEndPoint57, clientEndPoint58, clientEndPoint59, clientEndPoint60;

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

	private ArrayList<IComponentIdentifier> AgentsEmergency;

	ArrayList<String> listaDeEmisores;

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

		sitiosInseguros = new ArrayList<Coordenada>();
		// sitiosSeguros = new ArrayList<>();
		// sitiosSeguros.add(new Coordenada(200,100));
		// sitiosSeguros.add(new Coordenada(100,50));

		this.contMsgAyuda = new ArrayList<IComponentIdentifier>();
		this.contMsgDeCalma = new ArrayList<IComponentIdentifier>();
		this.contMsgDeConfianza = new ArrayList<IComponentIdentifier>();
		this.contMsgFrsutracion = new ArrayList<IComponentIdentifier>();
		this.contMsgHostilidad = new ArrayList<IComponentIdentifier>();
		this.contMsgPanico = new ArrayList<IComponentIdentifier>();
		this.contMsgPrimerosAux = new ArrayList<IComponentIdentifier>();
		this.contMsgResguardo = new ArrayList<IComponentIdentifier>();
		this.contMsgMotivacion = new ArrayList<IComponentIdentifier>();

		this.inicioSismo = false;
		this.finSismo = false;

		getLog().setInfo("Agente " + agent.getComponentIdentifier().getLocalName() + " creado");

		this.cidsEdifices = new ArrayList<IComponentIdentifier>();

		// Accedemos a los argumentos del agente
		this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();

		AgentsEmergency = new ArrayList<IComponentIdentifier>();
		listaDeEmisores = new ArrayList<String>();

		AgentsEmergency.addAll((Collection<? extends IComponentIdentifier>) arguments.get("cidsSalud"));
		AgentsEmergency.addAll((Collection<? extends IComponentIdentifier>) arguments.get("cidsBusqueda"));
		AgentsEmergency.addAll((Collection<? extends IComponentIdentifier>) arguments.get("cidsSeguridad"));

		for (IComponentIdentifier a : AgentsEmergency) {
			listaDeEmisores.add(a.getLocalName());
		}

		// Obtenemos los cid de las personas en la Zona
		cidsPerson = (ArrayList<IComponentIdentifier>) arguments.get("cidsPerson");

		for (IComponentIdentifier a : cidsPerson) {
			listaDeEmisores.add(a.getLocalName());
		}

		for (int p = 0; p < 20; p++) {
			listaDeEmisores.add("Zone" + contador);
			contador++;
		}

		listaDeEmisores.add(getAgent().getComponentIdentifier().getLocalName());

		contador = 0;
		// Obtenemos los cid de las edificaciones
		cidsEdifices = (ArrayList<IComponentIdentifier>) arguments.get("cidsEdifices");

		// Inicializamos el listado de agentes muertos
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
		//System.out.println("ENVIANDO FINAL DE ESTADISTICAS");

		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(4000).get();
		contextSendMsg = true;
		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(10000).get();

		// Cronometro crono = new Cronometro();
		// crono.iniciarCronometro();

		// Iniciamos el sismo
		// System.err.println("INICIANDO SISMO");
		// System.out.println("INICIANDO SISMO");
		this.iniciarSismo();
		// Iniciamos mecanismos de atencion de emergencias
		agent.getComponentFeature(IExecutionFeature.class)
				.waitForDelay((this.simulacionConfig.getDuracionSismo() * 1000)).get();
		this.startCoordinadorEmergencia();

		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(this.simulacionConfig.getDuracion()*1000).get();
		getEstadisticas();
		
		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(10000).get();
		
		if (contador > 19) {
			contador = 0;
		}

		System.out.println("ENVIANDO FINAL DE ESTADISTICAS");
		FinEstadisticaAction info = new FinEstadisticaAction("FinalEstadistica", new FinEstadisticaPojo());
		bandejaMsg.put("Zone" + contador, info.toJson());
		contador++;

	}

	private void getEstadisticas() {

		System.out.println("Consultando estadisticas");
		for (IComponentIdentifier cid : cidsEdifices) {
			IFuture<IGetEstadisticasService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IGetEstadisticasService.class, cid);

			persona.addResultListener(new IResultListener<IGetEstadisticasService>() {

				@Override
				public void resultAvailable(IGetEstadisticasService result) {
					EdificePojo d = result.getEstiditicas();

					System.out.println("Estadisticas del edificio: " + d.getIdAgent());
					System.out.println("Derrumbado : " + d.getDerrumbado());
					System.out.println("Msj Ayuda : " + d.getMsgAyuda());
					System.out.println("Msj Calma : " + d.getMsgDeCalma());
					System.out.println("Msj Confianza : " + d.getMsgDeConfianza());
					System.out.println("Msj Frsutracion : " + d.getMsgFrsutracion());
					System.out.println("Msj Hostilidad : " + d.getMsgHostilidad());
					System.out.println("Msj Motivacion : " + d.getMsgMotivacion());
					System.out.println("Msj Panico : " + d.getMsgPanico());
					System.out.println("Msj Primeros Aux : " + d.getMsgPrimerosAux());
					System.out.println("Msj Resguardo : " + d.getMsgResguardo());

					System.out.println("Atrapados : " + d.getPersonasAtrapadas());
					System.out.println("Muertes totales : " + d.getPersonasMuertas());
					System.out.println("Suicidios : " + d.getSuicidios());

					if (contador > 19) {
						contador = 0;
					}

					EdificeAction info = new EdificeAction("estadistica", d);
					bandejaMsg.put("Zone" + contador, info.toJson());
					contador++;
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});

		}

		EdificePojo datos = new EdificePojo(getAgent().getComponentIdentifier().getLocalName(),
				Traslator.getTraslator().getUbicacion(new Coordenada(0, 0)), "estadisticas");

		// datos.setPersonasMuertas(this.cidPersonDead.size());

		datos.setMsgAyuda(this.contMsgAyuda.size());
		datos.setMsgDeCalma(this.contMsgDeCalma.size());
		datos.setMsgDeConfianza(this.contMsgDeConfianza.size());
		datos.setMsgFrsutracion(this.contMsgFrsutracion.size());
		datos.setMsgHostilidad(this.contMsgHostilidad.size());
		datos.setMsgMotivacion(this.contMsgMotivacion.size());
		datos.setMsgPanico(this.contMsgPanico.size());
		datos.setMsgPrimerosAux(this.contMsgPrimerosAux.size());
		datos.setMsgResguardo(this.contMsgResguardo.size());

		if (contador > 19) {
			contador = 0;
		}
		EdificeAction info = new EdificeAction("estadistica", datos);
		bandejaMsg.put("Zone" + contador, info.toJson());
		contador++;

		System.out.println("********************************************************************");
		System.out.println("Estadisticas del edificio: " + agent.getComponentIdentifier().getLocalName());
		// System.out.println("Derrumbado :
		// "+result.getEstiditicas().getDerrumbado());
		System.out.println("Msj Ayuda : " + this.contMsgAyuda.size());
		System.out.println("Msj Calma : " + this.contMsgDeCalma.size());
		System.out.println("Msj Confianza : " + this.contMsgDeConfianza.size());
		System.out.println("Msj Frsutracion : " + this.contMsgFrsutracion.size());
		System.out.println("Msj Hostilidad : " + this.contMsgHostilidad.size());
		System.out.println("Msj Motivacion : " + this.contMsgMotivacion.size());
		System.out.println("Msj Panico : " + this.contMsgPanico.size());
		System.out.println("Msj Primeros Aux : " + this.contMsgPrimerosAux.size());
		System.out.println("Msj Resguardo : " + this.contMsgResguardo.size());
		System.out.println("Agentes muertos : " + this.cidPersonDead.size());

	}

	/**
	 * Metodo que instancia los 4 mensajeros establecidos
	 */
	public void createMensajeros() {

		// System.out.println(listaDeEmisores);
		m1 = new Mensajero(0, (this.listaDeEmisores.size() / 6) - 1, this.listaDeEmisores, bandejaMsg, clientEndPoint,
				clientEndPoint2, clientEndPoint3, clientEndPoint4, clientEndPoint5, clientEndPoint6, clientEndPoint7,
				clientEndPoint8, clientEndPoint9, clientEndPoint10, 1);

		m2 = new Mensajero(this.listaDeEmisores.size() / 6, ((this.listaDeEmisores.size())* 1/3) - 1, this.listaDeEmisores,
				bandejaMsg, clientEndPoint11, clientEndPoint12, clientEndPoint13, clientEndPoint14, clientEndPoint15,
				clientEndPoint16, clientEndPoint17, clientEndPoint18, clientEndPoint19, clientEndPoint20, 2);

		m3 = new Mensajero(((this.listaDeEmisores.size())* 1/3), (this.listaDeEmisores.size() * 1 / 2) - 1,
				this.listaDeEmisores, bandejaMsg, clientEndPoint21, clientEndPoint22, clientEndPoint23,
				clientEndPoint24, clientEndPoint25, clientEndPoint26, clientEndPoint27, clientEndPoint28,
				clientEndPoint29, clientEndPoint30, 3);

		m4 = new Mensajero((this.listaDeEmisores.size() * 1 / 2), ((this.listaDeEmisores.size())* 4/6) - 1, this.listaDeEmisores,
				bandejaMsg, clientEndPoint31, clientEndPoint32, clientEndPoint33, clientEndPoint34, clientEndPoint35,
				clientEndPoint36, clientEndPoint37, clientEndPoint38, clientEndPoint39, clientEndPoint40, 4);
		
		m5 = new Mensajero((this.listaDeEmisores.size())* 4/6, (this.listaDeEmisores.size())* 5/6 - 1, this.listaDeEmisores,
				bandejaMsg, clientEndPoint41, clientEndPoint42, clientEndPoint43, clientEndPoint44, clientEndPoint45,
				clientEndPoint46, clientEndPoint47, clientEndPoint48, clientEndPoint49, clientEndPoint50, 5);
		
		m6 = new Mensajero((this.listaDeEmisores.size())* 5/6, this.listaDeEmisores.size() - 1, this.listaDeEmisores,
				bandejaMsg, clientEndPoint51, clientEndPoint52, clientEndPoint53, clientEndPoint54, clientEndPoint55,
				clientEndPoint56, clientEndPoint57, clientEndPoint58, clientEndPoint59, clientEndPoint60, 6);

		// repartidor = new
		// RepartidorDeRutas(this.getAgent(),this.rutasDisponibles,this.cidsPerson,this.coordinador);

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
			m5.iniciar();
			m6.iniciar();
			// repartidor.iniciar();
		} else {
			m1.parar();
			m2.parar();
			m3.parar();
			m4.parar();
			m5.parar();
			m6.parar();
			// repartidor.parar();
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
			clientEndPoint41 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex41"));
			clientEndPoint42 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex42"));
			clientEndPoint43 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex43"));
			clientEndPoint44 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex44"));
			clientEndPoint45 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex45"));
			clientEndPoint46 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex46"));
			clientEndPoint47 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex47"));
			clientEndPoint48 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex48"));
			clientEndPoint49 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex49"));
			clientEndPoint50 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex50"));
			clientEndPoint51 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex51"));
			clientEndPoint52 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex52"));
			clientEndPoint53 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex53"));
			clientEndPoint54 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex54"));
			clientEndPoint55 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex55"));
			clientEndPoint56 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex56"));
			clientEndPoint57 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex57"));
			clientEndPoint58 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex58"));
			clientEndPoint59 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex59"));
			clientEndPoint60 = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadex60"));
			clientEndPonintZONE = new zoneClientEndpoint(new URI("ws://localhost:8080/sisobeem/simulacion/jadexZONE"));
			// add listener
			clientEndPonintZONE.addMessageHandler(new zoneClientEndpoint.MessageHandler() {
				public void handleMessage(String message) {

					/*
					 * Gson gson = new Gson(); JsonParser parser = new
					 * JsonParser(); JsonArray array =
					 * parser.parse(message).getAsJsonArray();
					 * IComponentIdentifier sim = gson.fromJson(array.get(0),
					 * IComponentIdentifier.class); Ubicacion[] edi =
					 * gson.fromJson(array.get(1), Ubicacion[].class );
					 * 
					 * 
					 * //System.out.println(message); //
					 * System.out.println("Ruta agregada!"); Gson gson = new
					 * Gson(); RoutePojo route = gson.fromJson(message,
					 * RoutePojo.class); //rutasDisponibles.add(route);
					 * 
					 * Ubicacion[] rutaU= route.getCoordenadas();
					 * 
					 * Coordenada[] ruta = new Coordenada[rutaU.length];
					 * 
					 * for (int j = 0; j <rutaU.length; j++) { ruta[j]=
					 * Traslator.traductor.getCoordenada(rutaU[j]); }
					 * 
					 * 
					 * 
					 * IComponentIdentifier ag = getAgent(route.getAgent());
					 * if(ag!=null){ //
					 * System.out.println("Existe este agente!");
					 * IFuture<ISetBeliefPersonService> zoneService = getAgent()
					 * .getComponentFeature(IRequiredServicesFeature.class)
					 * .searchService(ISetBeliefPersonService.class, ag);
					 * 
					 * zoneService.addResultListener(new
					 * IResultListener<ISetBeliefPersonService>() {
					 * 
					 * @Override public void
					 * resultAvailable(ISetBeliefPersonService result) {
					 * //System.out.println("Ruta enviada a :"+ag.getLocalName()
					 * ); result.setRute(rutaU); }
					 * 
					 * @Override public void exceptionOccurred(Exception
					 * exception) { getLog().setError(exception.getMessage()); }
					 * 
					 * }); }
					 */
				}
			});

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para conseguir el Identificador de un agente por su nombre
	 * 
	 * @param name
	 * @return
	 */
	public IComponentIdentifier getAgent(String name) {

		for (IComponentIdentifier p : this.cidsPerson) {
			if (p.getLocalName().equals(name)) {
				return p;
			}
		}

		if (this.coordinador.getLocalName().equals(name)) {
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
			sitiosInseguros.add(c);
			this.mapa[c.getX()][c.getY()].getAgents().add(cidsEdifices.get(i));
		}

		/**
		 * Asignacion coordenadas a transeutes de manera aleatoria
		 */
		for (int i = 0; i < cidsPerson.size(); i++) {
			this.mapa[Random.getIntRandom(0, this.mapa.length - 1)][Random.getIntRandom(0, this.mapa[0].length - 1)]
					.getAgents().add(cidsPerson.get(i));
		}

		// this.mapa[5][5].getAgents().addAll(this.AgentsEmergency);

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
	public boolean changePosition(Coordenada nueva, IComponentIdentifier cid, String tipo) {

		// System.out.println("Cambiando posicion de:"+cid.getLocalName());
		Coordenada antigua;
		if (validateInMap(nueva)) {
			antigua = getCoordenada(cid);
			if (antigua != null) {

				try {
					synchronized (this.mapa) {
						this.mapa[antigua.getX()][antigua.getY()].getAgents().remove(cid);
						this.mapa[nueva.getX()][nueva.getY()].getAgents().add(cid);
					}
				} catch (Exception e) {
					System.err.println("POR FUERA DEL MAPA!");
				}

				// for (IComponentIdentifier c :
				// this.mapa[nueva.getX()][nueva.getY()].getAgents()) {
				// System.out.println(c.getLocalName());
				// }

				// contador++;

				//

				MoveAction info = new MoveAction("move",
						new MovePojo(cid.getLocalName(), Traslator.getTraslator().getUbicacion(nueva), tipo));
				this.bandejaMsg.put(cid.getLocalName(), info.toJson());
				// System.out.println(this.bandejaMsg.size());
				// sendMensaje();

				return true;
			} else {
				try {
					synchronized (this.mapa) {
						// this.mapa[antigua.getX()][antigua.getY()].getAgents().remove(cid);
						this.mapa[nueva.getX()][nueva.getY()].getAgents().add(cid);
					}
				} catch (Exception e) {
					System.err.println("POR FUERA DEL MAPA!");
				}

				MoveAction info = new MoveAction("move",
						new MovePojo(cid.getLocalName(), Traslator.getTraslator().getUbicacion(nueva), tipo));
				this.bandejaMsg.put(cid.getLocalName(), info.toJson());

				return true;

			}
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

		if (c != null) {
			if (c.getX() < this.mapa.length && c.getY() < this.mapa[1].length && c.getX() >= 0 && c.getY() >= 0) {
				return true;
			}

			return false;
		} else {
			System.err.println("COORDENADA NULL");
			return false;
		}

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
		// ArrayList<IComponentIdentifier> listado = new
		// ArrayList<IComponentIdentifier>();
		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				this.mapa[i][j].setDaño(Random.getDoubleRandom(1, intesidad));

			}
		}

		sendDañoToAgents(this.cidsPerson, intesidad);
	}

	public void sendDañoToAgents(ArrayList<IComponentIdentifier> agents, double intensidad) {

		for (IComponentIdentifier a : agents) {
			IFuture<IsetDerrumbeService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IsetDerrumbeService.class, a);

			persona.addResultListener(new IResultListener<IsetDerrumbeService>() {

				@Override
				public void resultAvailable(IsetDerrumbeService result) {

					result.recibirDerumbe((int) intensidad / 100);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					getLog().setFatal(exception.getMessage());
				}

			});
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

		if (inicioSismo == false && finSismo == false) {
			inicioSismo = true;

			SismoAction info = new SismoAction("mensaje", new SismoPojo(true));

			System.err.println(
					getAgent().getComponentIdentifier().getLocalName() + ": Enviado mensaje de inicio de sismo");
			this.bandejaMsg.put(getAgent().getComponentIdentifier().getLocalName(), info.toJson());

		} else {

			// this.bandejaMsg.put(getAgent().getComponentIdentifier().getLocalName(),
			// null);
			if (this.intensidadSismo < 0) {
				// Enviamos la accion a la vista

				SismoAction info = new SismoAction("mensaje", new SismoPojo(false));
				this.bandejaMsg.put(getAgent().getComponentIdentifier().getLocalName(), info.toJson());
				// contador++;

				inicioSismo = false;
				finSismo = true;

				System.err.println(getAgent().getComponentIdentifier().getLocalName() + ": Sismo Terminado");

				// agent.getComponentFeature(IExecutionFeature.class)
				// .waitForDelay((this.simulacionConfig.getDuracionSismo() *
				// 1000)).get();

				// this.bandejaMsg.put(getAgent().getComponentIdentifier().getLocalName(),
				// "");

			} else {

				System.err.println("Intensidad:" + this.intensidadSismo);
				// this.bandejaMsg.put(getAgent().getComponentIdentifier().getLocalName(),
				// null);
				// SismoAction info = new SismoAction("m", new
				// SismoPojo(false));
				// this.bandejaMsg.put(getAgent().getComponentIdentifier().getLocalName(),
				// "");
			}

		}

	}

	/**
	 * Método que devuelve un listado con los agentes en el rango de escucha del
	 * emisor
	 * 
	 * @param emisor
	 * @return ArrayList<IComponentIdentifier> agents
	 */
	public ArrayList<IComponentIdentifier> getAgentsInMyRange(IComponentIdentifier emisor) {

		Coordenada agent = this.getCoordenada(emisor);

		ArrayList<IComponentIdentifier> list = new ArrayList<IComponentIdentifier>();
		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				if (((i >= agent.getX() - 5) && (i <= agent.getX() + 5))
						&& ((j >= agent.getY() - 5) && (j <= agent.getY() + 5))) {
					for (IComponentIdentifier a : this.mapa[i][j].getAgents()) {
						if (cidsPerson.contains(a) || AgentsEmergency.contains(a)) {
							list.add(a);
						}
					}

				}
			}
		}

		return list;

	}

	public ArrayList<IComponentIdentifier> getAgentsInMyRange(IComponentIdentifier emisor, int rango) {

		Coordenada agent = this.getCoordenada(emisor);

		ArrayList<IComponentIdentifier> list = new ArrayList<IComponentIdentifier>();
		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				if (((i >= agent.getX() - rango) && (i <= agent.getX() + rango))
						&& ((j >= agent.getY() - rango) && (j <= agent.getY() + rango))) {
					for (IComponentIdentifier a : this.mapa[i][j].getAgents()) {
						if (cidsPerson.contains(a)) {
							list.add(a);
						}
					}

				}
			}
		}

		return list;

	}

	/**
	 * Servicios de comunicacion de mensajes en rango de escucha
	 */
	@Override
	public void AyudaMsj(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);

		this.contMsgAyuda.add(emisor);
		// getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Ayuda
		// ");

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
					// getLog().setFatal(exception.getMessage());
				}

			});

		}
		PersonAction info = new PersonAction("mensaje", new PersonPojo(emisor.getLocalName(), "ayuda"));
		this.bandejaMsg.put(emisor.getLocalName(), info.toJson());

	}

	@Override
	public void PrimeroAuxMsj(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
		this.contMsgPrimerosAux.add(emisor);
		// getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de
		// PrimerosAux ");

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
					// getLog().setFatal(exception.getMessage());
				}

			});

		}

		PersonAction info = new PersonAction("mensaje", new PersonPojo(emisor.getLocalName(), "auxilios"));
		this.bandejaMsg.put(emisor.getLocalName(), info.toJson());

	}

	@Override
	public void CalmaMsj(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
		this.contMsgDeCalma.add(emisor);
		// getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Calma
		// ");

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
					// getLog().setFatal(exception.getMessage());
				}

			});

		}

		PersonAction info = new PersonAction("mensaje", new PersonPojo(emisor.getLocalName(), "calma"));
		this.bandejaMsg.put(emisor.getLocalName(), info.toJson());

	}

	@Override
	public void ConfianzaMsj(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
		this.contMsgDeConfianza.add(emisor);
		// getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de
		// Confianza ");

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
					// getLog().setFatal(exception.getMessage());
				}

			});

		}

		// Enviamos la accion a la vista
		PersonAction info = new PersonAction("mensaje", new PersonPojo(emisor.getLocalName(), "confianza"));
		this.bandejaMsg.put(emisor.getLocalName(), info.toJson());

	}

	@Override
	public void FrustracionMsj(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
		this.contMsgFrsutracion.add(emisor);
		// getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de
		// Frustracion ");

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
					// getLog().setFatal(exception.getMessage());
				}

			});

		}

		// Enviamos la accion a la vista

		PersonAction info = new PersonAction("mensaje", new PersonPojo(emisor.getLocalName(), "frustracion"));
		this.bandejaMsg.put(emisor.getLocalName(), info.toJson());

	}

	@Override
	public void HostilMsj(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
		this.contMsgHostilidad.add(emisor);
		// getLog().setInfo(emisor.getLocalName() + ": Envia mensaje Hostil");

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
					// getLog().setFatal(exception.getMessage());
				}

			});

		}

		// Enviamos la accion a la vista

		PersonAction info = new PersonAction("mensaje", new PersonPojo(emisor.getLocalName(), "hostil"));
		this.bandejaMsg.put(emisor.getLocalName(), info.toJson());

	}

	@Override
	public void PanicoMsj(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
		this.contMsgPanico.add(emisor);
		// getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de Panico
		// ");

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
					// getLog().setFatal(exception.getMessage());
				}

			});

		}

		// Enviamos la accion a la vista

		PersonAction info = new PersonAction("mensaje", new PersonPojo(emisor.getLocalName(), "panico"));
		this.bandejaMsg.put(emisor.getLocalName(), info.toJson());

	}

	@Override
	public void ResguardoMsj(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
		this.contMsgResguardo.add(emisor);
		// getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de
		// Resguardo ");

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
					// getLog().setFatal(exception.getMessage());
				}

			});

		}

		// Enviamos la accion a la vista

		PersonAction info = new PersonAction("mensaje", new PersonPojo(emisor.getLocalName(), "resguardo"));
		this.bandejaMsg.put(emisor.getLocalName(), info.toJson());

	}

	@Override
	public void Motivacion(IComponentIdentifier emisor) {

		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);
		this.contMsgMotivacion.add(emisor);
		// getLog().setInfo(emisor.getLocalName() + ": Envia mensaje de
		// Motivacion ");

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
					// getLog().setFatal(exception.getMessage());
				}

			});

		}

		// Enviamos la accion a la vista

		PersonAction info = new PersonAction("mensaje", new PersonPojo(emisor.getLocalName(), "motivacion"));
		this.bandejaMsg.put(emisor.getLocalName(), info.toJson());

	}

	@Override
	public void Team(IComponentIdentifier emisor, double liderazgo) {
		ArrayList<IComponentIdentifier> listado = this.getAgentsInMyRange(emisor);

		// getLog().setInfo(emisor.getLocalName() + ": armado team ");

		for (IComponentIdentifier receptor : listado) {

			IFuture<IReceptorMensajesService> persona = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(IReceptorMensajesService.class, receptor);

			persona.addResultListener(new IResultListener<IReceptorMensajesService>() {

				@Override
				public void resultAvailable(IReceptorMensajesService result) {

					result.Team(emisor, liderazgo);

				}

				@Override
				public void exceptionOccurred(Exception exception) {
					// getLog().setFatal(exception.getMessage());
				}

			});

		}

	}

	@Override
	public void derrumbarEdifice(IComponentIdentifier cidEdifice) {
		Coordenada edificio = this.getCoordenada(cidEdifice);

		for (int i = 0; i < this.mapa.length; i++) {
			for (int j = 0; j < this.mapa[i].length; j++) {
				if (((i >= edificio.getX() - 8) && (i <= edificio.getX() + 8))
						&& ((j >= edificio.getY() - 8) && (j <= edificio.getY() + 8))) {
					this.mapa[i][j].setDaño(9);
					ArrayList<IComponentIdentifier> listado = new ArrayList<IComponentIdentifier>();
					for (IComponentIdentifier a : this.mapa[i][j].getAgents()) {
						if (this.cidsPerson.contains(a)) {
							listado.add(a);
						}
					}

					// System.out.println(listado.size());
					sendDañoToAgents(listado, 9);
				}
			}
		}

		if (contador > 19) {
			contador = 0;
		}

		EdificeAction info = new EdificeAction("derrumbe", new EdificePojo(cidEdifice.getLocalName(),
				Traslator.getTraslator().getUbicacion(edificio), "derrumbe"));
		this.bandejaMsg.put("Zone" + contador, info.toJson());
		contador++;
	}

	@Override
	public void AdicionarPersonService(IComponentIdentifier person) {
		if (!this.cidsPerson.contains(person)) {
			getLog().setDebug("Nuevo agente recibido por el zone: " + person.getLocalName());
			this.cidsPerson.add(person);
		}
	}

	@Override
	public Coordenada[] getRuta(IComponentIdentifier agent, Coordenada inicio, Coordenada destino,
			double conocimientoDelaZona) {
		// Coordenada inicio = this.getCoordenada(agent);
		/*
		 * 
		 * RouteAction info = new RouteAction("route", new
		 * RoutePojo(Traslator.getTraslator().getUbicacion(inicio),
		 * Traslator.getTraslator().getUbicacion(destino),
		 * agent.getLocalName()));
		 * 
		 * this.bandejaMsg.put(agent.getLocalName(), info.toJson()); //
		 * Thread.sleep(300);
		 */

		Coordenada[] res = construirRuta(inicio, destino);
		return res;

	}

	private Coordenada[] construirRuta(Coordenada inicio, Coordenada fin) {
		Coordenada[] res;
		switch (Random.getIntRandom(1, 4)) {
		case 1:
			res = algoritmo1(inicio, fin);
			break;
		case 2:
			res = algoritmo2(inicio, fin);

			break;

		case 3:
			res = algoritmo3(inicio, fin);

			break;
		case 4:
			res = algoritmo1(inicio, fin);
			break;

		default:
			res = algoritmo1(inicio, fin);
			break;
		}

		return res;

	}

	private Coordenada[] algoritmo1(Coordenada inicio, Coordenada fin) {
		int xI = inicio.getX();
		int yI = inicio.getY();
		int xF = fin.getX();
		int yF = fin.getY();

		ArrayList<Coordenada> lista = new ArrayList<Coordenada>();

		while (xI != xF || yI != yF) {
			int xD = xI - xF;
			int yD = yI - yF;
			if (xD < 0) {
				xI = xI + 1;
			} else if (xD == 0) {

			} else {
				xI = xI - 1;
			}

			if (yD < 0) {
				yI = yI + 1;
			} else if (yD == 0) {

			} else {
				yI = yI - 1;
			}

			lista.add(new Coordenada(xI, yI));
		}

		Coordenada[] rute = new Coordenada[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			rute[i] = lista.get(i);
		}
		return rute;
	}

	private Coordenada[] algoritmo2(Coordenada inicio, Coordenada fin) {

		int xI = inicio.getX();
		int yI = inicio.getY();
		int xF = fin.getX();
		int yF = fin.getY();

		ArrayList<Coordenada> lista = new ArrayList<Coordenada>();

		while (xI != xF) {
			int xD = xI - xF;
			// int yD = yI - yF;
			if (xD < 0) {
				xI = xI + 1;
			} else if (xD == 0) {

			} else {
				xI = xI - 1;
			}

			lista.add(new Coordenada(xI, yI));
		}

		while (yI != yF) {
			// int xD = xI - xF;
			int yD = yI - yF;

			if (yD < 0) {
				yI = yI + 1;
			} else if (yD == 0) {

			} else {
				yI = yI - 1;
			}

			lista.add(new Coordenada(xI, yI));
		}

		Coordenada[] rute = new Coordenada[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			rute[i] = lista.get(i);
		}
		return rute;

	}

	private Coordenada[] algoritmo3(Coordenada inicio, Coordenada fin) {

		int xI = inicio.getX();
		int yI = inicio.getY();
		int xF = fin.getX();
		int yF = fin.getY();

		ArrayList<Coordenada> lista = new ArrayList<Coordenada>();

		while (yI != yF) {
			// int xD = xI - xF;
			int yD = yI - yF;

			if (yD < 0) {
				yI = yI + 1;
			} else if (yD == 0) {

			} else {
				yI = yI - 1;
			}

			lista.add(new Coordenada(xI, yI));
		}

		while (xI != xF) {
			int xD = xI - xF;
			// int yD = yI - yF;
			if (xD < 0) {
				xI = xI + 1;
			} else if (xD == 0) {

			} else {
				xI = xI - 1;
			}

			lista.add(new Coordenada(xI, yI));
		}

		Coordenada[] rute = new Coordenada[lista.size()];
		for (int i = 0; i < lista.size(); i++) {
			rute[i] = lista.get(i);
		}
		return rute;

	}

	@Override
	public Coordenada getDestiny(IComponentIdentifier agent) {
		if (sitiosSeguros == null) {

			sitiosSeguros = getPuntosSeguros();
		}

		return sitiosSeguros.get(Random.getIntRandom(0, sitiosSeguros.size() - 1));

	}

	@Override
	public ArrayList<Coordenada> getPuntosSeguros() {

		if (this.sitiosSeguros == null) {
			ArrayList<Coordenada> lista = new ArrayList<Coordenada>();

			for (int i = 0; i < this.mapa.length; i++) {
				for (int j = 0; j < this.mapa[i].length; j++) {
					if (this.mapa[i][j].getDaño() < 3) {
						lista.add(new Coordenada(i, j));
					}
				}
			}

			ArrayList<Coordenada> res = new ArrayList<Coordenada>();

			res.add(lista.get(Random.getIntRandom(0, lista.size() / 4)));
			res.add(lista.get(Random.getIntRandom(lista.size() / 4, lista.size() / 2)));
			res.add(lista.get(Random.getIntRandom(lista.size() / 2, lista.size() * (3 / 4))));
			res.add(lista.get(Random.getIntRandom(lista.size() * (3 / 4), lista.size() - 1)));

			// @SuppressWarnings("unchecked")

			// ArrayList<Coordenada> depurada = (ArrayList<Coordenada>)
			// reducirPuntos(lista).clone();

			sendPuntosSeguros(res);
			return res;
		} else {
			return sitiosSeguros;
		}

	}

	private void sendPuntosSeguros(ArrayList<Coordenada> puntosSeguros) {

		for (Coordenada coordenada : puntosSeguros) {

			if (contador > 19) {
				contador = 0;
			}

			System.out.println("Punto seguro:" + coordenada.getX() + " - " + coordenada.getY());
			PuntoAction info = new PuntoAction("punto",
					new PuntoPojo(Traslator.getTraslator().getUbicacion(coordenada), "seguro"));
			this.bandejaMsg.put("Zone" + contador, info.toJson());
			contador++;
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

		sitiosInseguros.addAll(depurada);
		sendPuntosInseguros(sitiosInseguros);

		// System.err.println("PUNTOS INSEGUROS ENCONTRADOS");
		return (ArrayList<Coordenada>) sitiosInseguros.clone();
	}

	private void sendPuntosInseguros(ArrayList<Coordenada> pInseguro) {

		for (Coordenada coordenada : pInseguro) {
			if (contador > 19) {
				contador = 0;
			}

			System.out.println("Punto Insseguro:" + coordenada.getX() + " - " + coordenada.getY());

			PuntoAction info = new PuntoAction("punto",
					new PuntoPojo(Traslator.getTraslator().getUbicacion(coordenada), "inseguro"));
			this.bandejaMsg.put("Zone" + contador, info.toJson());
			contador++;
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

		this.cidPersonDead.add(agent);

		PersonAction info = new PersonAction("muerte", new PersonPojo(agent.getLocalName(),
				Traslator.getTraslator().getUbicacion(this.getCoordenada(agent)), "muerte"));
		this.bandejaMsg.put(agent.getLocalName(), info.toJson());
		// contador++;

	}

	@Override
	public ArrayList<IComponentIdentifier> getPeopleHelp(IComponentIdentifier agent) {
		// TODO Auto-generated method stub

		ArrayList<IComponentIdentifier> agents = this.getAgentsInMyRange(agent, 20);
		// System.out.println("Generando Listado");
		ArrayList<IComponentIdentifier> listado = new ArrayList<IComponentIdentifier>();

		for (IComponentIdentifier age : agents) {
			if (this.contMsgAyuda.contains(age)) {
				listado.add(age);
			}
		}

		IFuture<ISetBeliefPersonService> persona = getAgent().getComponentFeature(IRequiredServicesFeature.class)
				.searchService(ISetBeliefPersonService.class, agent);

		persona.addResultListener(new IResultListener<ISetBeliefPersonService>() {

			@Override
			public void resultAvailable(ISetBeliefPersonService result) {

				result.setPersonHelp(ArrayListToArray(listado));

			}

			@Override
			public void exceptionOccurred(Exception exception) {
				// getLog().setFatal(exception.getMessage());
			}

		});

		return listado;
	}

	@Override
	public void setEstadisticasEdifice(EdificePojo info) {

	}

	@Override
	public void setResguardo(IComponentIdentifier agent) {

		PersonAction info = new PersonAction("mensaje", new PersonPojo(agent.getLocalName(), "resguardo"));
		this.bandejaMsg.put(agent.getLocalName(), info.toJson());

	}

	@Override
	public void setPersonaAyudada(IComponentIdentifier agent) {
		EmergencyAction info = new EmergencyAction("action", new EmergencyPojo(agent.getLocalName(), "ayuda"));
		this.bandejaMsg.put(agent.getLocalName(), info.toJson());

	}

	@Override
	public ArrayList<IComponentIdentifier> getAgentsHeridos(IComponentIdentifier agent) {
		ArrayList<IComponentIdentifier> agents = this.getAgentsInMyRange(agent,50);
		ArrayList<IComponentIdentifier> lista = new ArrayList<IComponentIdentifier>();
		for (IComponentIdentifier a : agents) {
			if (this.contMsgPrimerosAux.contains(a)) {
				lista.add(a);
			}
		}

		return lista;
	}

	@Override
	public Coordenada getTamañoMapa() {
		return new Coordenada(this.mapa.length, this.mapa[0].length);
	}

	@Override
	public void setEstadisticasEmergencia(EmergencyPojo info) {

		if (contador > 19) {
			contador = 0;
		}
		EmergencyAction a = new EmergencyAction("estadistica", info);
		this.bandejaMsg.put("Zone" + contador, a.toJson());
		contador++;

		if (info.getTipo().equals("salud")) {
			System.out.println("***************************");
			System.out.println(info.getTipo());
			System.out.println("Numero de pacientes" + ":" + info.getPacientes());
			System.out.println("Numero de personas atendidas" + ":" + info.getPersonasAtendidas());
		} else {
			System.out.println("***************************");
			System.out.println(info.getTipo());
			System.out.println("Numero de personas ayudadas" + ":" + info.getPersonasAyudadas());

		}

	}

	/**
	 * public void AddAgentsEmergency(ArrayList<IComponentIdentifier> agents) {
	 * 
	 * synchronized (this.mapa) { this.mapa[0][0].getAgents().addAll(agents); }
	 * 
	 * this.cidsPerson.addAll(agents);
	 * 
	 * 
	 * }
	 * 
	 **/

}
