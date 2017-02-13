package sisobeem.artifacts.print;

import com.google.gson.Gson;

import sisobeem.abstracts.Action;
import sisobeem.artifacts.print.pojo.EdificePojo;

public class EdificeAction extends Action <EdificePojo> {

	public EdificeAction(String name, EdificePojo data) {
		super(name, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public EdificePojo toClass(String json) {
		Gson gson = new Gson();
	 	EdificePojo move = gson.fromJson(json, EdificePojo.class);
		return move;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);  
	}

}
