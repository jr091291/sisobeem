package sisobeem.artifacts.sisobeem.config;

public class Ubicacion {
	private double lat, lng;

	public Ubicacion() {}
	
	public Ubicacion(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}
	
}
