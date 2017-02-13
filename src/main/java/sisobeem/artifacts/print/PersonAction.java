package sisobeem.artifacts.print;

import com.google.gson.Gson;

import sisobeem.abstracts.Action;
import sisobeem.artifacts.print.pojo.PersonPojo;

public class PersonAction extends Action <PersonPojo> {

	
	public PersonAction(String name, PersonPojo data) {
		super(name, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PersonPojo toClass(String json) {
		Gson gson = new Gson();
		PersonPojo move = gson.fromJson(json, PersonPojo.class);
		return move;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);  
	}

}
