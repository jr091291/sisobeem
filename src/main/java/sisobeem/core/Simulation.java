package sisobeem.core;

import sisobeem.core.simulation.Configuration;

public class Simulation {
	private JadexPlatform platform;
	private Configuration config;

	public Simulation(JadexPlatform platform, Configuration config) {
		this.setConfig(config);
		this.setPlatform(platform);
	}

	public JadexPlatform getPlatform() {
		return platform;
	}

	public void setPlatform(JadexPlatform platform) {
		this.platform = platform;
	}

	public Configuration getConfig() {
		return config;
	}

	public void setConfig(Configuration config) {
		this.config = config;
	}
	
	public void BuildingEmergency(){
		 
	}
	

}
