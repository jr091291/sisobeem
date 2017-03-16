package sisobeem.artifacts.print;

import com.google.gson.Gson;

import sisobeem.abstracts.Action;
import sisobeem.artifacts.print.pojo.SismoPojo;

public class SismoAction  extends Action <SismoPojo>{

	public SismoAction(String name, SismoPojo data) {
		super(name, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SismoPojo toClass(String json) {
		Gson gson = new Gson();
	 	SismoPojo move = gson.fromJson(json, SismoPojo.class);
		return move;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);  
	}

}
