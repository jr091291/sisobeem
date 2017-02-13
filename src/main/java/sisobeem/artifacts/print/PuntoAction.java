package sisobeem.artifacts.print;

import com.google.gson.Gson;

import sisobeem.abstracts.Action;
import sisobeem.artifacts.print.pojo.PuntoPojo;

public class PuntoAction extends Action <PuntoPojo> {

	public PuntoAction(String name, PuntoPojo data) {
		super(name, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PuntoPojo toClass(String json) {
		Gson gson = new Gson();
	 	PuntoPojo move = gson.fromJson(json, PuntoPojo.class);
		return move;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);  
	}

}
