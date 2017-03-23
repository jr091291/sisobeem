package sisobeem.artifacts.print;

import com.google.gson.Gson;

import sisobeem.abstracts.Action;
import sisobeem.artifacts.print.pojo.EmergencyPojo;
import sisobeem.artifacts.print.pojo.PersonPojo;

public class EmergencyAction extends Action <EmergencyPojo> {

	public EmergencyAction(String name, EmergencyPojo data) {
		super(name, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EmergencyPojo toClass(String json) {
		Gson gson = new Gson();
		EmergencyPojo move = gson.fromJson(json, EmergencyPojo.class);
		return move;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);  
	}

}
