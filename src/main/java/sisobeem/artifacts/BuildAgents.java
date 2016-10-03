package sisobeem.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import jadex.bridge.IComponentIdentifier;
import jadex.bridge.service.types.cms.CreationInfo;
import sisobeem.artifacts.abstractos.BuildAgentsAbstract;
import sisobeem.artifacts.sisobeem.config.Configuration;
import sisobeem.artifacts.sisobeem.config.EdificesConfig;
import sisobeem.artifacts.sisobeem.config.EmergencyConfig;
import sisobeem.artifacts.sisobeem.config.PersonsConfig;
import sisobeem.artifacts.sisobeem.config.SimulationConfig;

public class BuildAgents extends BuildAgentsAbstract {

    /**
     * Constructor
     * @param config
     */
	public BuildAgents(Configuration config) {
		super(config);
	}

	@Override
	public ArrayList<IComponentIdentifier> createTranseuntes(PersonsConfig personConfig) {
		
		ArrayList<CreationInfo> infos = new ArrayList<CreationInfo>();
		
		int[]porcentajesEdades = separador(personConfig.getEdad());
		int[] edades = Random.getEdadRandom(personConfig.getTranseuntes(),
				                            porcentajesEdades[0], 
				                            porcentajesEdades[1]-porcentajesEdades[0], 
				                            100-porcentajesEdades[1]);
		
		int[] porcentajesConocimiento = separador(personConfig.getConocimiento());
		double[]conocimiento = Random.getConocimientoRandom(personConfig.getTranseuntes(),
				                                            porcentajesConocimiento[0],
				                                            porcentajesConocimiento[1]-porcentajesConocimiento[0],
				                                            100-porcentajesConocimiento[1]);
		
		int[]salud = Random.getSaludRamdon(personConfig.getTranseuntes(), personConfig.getSalud());
		double[]liderazgos = Random.getLiderazgoRamdon(personConfig.getTranseuntes(), personConfig.getLiderazgo());
	    
		for (int i = 0; i < personConfig.getTranseuntes(); i++) {
			// Creacion del mapa de argumentos
			Map<String, Object> arguments = new HashMap<String, Object>();
			arguments.put("edad", edades[i]);
			arguments.put("conocimientoZona", conocimiento[i]);
			arguments.put("salud", salud[i]);
			arguments.put("liderazgo", liderazgos[i]);
		    //Asignacion de los Argumentos en la estructura de informacion
			CreationInfo info = new CreationInfo(arguments);
			infos.add(info);
		}	
		
		return (ArrayList<IComponentIdentifier>) super.getPlataforma().BuildComponents("sisobeem.agent.person/CivilAgentBDI.class", infos).clone();

	}
	
	public ArrayList<IComponentIdentifier> createTranseuntes(PersonsConfig personConfig, int cantidad) {
	    
		ArrayList<CreationInfo> infos = new ArrayList<CreationInfo>();
		int[]porcentajesEdades = separador(personConfig.getEdad());
		int[] edades = Random.getEdadRandom(cantidad,porcentajesEdades[0],
				                            porcentajesEdades[1]-porcentajesEdades[0],
				                            100-porcentajesEdades[1]);
		
		int[] porcentajesConocimiento = separador(personConfig.getConocimiento());
		double[]conocimiento = Random.getConocimientoRandom(cantidad, porcentajesConocimiento[0],
				                                            porcentajesConocimiento[1]-porcentajesConocimiento[0],
				                                            100-porcentajesConocimiento[1]);
		
		int[]salud = Random.getSaludRamdon(cantidad, personConfig.getSalud());
		double[]liderazgos = Random.getLiderazgoRamdon(cantidad, personConfig.getLiderazgo());
	    
		for (int i = 0; i < cantidad; i++) {
			// Creacion del mapa de argumentos
			Map<String, Object> arguments = new HashMap<String, Object>();
			arguments.put("edad", edades[i]);
			arguments.put("conocimientoZona", conocimiento[i]);
			arguments.put("salud", salud[i]);
			arguments.put("liderazgo", liderazgos[i]);
		    
			CreationInfo info = new CreationInfo(arguments);
			infos.add(info);
		}		
		return (ArrayList<IComponentIdentifier>) super.getPlataforma().BuildComponents("sisobeem.agent.person/CivilAgentBDI.class", infos).clone();
	}

	@Override
	public ArrayList<IComponentIdentifier> createEdifices(EdificesConfig[] edificesConfigs) {
		
		ArrayList<CreationInfo> infos = new ArrayList<CreationInfo>();
		
		for (EdificesConfig edificesConfig : edificesConfigs) {
			
			Map<String, Object> arguments = new HashMap<String, Object>();	//Mapa de Argumentos
			//Guardado de los argmentos y adesion a la Lista
			arguments.put("salidas", edificesConfig.getSalidas());
			arguments.put("resistencia", edificesConfig.getResistencia());
			arguments.put("ubicacion", edificesConfig.getUbicacion());
		
			ArrayList<IComponentIdentifier> personas = createTranseuntes(super.getConfig().getPersonsConfig(),  edificesConfig.getPersonas());
			
			arguments.put("cidsPlants", createPlants(personas, edificesConfig.getPisos()));
			
			CreationInfo info = new CreationInfo(arguments);
			infos.add(info);		   
		}
		
		return  super.getPlataforma().BuildComponents("sisobeem.agent.enviroment/EdificeAgentBDI.class", infos);
	}
	
	/**
	 * Método para la creacion de pisos en un edificio.
	 * @param personas Las personas que se seran asignadas aleatoriamente en los pisos creados
	 * @param numPisos Numero de pisos de la edificacion
	 * @return
	 */
	public ArrayList<IComponentIdentifier> createPlants(ArrayList<IComponentIdentifier> personas, int numPisos){
		
		ArrayList<CreationInfo> infos = new ArrayList<CreationInfo>();
	
		for (int i = 0; i < numPisos; i++) {
		
			Map<String, Object> arguments = new HashMap<String, Object>();	//Mapa de Argumentos			
			ArrayList<IComponentIdentifier> personPlants = new ArrayList<IComponentIdentifier>(); //Personas para el piso
			
			/**
			 * Eleccion personas para el piso
			 */
			if(i!=numPisos-1){
				int random = Random.getIntRandom(0, personas.size());
			    for (int j = 0; j < random; j++) {
			    	if(!personas.isEmpty()){
			    		personPlants.add(personas.get(personas.size()-1));
			    		personas.remove(personas.size()-1);
			    	}
				}
			}else{
				personPlants = (ArrayList<IComponentIdentifier>) personas.clone();	
			}
			
			//Guardado de los argmentos y adesion a la Lista
			arguments.put("cidsPerson", personPlants );
			CreationInfo info = new CreationInfo(arguments);
			infos.add(info);
		}
		
		return  super.getPlataforma().BuildComponents("sisobeem.agent.enviroment/PlantAgentBDI.class", infos);

	}

	@Override
	public ArrayList<IComponentIdentifier> createAtencionEmergency(EmergencyConfig emergencyConfig) {

		return new ArrayList<IComponentIdentifier>();
	}
	
	@Override
	public void createZone(SimulationConfig simulacionConfig, ArrayList<IComponentIdentifier> transeuntes,
		 	               EdificesConfig[] edificesConfig, ArrayList<IComponentIdentifier> edifices,
			               ArrayList<IComponentIdentifier> emergency) {
		
		Map<String, Object> arguments = new HashMap<String, Object>();	//Mapa de Argumentos
		//Guardado de los argmentos y adesion a la Lista
		arguments.put("cidsPerson", transeuntes );
		arguments.put("cidsEdifices", edifices );
		arguments.put("simulacionConfig", simulacionConfig);
		arguments.put("edificesConfig", edificesConfig);
		Coordenada c =  super.getTraductor().getTamaño();
		arguments.put("x",c.getX());
		arguments.put("y",c.getY());
		CreationInfo info = new CreationInfo(arguments);
		
		super.getPlataforma().BuildComponent("Zone", "sisobeem.agent.enviroment/ZoneAgentBDI.class", info);
		
	}


	/**
	 * Metodo para separar  int separados por "," escritos en un String
	 * @param datos
	 * @return int[][]
	 */
	public int[] separador(String datos){
		String[] porcentajes = datos.split(",");
		int[]porcen = new int[porcentajes.length];
		
		for (int i = 0; i < porcentajes.length; i++) {		
			porcen[i]=Integer.parseInt(porcentajes[i]);
		}
		
		return porcen;
	}



}
