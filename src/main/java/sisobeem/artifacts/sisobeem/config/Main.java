package sisobeem.artifacts.sisobeem.config;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class Main {

	public static void main(String[] args)  {
		String config = "[{\"lugar\":{\"south\":-35.52391179765372,\"west\":149.08041112168883,\"north\":-35.523342038436404,\"east\":149.08154837831114},\"momento\":\"dia\",\"duracion\":20,\"intencidad\":1,\"duracionSismo\":10},[{\"personas\":1,\"ubicacion\":{\"lat\":-35.523511317207216,\"lng\":149.0812151134014},\"pisos\":1,\"id\":0},{\"personas\":1,\"ubicacion\":{\"lat\":-35.523550610993325,\"lng\":149.08100858330727},\"pisos\":1,\"id\":1}],{\"transeuntes\":1,\"edad\":\"22,60\",\"conocimiento\":\"20,60\"},{\"tiempoRespuesta\":2,\"personalBusquedaRescate\":3,\"personalSalud\":3,\"PersonalSeguridad\":2}]";
		Gson gson = new Gson();
		JsonParser parser = new JsonParser();
	    JsonArray array = parser.parse(config).getAsJsonArray();
	    SimulationConfig sim = gson.fromJson(array.get(0), SimulationConfig.class);
	    EdificesConfig[] edi = gson.fromJson(array.get(1), EdificesConfig[].class );
	    PersonsConfig per = gson.fromJson(array.get(2), PersonsConfig.class);
	    EmergencyConfig eme = gson.fromJson(array.get(3), EmergencyConfig.class );
	    Configuration con = new Configuration(
	    		per,
	    		sim,
	    		edi,
	    		eme
	    		);
	    System.out.println(con.getEdificesConfig()[0].getPersonas());
	    
	}
	
	

}
