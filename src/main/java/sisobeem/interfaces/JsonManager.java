package sisobeem.interfaces;

public interface JsonManager<T> {
	public T toClass(String json);
	public String toJson(T clase);
}
