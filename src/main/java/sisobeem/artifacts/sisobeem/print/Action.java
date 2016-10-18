package sisobeem.artifacts.sisobeem.print;

import sisobeem.artifacts.JsonManager;

public abstract class Action<T> implements JsonManager<T> {
	private String name;
	private T data;
	
	public Action(String name, T data) {
		super();
		this.name = name;
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}	
}
