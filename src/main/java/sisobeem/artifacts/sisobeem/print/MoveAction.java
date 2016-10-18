package sisobeem.artifacts.sisobeem.print;

import com.google.gson.Gson;

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
	public String toJson(MovePojo clase) {
		Gson gson = new Gson();
		return gson.toJson(this.getData());  
	}


}
