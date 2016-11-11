package sisobeem.artifacts.test.sisobeem.config;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import sisobeem.artifacts.Bounds;

public class BoundsTest {
	@Test
	public void getSet() {
		Bounds bounds=  new Bounds();
		bounds.setSouth(1.3);
		bounds.setWest(2.2);
		bounds.setEast(3.7);
		bounds.setNorth(6.5);
		assertEquals(1.3, bounds.getSouth() , 0);
		assertEquals(2.2, bounds.getWest() , 0);
		assertEquals(3.7, bounds.getEast() , 0);
		assertEquals(6.5, bounds.getNorth() , 0);
	}

}
