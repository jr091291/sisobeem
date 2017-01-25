package sisobeem.artifacts.print;

import com.google.gson.Gson;

import sisobeem.abstracts.Action;
import sisobeem.artifacts.print.pojo.RoutePojo;

public class RouteAction extends Action<RoutePojo> {

	public RouteAction(String name, RoutePojo data) {
		super(name, data);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoutePojo toClass(String json) {
		Gson gson = new Gson();
		RoutePojo route = gson.fromJson(json, RoutePojo.class);
		return route;
	}

	@Override
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);  
	}

}
