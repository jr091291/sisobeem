package sisobeem.artifacts.print;

import com.google.gson.Gson;

import sisobeem.abstracts.Action;
import sisobeem.artifacts.print.pojo.EdificePojo;
import sisobeem.artifacts.print.pojo.FinEstadisticaPojo;

public class FinEstadisticaAction  extends Action<FinEstadisticaPojo>{

	public FinEstadisticaAction(String name, FinEstadisticaPojo data) {
		super(name, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public FinEstadisticaPojo toClass(String json) {
		Gson gson = new Gson();
	 	FinEstadisticaPojo move = gson.fromJson(json, FinEstadisticaPojo.class);
		return move;
		
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);  
	}

}
