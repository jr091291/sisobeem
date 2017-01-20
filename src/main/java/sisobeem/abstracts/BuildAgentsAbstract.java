package sisobeem.abstracts;

import java.util.ArrayList;
import jadex.bridge.IComponentIdentifier;
import sisobeem.core.JadexPlatform;
import sisobeem.core.simulation.Configuration;
import sisobeem.core.simulation.EdificesConfig;
import sisobeem.core.simulation.EmergencyConfig;
import sisobeem.core.simulation.PersonsConfig;
import sisobeem.core.simulation.SimulationConfig;
import sisobeem.utilities.Traslator;

/**
 * Define el comportamiento de construcción de los agentes
 * @author Erley
 *
 */
public abstract class BuildAgentsAbstract {
	
	private Configuration config;
	
	private final JadexPlatform plataforma;
	
	
	
	private Traslator traductor;
	
	
	/**
	 * Constructor que recibe como parametro la configuracion de la simulacion
	 * @param config
	 */
	public BuildAgentsAbstract(Configuration config){
		this.plataforma = new JadexPlatform();
		this.config = config;
		
		Traslator traductor = Traslator.getTraslator();
		traductor.setDatosIniciales(config.getSimulationConfig().getLugar(), 0.00001);
		this.setTraductor(traductor);
	}
    
	/**
	 * Método en el que se construyen las personas que están en la calle, en base a una configuracion
	 * @param personConfig Configuracion de los transeuntes
	 * @return ArrayList<IComponentIdentifier> cidTranseuntes
	 */
	public abstract ArrayList<IComponentIdentifier> createTranseuntes(PersonsConfig personConfig);
	
	
	/**
	 * Método que construye los agentes edificios en base a una configuracion
	 * @param edificesConfig  Configuracion de los edificions
	 * @return ArrayList<IComponentIdentifier> cidEdifices
	 */
	public abstract ArrayList<IComponentIdentifier> createEdifices(EdificesConfig[] edificesConfig);
	
	
	/**
	 * Metodo que construye los agentes para la atencion de la emergencia
	 * @param emergencyConfig Configuracion atencion de la emergencia
	 * @return ArrayList<IComponentIdentifiier> cidAtencionEmergencia
	 */
	public abstract ArrayList<IComponentIdentifier> createAtencionEmergency(EmergencyConfig emergencyConfig);
	
	
	/**
	 * Método para la creacion del agente principal (Ambiente general)
	 * @param simulacionConfig
	 * @param transeuntes
	 * @param edifices
	 * @param emergency
	 */
	public abstract void createZone(SimulationConfig simulacionConfig, ArrayList<IComponentIdentifier> transeuntes,EdificesConfig[] edificesConfig, ArrayList<IComponentIdentifier> edifices, ArrayList<IComponentIdentifier> emergency);
	
	
	/**
	 * Método principal para la creación de los agentes
	 */
	public void build(){
		createZone(config.getSimulationConfig(),
		          createTranseuntes(config.getPersonsConfig()),
		          config.getEdificesConfig(),
		         createEdifices(config.getEdificesConfig()),
		         createAtencionEmergency(config.getEmergencyConfig()));
	}

	public JadexPlatform getPlataforma() {
		return plataforma;
	}

	public Configuration getConfig() {
		return config;
	}

	public Traslator getTraductor() {
		return traductor;
	}

	public void setTraductor(Traslator traductor) {
		this.traductor = traductor;
	}

	
	
}
