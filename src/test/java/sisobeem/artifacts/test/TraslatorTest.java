package sisobeem.artifacts.test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import sisobeem.artifacts.Bounds;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Ubicacion;
import sisobeem.utilities.Traslator;


public class TraslatorTest {
  
	Traslator ob; 
	
    public TraslatorTest() {
    	
    	Bounds b = new Bounds();
    	b.setNorth(6.960997099008478);
    	b.setSouth(6.998);
    	b.setWest(168.763111375);
    	b.setEast(168.733111375);
    	
        Traslator.getTraslator().setDatosIniciales(b,0.00001);
        ob = Traslator.getTraslator();
	}
	
	
	@Test
	public void getTamañoTest() {
		Coordenada tamaño = ob.getTamaño();
		
		//System.out.println(tamaño.getX());

		//System.out.println(tamaño.getY());
		
		assertEquals(3000, tamaño.getX());
	    assertEquals(3700, tamaño.getY());
		

	}
	
	/**
	@Test
	public void getCoordenadaTest(){
		Ubicacion u = new Ubicacion();
		u.setLat(this.ob.getNorte());
		u.setLng(this.ob.getEste());
		assertEquals(33536040, ob.getCoordenada(u).getX());
	}
    */
	
	@Test
	public void getUbicacion(){
		Coordenada c = new Coordenada(100,0);
		
		Ubicacion ubi = ob.getUbicacion(c);
		
		System.out.println(Double.toString(ubi.getLng()));
		System.out.println(Double.toString(ubi.getLat()));
		System.out.println("-166.59729687499998");
		
		
		Coordenada nueva = ob.getCoordenada(new Ubicacion(6.998, 168.764111376));
		System.out.println(nueva.getX());
		
		Ubicacion nuevaa= ob.getUbicacion(new Coordenada(99,0));
		
		System.out.println(nuevaa.getLng());
		
		
		
		
		assertEquals(nueva.getX(), 100);
	}
	
	  
}