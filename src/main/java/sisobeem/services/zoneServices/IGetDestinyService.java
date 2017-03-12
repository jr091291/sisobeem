package sisobeem.services.zoneServices;

import jadex.bridge.IComponentIdentifier;
import sisobeem.artifacts.Coordenada;

public interface IGetDestinyService {

	public Coordenada getDestiny(IComponentIdentifier agent);
}
