package sisobeem.artifacts.test;

import org.junit.Test;

import sisobeem.artifacts.Bounds;
import sisobeem.artifacts.Ubicacion;
import sisobeem.core.BuildAgents;
import sisobeem.core.simulation.Configuration;
import sisobeem.core.simulation.EdificesConfig;
import sisobeem.core.simulation.PersonsConfig;
import sisobeem.core.simulation.SimulationConfig;

public class BuildAgentsTest {
	
	PersonsConfig personsConfig;
	EdificesConfig edficesConfig[] = new EdificesConfig[0];
	BuildAgents ob;
	Configuration config;
	SimulationConfig simulationConfig;
	
	
	public BuildAgentsTest(){
		personsConfig= new PersonsConfig();

		personsConfig.setConocimiento("30,70");
		personsConfig.setEdad("30,70");
		personsConfig.setLiderazgo(50);
		personsConfig.setSalud(50);
		personsConfig.setTranseuntes(0);
		
		Ubicacion u = new Ubicacion();
		u.setLat(6.952999096008478);
		u.setLng(168.736111375);
		
		//Cantidad de edificios
		for (int i = 0; i < 2; i++) {
		//	edficesConfig[i] = new EdificesConfig(0, 2, u, 10, 2, 4);
		}
		
	   	Bounds b = new Bounds();
    	b.setNorth(6.960997099008478);
    	b.setSouth(6.950997099008478);
    	b.setWest(168.763111375);
    	b.setEast(168.733111375);
    	
    	simulationConfig = new SimulationConfig();
    	simulationConfig.setDuracion(60);
    	simulationConfig.setDuracionSismo(20);
    	simulationConfig.setIntencidad(9);
    	simulationConfig.setLugar(b);
    	simulationConfig.setMomento("dia");
		
		
	    config = new Configuration();
		config.setPersonsConfig(personsConfig);
		config.setEdificesConfig(edficesConfig);
		config.setSimulationConfig(simulationConfig);
		
		
		 ob = new BuildAgents(config);
	}
	
	/**

	
	 * @Test
	public void testCreateTranseuntes() {			
			assertEquals(10,ob.createTranseuntes(config.getPersonsConfig()).size());
		
	}
	
	
	@Test
	public void testCreateTranseuntes2(){			
			assertEquals(5,ob.createTranseuntes(config.getPersonsConfig(),5).size());
	}
	
	
	@Test
	public void createPlants(){
		ArrayList<IComponentIdentifier> person = ob.createTranseuntes(config.getPersonsConfig(), 20);
		assertEquals(3, ob.createPlants((ArrayList<IComponentIdentifier>) person.clone(), 3).size());
	
	}

	@Test
	public void createEdifices(){
		assertEquals(4, ob.createEdifices(config.getEdificesConfig()).size());
	}
    
    **/
	@Test
	public void createZone() throws InterruptedException{
		ob.build();	
		
		Thread.sleep(100000);
	}
	
	
}
