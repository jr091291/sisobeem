package sisobeem.artifacts.test;

import static org.junit.Assert.*;

import org.junit.Test;

import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Traslator;


public class TraslatorTest {
  
	Traslator ob  = new Traslator(6.960997099008478, -6.041245203091869, 168.763111375,-166.59729687499998,0.00001);
	@Test
	public void getTamañoTest() {
		Coordenada tamaño = ob.getTamaño();
		assertEquals(33536040, tamaño.getX());
		assertEquals(1300224, tamaño.getY());
	}

}