package sisobeem.artifacts.test;

import static org.junit.Assert.*;

import org.apache.xalan.xsltc.compiler.sym;
import org.junit.Test;

import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Traslator;
import sisobeem.artifacts.sisobeem.config.Bounds;
import sisobeem.artifacts.sisobeem.config.Ubicacion;


public class TraslatorTest {
  
	Traslator ob; 
	
    public TraslatorTest() {
    	
    	Bounds b = new Bounds();
    	b.setNorth(6.960997099008478);
    	b.setSouth(6.960997099008478);
    	b.setWest(168.763111375);
    	b.setEast(168.733111375);
    	
        Traslator.getTraslator().setDatosIniciales(b,0.00001);
        ob = Traslator.getTraslator();
	}
	
	
	@Test
	public void getTamañoTest() {
		Coordenada tamaño = ob.getTamaño();
		assertEquals(33536040, tamaño.getX());
		assertEquals(1300224, tamaño.getY());
		

	}
	
	/**
	@Test
	public void getCoordenadaTest(){
		Ubicacion u = new Ubicacion();
		u.setLat(this.ob.getNorte());
		u.setLng(this.ob.getEste());
		assertEquals(33536040, ob.getCoordenada(u).getX());
	}
    
	
	@Test
	public void getUbicacion(){
		Coordenada c = new Coordenada(100,0);
		
		Ubicacion ubi = ob.getUbicacion(c);
		
		System.out.println(Double.toString(ubi.getLng()));
		System.out.println("-166.59729687499998");
		
		assertEquals("-166.59729687499998", Double.toString(ubi.getLng()));
	}
	
	*/
}