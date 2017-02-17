package sisobeem.agent.person;

import static sisobeem.artifacts.Log.getLog;
import java.util.ArrayList;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bridge.IComponentIdentifier;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.capabilitys.MoveCapability.Aleatorio;
import sisobeem.utilities.Random;

@Agent
@Description("Agente vicil, encargados de vivir la experiencia del terremoto.")
@ProvidedServices({})
public class CivilAgentBDI extends PersonAgentBDI {

	IComponentIdentifier cidlider;

	/**
	 * Configuraciones Iniciales
	 */
	@AgentCreated
	public void init() {

	}

	/**
	 * Cuerpo Principal del agente
	 */
	@AgentBody
	public void body() {

	}

	/**
	 * Caminar, Metodo para activar la capacidad de caminar Metodo para caminar,
	 * se activa por medio de un contexto.
	 */
	@Plan(trigger = @Trigger(factchangeds = "contextCaminar"))
	public void caminar() {
		this.velocidad = Random.getIntRandom(1, 5);
		getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
				super.move.new Aleatorio(this.getAgent(), this.velocidad, this.getPosition(), cidZone));

	}

	/**
	 * Cuando termino de dar caminar
	 */
	@Plan(trigger = @Trigger(goalfinisheds = Aleatorio.class))
	public void caminarEnd() {
		if (contextCaminar) {
			caminar();
		}
	}

	/**
	 * Cuando termino de dar caminar
	 */
	@Plan(trigger = @Trigger(goalfinisheds = sisobeem.capabilitys.EvacuarCapability.Evacuar.class))
	public void Evacuar() {
		if (cidEdifice != null && cidPlant != null) {
			getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(EvacuarEdificio.new Evacuar(
					this.getAgent(), this.conocimientoZona, this.cidPlant, this.cidEdifice));
		}
	}

	/*
	 * @Plan(trigger=@Trigger(goalfinisheds={sisobeem.capabilitys.
	 * EvacuarCapability.Evacuar.class,})) public void Evacuar2() {
	 * System.out.println("Terminó"); }
	 * 
	 * 
	 */
	/**
	 * Acciones al recibir distintos tipos de mensajes
	 */

	@Override
	public void AyudaMsj(IComponentIdentifier cidPersonHelp) {

	}

	@Override
	public void CalmaMsj() {

		if (this.vivo) {
			this.miedo = this.miedo - 1;
			this.enojo = this.enojo - 1;
		}

	}

	@Override
	public void ConfianzaMsj() {
		if (this.vivo) {
			this.confianza = this.confianza + 1;
			this.gregarismo = this.gregarismo + 1;
		}

	}

	@Override
	public void FrustracionMsj() {

		if (this.vivo) {
			this.confianza = this.confianza - 1;
			this.enojo = this.enojo + 1;
			this.tristeza = this.tristeza + 1;
		}

	}

	@Override
	public void HostilMsj() {
		if (this.vivo) {
			this.enojo = this.enojo + 2;
		}
	}

	@Override
	public void PanicoMsj() {
		if (this.vivo) {
			this.miedo = this.miedo + 1;
			this.confianza = this.confianza - 1;
		}

	}

	@Override
	public void PrimeroAuxMsj(IComponentIdentifier cidPersonAux) {

	}

	@Override
	public void ResguardoMsj() {

		if (this.vivo) {
			this.contextCaminar = false;
		}
	}

	@Override
	public void MotivacionMsj() {
		if (this.vivo) {
			this.miedo = this.miedo - 1;
			this.tristeza = this.tristeza - 1;
			this.confianza = this.confianza + 1;
		}
	}

	@Override
	public void Team(IComponentIdentifier parner) {
		cidlider = parner;
	}

	@Override
	public void TomaDeDecisiones() {
		if (this.vivo) {

			// Lideres
			if (this.salud > 70 && this.edad <= 45 && this.edad > 24 && this.liderazgo > 50
					&& this.conocimientoZona == 2 && this.riesgo < 1) {
				if (this.contextSismo) { // Si esta temblando

					if (this.cidPlant == null) {// Si está en la calle
						if (this.myDestiny == null) { // Si no tengo destino
							// Solicitando destino
							
							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Identificando punto seguro");
							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
									this.IdentificarZonas.new FindZonaSegura(this.getAgent(), this.cidZone));
						} else {
							if (this.cidsPeopleHelp == null) {
								// Solicitar Personas que necesitan ayuda
								getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Identificando Personas que necesitan ayuda");
								getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
										FindPersonHelpCapability.new FindPerson(this.getAgent(), this.cidZone));

							} else {

								if (this.contextTeam) { // Si tengo un grupo
									switch (Random.getIntRandom(1, 2)) {
									// dosopciones O evacuar o enviar mensajes
									// al team

									case 1:
										switch (Random.getIntRandom(1, 3)) { // enviar
																				// mensajes
																				// al
																				// grupo
										case 1:
											getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de Calma");
											getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
													msg.new MensajeDeCalma(this.getAgent(), this.cidPlant));
											break;
										case 2:
											getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de motivacion");
											getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
													msg.new MensajeDeMotivacion(this.getAgent(), this.cidPlant));
											break;

										case 3:
											getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de confianza");
											getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
													msg.new MensajeDeConfianza(this.getAgent(), this.cidPlant));
											break;

										default:
											break;
										}
										break;

									case 2: //
										if (this.rute == null) {
											//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Moviendo");
											getAgent().getComponentFeature(IBDIAgentFeature.class)
													.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(),
															this.velocidad, this.getPosition(), cidZone));
										}

										break;
									}

								} else {
									if (this.cidsPeopleHelp.size() > 0) {
										// Como no tiene grupo
										// Enviar invitacion a Team a las
										// personas
										// que necesitan ayuda
										getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Creando Team");
										getAgent().getComponentFeature(IBDIAgentFeature.class)
												.dispatchTopLevelGoal(grupo.new AddPersonNeedHelp(this.getAgent(),
														this.cidPlant, this.cidsPeopleHelp));
									} else {
										// Mensaje de team
										getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de Team");
										getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
												this.grupo.new MensajeDeTeam(this.getAgent(), this.cidZone));
									}
								}

							}

						}

					} else {// Si está en un edificio

						if (this.salidasDisponibles < 0) {
							// Solicitar salidas disponibles
							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Buscando salidas disponibles");
							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
									findsalidas.new salidas(this.getAgent(), this.conocimientoZona, this.cidEdifice));
						} else if (this.salidasDisponibles > 0) {

							if (this.cidsPeopleHelp == null) {
								// Solicitar Personas que necesitan ayuda
								getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Buscando personas que necesitan ayuda");
								getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
										FindPersonHelpCapability.new FindPerson(this.getAgent(), this.cidPlant));

							} else {

								if (this.contextTeam) { // Si tengo un grupo
									switch (Random.getIntRandom(1, 2)) {
									// dosopciones O evacuar o enviar mensajes
									// al team

									case 1:
										switch (Random.getIntRandom(1, 3)) { // enviar
																				// mensajes
																				// al
												
										// grupo
										case 1:
											getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de calma");
											getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
													msg.new MensajeDeCalma(this.getAgent(), this.cidPlant));
											break;
										case 2:
											getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de motivacion");
											getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
													msg.new MensajeDeMotivacion(this.getAgent(), this.cidPlant));
											break;

										case 3:
											getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mesaje de confianza");
											getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
													msg.new MensajeDeConfianza(this.getAgent(), this.cidPlant));
											break;

										default:
											break;
										}
										break;

									case 2: // evacuar
										getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Intentando evacuar");
										getAgent().getComponentFeature(IBDIAgentFeature.class)
												.dispatchTopLevelGoal(EvacuarEdificio.new Evacuar(this.getAgent(),
														this.conocimientoZona, this.cidPlant, this.cidEdifice));
										break;
									}

								} else {
									if (this.cidsPeopleHelp.size() > 0) {
										// Como no tiene grupo
										// Enviar invitacion a Team a las
										// personas
										// que necesitan ayuda
										getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Conformando grupo");
										getAgent().getComponentFeature(IBDIAgentFeature.class)
												.dispatchTopLevelGoal(grupo.new AddPersonNeedHelp(this.getAgent(),
														this.cidPlant, this.cidsPeopleHelp));
									}
								}

							}
						} else {

							// Solicitar salidas disponibles
							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Buscando salidas disponibles");
							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
									findsalidas.new salidas(this.getAgent(), this.conocimientoZona, this.cidEdifice));

						}
					}

				} else { // Si no esta temblando

					if (this.cidPlant == null) {
						// Si está en la calle
						getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
								super.move.new Aleatorio(this.getAgent(), this.velocidad, this.getPosition(), cidZone));
					} else {
						// Si está en un edificio
					}

				}

			}
			// Dependientes
			else if (this.salud < 70 && this.gregarismo > 2 && this.conocimientoZona <= 1) {

				if (this.contextSismo) {
					if (this.cidPlant == null) { // estan en el zone
						if (this.cidlider == null) { // Si no tiene lider
							switch (Random.getIntRandom(1, 2)) {
							case 1:
								getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de ayuda");
								getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(msg.new MensajeAyuda(this.getAgent(), this.cidZone));
								contextResguardarse = true;
								break;
							case 2:
							//	getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensa");
								getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
										this.getPosition(), cidZone));
								contextResguardarse = false;
								break;

							default:
								break;
							}
												

						} else {

							if (this.rute == null) {
						
								getAgent().getComponentFeature(IBDIAgentFeature.class)
										.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
												this.getPosition(), cidZone));
								contextResguardarse = false;

							} else {
								contextResguardarse = false;
								// codigo para moverse por ruta
								getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Moviendome en ruta");
								getAgent().getComponentFeature(IBDIAgentFeature.class)
										.dispatchTopLevelGoal(super.move.new rute(this.getAgent(), this.velocidad,
												this.getPosition(), cidZone, this.rute.get(0)));

								this.rute.remove(0);
							}

						}
					} else { // Estan en un edificio
						if (this.cidlider == null) { // Si no tiene lider
							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de ayuda");
							getAgent().getComponentFeature(IBDIAgentFeature.class)
									.dispatchTopLevelGoal(msg.new MensajeAyuda(this.getAgent(), this.cidPlant));
														
							contextResguardarse = true;
							
						} else {

							if(this.salud>40){
								// evacuar con el lider
								
								contextResguardarse = false;
								getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Intentando evacuar con el grupo");
								getAgent().getComponentFeature(IBDIAgentFeature.class)
										.dispatchTopLevelGoal(EvacuarEdificio.new Evacuar(this.getAgent(),
												this.conocimientoZona, this.cidPlant, this.cidEdifice, this.cidlider));
							}

						}

					}
				} else {

					if (this.cidPlant == null) { // estan en el zone
						
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
										this.getPosition(), cidZone));
					} else { // Estan en un edificio

					}

				}

			}
			// Indepedendientes
			else if (this.edad <= 45 && this.edad > 24 && this.salud > 70 && this.gregarismo < 2 && this.riesgo < 1) {
                if(this.contextSismo){
                	 if(this.cidPlant==null){
                    	 if(this.myDestiny==null){
                    		switch (Random.getIntRandom(1, 5)) {
    						case 1:
    							 getAgent().getComponentFeature(IBDIAgentFeature.class)
    								.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
    										this.getPosition(), cidZone));
    					     	contextResguardarse = false;
    							break;
    						case 2:
    							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":enviando mensaje de hostilidad");
    							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
         								msg.new MensajeDeHostilidad(this.getAgent(),this.cidPlant));
    							break;
    							
    						case 3:
    							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de frustracion ");
    							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
         								msg.new MensajeDeFrustracion(this.getAgent(),this.cidPlant));
    							break;
    						case 4:
    							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de panico");
    							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
         								msg.new MensajeDePanico(this.getAgent(),this.cidPlant));
    						case 5:
    							// Solicitando destino
    							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Tratando de identificar punto seguro");
    							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
    									this.IdentificarZonas.new FindZonaSegura(this.getAgent(), this.cidZone));
         						
    							break;
    							

    						default:
    							break;
    						}
                   		 
                    	 }else{
                    		 
                    		 if(this.rute == null){
                    			 getAgent().getComponentFeature(IBDIAgentFeature.class)
    								.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
    										this.getPosition(), cidZone));
                    		 }else{
                    			 getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Moviendome en ruta");
                    			 getAgent().getComponentFeature(IBDIAgentFeature.class)
    								.dispatchTopLevelGoal(super.move.new rute(this.getAgent(), this.velocidad,
    										this.getPosition(), cidZone,this.rute.get(0)));
                    			 
                    			 this.rute.remove(0);
                    		 }
                    		 
                    	 }
                    	 
                     }else{ //Está en el edificio
                    	 
                         if(this.salidasDisponibles<0){
                         	// Solicitar salidas disponibles
                        	 getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Buscando salidas disponibles");
         						getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
         								findsalidas.new salidas(this.getAgent(), this.conocimientoZona, this.cidEdifice));
                          }else if(this.salidasDisponibles>0){
                         	 switch (Random.getIntRandom(1, 2)) {
         					case 1:
         						getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Evacuando Independientemente");
         						getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
         								EvacuarEdificio.new Evacuar(this.getAgent(), this.conocimientoZona,this.cidPlant, this.cidEdifice));
         					
         						break;
         						
         					case 2:
         					      switch (Random.getIntRandom(1, 3)) {
    							case 1:
    								getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de hsotilidad");
    								getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
    	     								msg.new MensajeDeHostilidad(this.getAgent(),this.cidPlant));
    								break;
    								
    							case 2:
    								getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de frustracion");
    								getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
    	     								msg.new MensajeDeFrustracion(this.getAgent(),this.cidPlant));
    								break;
    							case 3:
    								getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de panico");
    								getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
    	     								msg.new MensajeDePanico(this.getAgent(),this.cidPlant));
    	     						
    								break;

    							default:
    								break;
    							}

         					default:
         						break;
         					}
                          }else{
                        	  
                        	  switch (Random.getIntRandom(1, 3)) {
    						case 1:
    							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de panico");
    							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
         								msg.new MensajeDePanico(this.getAgent(),this.cidPlant));
    							break;
    						case 2:
    							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de ayuda");
    							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
         								msg.new MensajeAyuda(this.getAgent(),this.cidPlant));
    							break;
    						case 3:
    							getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Me suicido!!");
    							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
         								this.suicidio.new SaltarDelEdificio(this.getAgent(),this.cidPlant));
    						default:
    							break;
    						}
                         	 
                          }
                    	 
                     }
                }else{
                	if(this.cidPlant==null){
                		 getAgent().getComponentFeature(IBDIAgentFeature.class)
							.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
									this.getPosition(), cidZone));
                	}
                	
                }
                 
			}else{
				getLog().setFatal("NO SOY NI INDEPENDIENTE NI DEPDENDIENTE NI LIDER ¿Y Ahora que hago?");
			}
		}

	}

	@Override
	public int getSalud() {
		// TODO Auto-generated method stub
		return this.salud;
	}

}
