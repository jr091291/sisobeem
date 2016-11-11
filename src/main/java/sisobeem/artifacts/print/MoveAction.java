package sisobeem.artifacts.print;

import com.google.gson.Gson;

import sisobeem.abstracts.Action;
import sisobeem.artifacts.print.pojo.MovePojo;

public class MoveAction extends Action <MovePojo>{

	public MoveAction(String name, MovePojo data) {
		super(name, data);
	}

	@Override
	public MovePojo toClass(String json) {
		Gson gson = new Gson();
	 	MovePojo move = gson.fromJson(json, MovePojo.class);
		return move;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);  
	}
}
