package sisobeem.services.personServices;

import jadex.bridge.IComponentIdentifier;

/**
 * Interfaz para la recepcion de distintos tipos de mensajes
 * 
 * @author Erley
 *
 */
public interface IReceptorMensajesService {

	/**
	 * Servicio que recibe un mensaje de ayuda
	 * 
	 * @param cidPersonHelp
	 *            identificador del agente que solicita la ayuda
	 */
	public void AyudaMsj(IComponentIdentifier cidPersonHelp);

	/**
	 * Servicio que recibe un mensaje para disminuir los indices de miedo y
	 * enojo
	 */
	public void CalmaMsj();

	/**
	 * Servicio que recibe un mensaje para disminuir los indices de Confianza y
	 * Gregarismo
	 */
	public void ConfianzaMsj();

	/**
	 * Servicio que recibe un mensaje para disminuir los indices de Confianza,
	 * Enojo y Tristreza
	 */
	public void FrustracionMsj();

	/**
	 * Servicio que recibe un mensaje para disminuir los indices de Enojo
	 */
	public void HostilMsj();

	/**
	 * Servicio que recibe un mensaje para disminuir los indices de Confianza y
	 * Miedo
	 */
	public void PanicoMsj();

	/**
	 * Servicio que recibe un mensaje con el identificador de un agente que
	 * solicita primeros auxilios
	 * 
	 * @param cidPersonAux
	 */
	public void PrimeroAuxMsj(IComponentIdentifier cidPersonAux);

	/**
	 * Servicio que recibe un mensaje de resguardo (Quedarse quieto y protegido)
	 */
	public void ResguardoMsj();

	/**
	 * Servicio que recibe un mensaje para disminuir los indices de
	 * Confianza,miedo y tristeza
	 */
	public void MotivacionMsj();
}
