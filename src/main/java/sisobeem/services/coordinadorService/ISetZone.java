package sisobeem.services.coordinadorService;

import java.util.ArrayList;

import jadex.bridge.IComponentIdentifier;

public interface ISetZone {

	
	public ArrayList<IComponentIdentifier> setZone(IComponentIdentifier zone);
	
	public void duracion(int dura);
}
