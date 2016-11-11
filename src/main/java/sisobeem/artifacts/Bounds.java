package sisobeem.artifacts;

public class Bounds {
	private double south;
	private double west;
	private double east;
	private double north;
	
	public Bounds(){};

	public Bounds(double east, double north, double south, double west) {
		this.south = south;
		this.west = west;
		this.east = east;
		this.north = north;
	}

	public double getSouth() {
		return south;
	}

	public void setSouth(double south) {
		this.south = south;
	}

	public double getWest() {
		return west;
	}

	public void setWest(double west) {
		this.west = west;
	}

	public double getEast() {
		return east;
	}

	public void setEast(double east) {
		this.east = east;
	}

	public double getNorth() {
		return north;
	}

	public void setNorth(double north) {
		this.north = north;
	}
}
