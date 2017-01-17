package sisobeem.services.enviromentService;

import jadex.bridge.IComponentIdentifier;

/**
 * Servicios de comunicacion que implementan los "Ambientes"
 * 
 * @author Erley
 *
 */
public interface IComunicarMensajesService {

	/**
	 * Servicio que solicita enviar un mensaje de Ayuda en el rango de escucha
	 * del emisor
	 * 
	 * @param emisor
	 *            Agente que envia el mensaje de ayuda
	 */
	public void AyudaMsj(IComponentIdentifier emisor);

	/**
	 * Servicio que solicita enviar un mensaje de calma el rango de escucha del
	 * emisor
	 * 
	 * @param emisor
	 *            Agente que envia el mensaje de ayuda
	 */
	public void CalmaMsj(IComponentIdentifier emisor);

	/**
	 * Servicio para enviar un mensaje de calma a un agente especifico
	 * 
	 * @param emisor
	 * @param receptor
	 */
	public void CalmaMsj(IComponentIdentifier emisor, IComponentIdentifier receptor);

	/**
	 * Servicio que solicita enviar un mensaje de confianza en un rango de
	 * escucha
	 * 
	 * @param emisor
	 *            Agente que envía el mensaje
	 */
	public void ConfianzaMsj(IComponentIdentifier emisor);

	/**
	 * Servicio para enviar un mensaje de confianza a un agente especifico
	 * 
	 * @param emisor
	 * @param receptor
	 */
	public void ConfianzaMsj(IComponentIdentifier emisor, IComponentIdentifier receptor);

	/**
	 * Servicio que solicita enviar un mensaje de frustracionsen un rango de
	 * escucha
	 * 
	 * @param emisor
	 *            Agente que envia el mensaje
	 */
	public void FrustracionMsj(IComponentIdentifier emisor);

	/**
	 * Servicio que envia un mensaje de frustracion a un agente especifico
	 * 
	 * @param emisor
	 * @param receptor
	 */
	public void FrustracionMsj(IComponentIdentifier emisor, IComponentIdentifier receptor);

	/**
	 * Servicio que solicita enviar un mensaje de Hostilidad en un rango de
	 * escucha
	 * 
	 * @param emisor
	 *            Agente que envia el mensaje
	 */
	public void HostilMsj(IComponentIdentifier emisor);

	/**
	 * Servicio que solicita enviar un mensaje hostil A un agente determinado
	 * 
	 * @param emisor
	 * @param receptor
	 */
	public void HostilMsj(IComponentIdentifier emisor, IComponentIdentifier receptor);

	/**
	 * Servicio que solicita enviar un mensaje de Panico en un rango de escucha
	 * 
	 * @param emisor
	 *            Agente que envia el mensaje
	 */
	public void PanicoMsj(IComponentIdentifier emisor);

	/**
	 * Servicio que envia un mensaje de Panico a un agente especifico
	 * 
	 * @param emisor
	 * @param receptor
	 */
	public void PanicoMsj(IComponentIdentifier emisor, IComponentIdentifier receptor);

	/**
	 * Servicio que solicita enviar un mensaje de Primeros Auxilios en un rango
	 * de escucha
	 * 
	 * @param emisor
	 *            Agente que envía el mensaje
	 */
	public void PrimeroAuxMsj(IComponentIdentifier emisor);

	/**
	 * Servicio que envia un mensaje de primeros aux a un agente especifico
	 * 
	 * @param emisor
	 * @param receptor
	 */
	public void PrimeroAuxMsj(IComponentIdentifier emisor, IComponentIdentifier receptor);

	/**
	 * Servicio que solicita enviar un mensaje de Resguardo en un rango de
	 * escucha
	 * 
	 * @param emisor
	 *            Agente que envia el mensaje
	 */
	public void ResguardoMsj(IComponentIdentifier emisor);

	/**
	 * Servicio que envia un mensaje de resguardo a un agente especifico
	 * 
	 * @param emisor
	 * @param receptor
	 */
	public void ResguardoMsj(IComponentIdentifier emisor, IComponentIdentifier receptor);

	/**
	 * Servicio que solicita enviar un mensaje de Motivacion en un rango de
	 * escucha
	 * 
	 * @param emisor
	 *            Agente que envia el mensaje
	 */
	public void Motivacion(IComponentIdentifier emisor);

	/**
	 * Servicio que envia un mensaje de motivacion a un agente especifico
	 * 
	 * @param emisor
	 * @param receptor
	 */
	public void Motivacion(IComponentIdentifier emisor, IComponentIdentifier receptor);
}
