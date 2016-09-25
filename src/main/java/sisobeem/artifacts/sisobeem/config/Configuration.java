package sisobeem.artifacts.sisobeem.config;

public class Configuration{
	private PersonsConfig PersonsConfig;
	private SimulationConfig SimulationConfig;
    private EdificesConfig[] EdificesConfig;
    private EmergencyConfig EmergencyConfig;
    
    public Configuration() {
		
	}
    
	public Configuration(PersonsConfig personsConfig,	SimulationConfig simulationConfig,
			EdificesConfig[] edificesConfig, EmergencyConfig emergencyConfig) {
		PersonsConfig = personsConfig;
		SimulationConfig = simulationConfig;
		EdificesConfig = edificesConfig;
		EmergencyConfig = emergencyConfig;
	}

	public PersonsConfig getPersonsConfig ()
    {
        return PersonsConfig;
    }

    public void setPersonsConfig (PersonsConfig PersonsConfig)
    {
        this.PersonsConfig = PersonsConfig;
    }

    public SimulationConfig getSimulationConfig ()
    {
        return SimulationConfig;
    }

    public void setSimulationConfig (SimulationConfig SimulationConfig)
    {
        this.SimulationConfig = SimulationConfig;
    }

    public EdificesConfig[] getEdificesConfig ()
    {
        return EdificesConfig;
    }

    public void setEdificesConfig (EdificesConfig[] EdificesConfig)
    {
        this.EdificesConfig = EdificesConfig;
    }

    public EmergencyConfig getEmergencyConfig ()
    {
        return EmergencyConfig;
    }

    public void setEmergencyConfig (EmergencyConfig EmergencyConfig)
    {
        this.EmergencyConfig = EmergencyConfig;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PersonsConfig = "+PersonsConfig+", SimulationConfig = "+SimulationConfig+", EdificesConfig = "+EdificesConfig+", EmergencyConfig = "+EmergencyConfig+"]";
    }
}
