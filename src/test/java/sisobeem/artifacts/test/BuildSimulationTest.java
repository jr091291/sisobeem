package sisobeem.artifacts.test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import jadex.bridge.IComponentIdentifier;
import sisobeem.agent.person.CivilAgentBDI;
import sisobeem.core.JadexPlatform;

public class BuildSimulationTest {
	static JadexPlatform simulation;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		simulation =  new JadexPlatform();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Test
	public void BuildComponent() {
		IComponentIdentifier ricardo = simulation.BuildComponent("ricardo", "sisobeem.agent.person/CivilBDI.class", null);
		assertEquals("Se creo un componente con el nombre ricardo", "ricardo",ricardo.getLocalName() );
		ArrayList<IComponentIdentifier> others = simulation.BuildComponents( CivilAgentBDI.class.getName()+ ".class", 10);
		assertEquals("Se crearon 10 componentes", 10, others.size());
	}
	
	@Test
	public void BuildComponent2() {
		ArrayList<IComponentIdentifier> others = simulation.BuildComponents( CivilAgentBDI.class.getName()+ ".class", 10);
		assertEquals("Se crearon 10 componentes", 10, others.size());
	}

	@Test
	public void BuildComponent3(){
		String[] names = {"lucas", "pedro", "juan"};
		String[] agentsNames =  new String[3];
		ArrayList<IComponentIdentifier> components = simulation.BuildComponents(CivilAgentBDI.class.getName()+".class", names);
		for(int i=0; i< components.size();i++){
			agentsNames[i] = components.get(i).getLocalName();
		}
		assertArrayEquals("componentes creados mediante un array de nombres", names, agentsNames);
	}

}
