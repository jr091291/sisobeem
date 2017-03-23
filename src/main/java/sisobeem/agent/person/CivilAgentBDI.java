package sisobeem.agent.person;

import static sisobeem.artifacts.Log.getLog;
import java.util.ArrayList;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bdiv3.features.IBDIAgentFeature;
import jadex.bdiv3.runtime.wrappers.ListWrapper;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.print.pojo.EmergencyPojo;
import sisobeem.capabilitys.MoveCapability.Aleatorio;
import sisobeem.services.enviromentService.IComunicarMensajesService;
import sisobeem.services.personServices.ITeamService;
import sisobeem.utilities.Random;

@Agent
@Description("Agente civil, encargados de vivir la experiencia del terremoto.")
@ProvidedServices({})
public class CivilAgentBDI extends PersonAgentBDI {

	IComponentIdentifier cidlider;
	
	int umbralSalud = 50;
	

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
		// this.velocidad = Random.getIntRandom(1, 5);
		// getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
		// super.move.new Aleatorio(this.getAgent(), this.velocidad,
		// this.getPosition(), cidZone));

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
	public void Team(IComponentIdentifier parner, double liderazgo) {
		
		//System.out.println("MENSAJE DE TEAM RECIBIDO");
		if (liderazgo > this.liderazgo) {
			this.cidlider = parner;
			IFuture<ITeamService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ITeamService.class, parner);

			zoneService.addResultListener(new IResultListener<ITeamService>() {

				@Override
				public void resultAvailable(ITeamService result) {
					result.entrar(getAgent().getComponentIdentifier());
				}

				@Override
				public void exceptionOccurred(Exception exception) {
                   //  getLog().setError(exception.getMessage());
				}

			});

		}
	}

	@Override
	public void TomaDeDecisiones() {

		agent.getComponentFeature(IExecutionFeature.class).waitForDelay(1000).get();
		this.velocidad = Random.getIntRandom(1, 5);
		if (this.vivo) {

			// Lideres
			if (this.salud > 70 && this.edad <= 45 && this.edad > 24 && this.liderazgo > 50
					&& this.conocimientoZona == 2 && this.riesgo < 1) {
				lideres();

			}
			// Dependientes
			else if (this.salud < 70 && this.gregarismo > 2 && this.conocimientoZona <= 1) {
				dependientes();
			}
			// Indepedendientes
			else if (this.edad <= 45 && this.edad > 24 && this.salud > 70 && this.gregarismo < 2 && this.riesgo < 1) {
				independientes();
			} else {
				// getLog().setFatal("NO SOY NI INDEPENDIENTE NI DEPDENDIENTE NI
				// LIDER ¿Y Ahora que hago?");

				switch (Random.getIntRandom(1, 3)) {
				case 1:
					dependientes();
					break;
				case 2:
					independientes();
					break;
				case 3:
					independientes();
					break;

				default:
					break;
				}
			}
		}

	}

	/**
	 * Define el comportamiento de los lideres
	 */
	private void lideres() {

		// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+"
		// Soy Lider");

		if (this.contextSismo||seguirCorriendo) { // Si esta temblando

			if (this.cidPlant == null) {// Si está en la calle
				if (this.myDestiny == null) { // Si no tengo destino
					// Solicitando destino

				//   getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Identificando punto seguro");
					getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
							this.IdentificarZonas.new FindZonaSegura(this.getAgent(), this.cidZone));
				} else {
					if (this.agentAyuda == null) {
						// Solicitar Personas que necesitan ayuda
						//	 getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Identificando Personas que necesitan ayuda");
						getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
								FindPersonHelpCapability.new FindPerson(this.getAgent(), this.cidZone));

					} else {

						if (this.contextTeam) { // Si tengo un grupo
							switch (Random.getIntRandom(1, 2)) {
							// dosopciones O evacuar o enviar mensajes
							// al team

							case 1:
								switch (Random.getIntRandom(1, 4)) { // enviar
																		// mensajes
																		// al
																		// grupo
								case 1:
									// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de Calma");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											msg.new MensajeDeCalma(this.getAgent(), this.cidZone));
									break;
								case 2:
									//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de motivacion");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											msg.new MensajeDeMotivacion(this.getAgent(), this.cidZone));
									break;

								case 3:
									//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de confianza");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											msg.new MensajeDeConfianza(this.getAgent(), this.cidZone));
									break;
								case 4:
									//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de confianza");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											msg.new MensajeDeResguardo(this.getAgent(), this.cidZone));
									break;

								default:
									//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":buscando personas que necesiten ayuda");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											FindPersonHelpCapability.new SolicitarRuta(this.getAgent(), this.cidZone,
													this.myDestiny));
									break;
								}
								break;

							case 2: //
								if (this.rute == null) {
									//	getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Moviendo");
									getAgent().getComponentFeature(IBDIAgentFeature.class)
											.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(),
													this.velocidad, this.getPosition(), cidZone, this.tipo));
								} else {
									if(this.rute[0]==null){
										//		getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de motivacion");
										getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
												msg.new MensajeDeMotivacion(this.getAgent(), this.cidZone));
									}else{
										//		getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+ ": Moviendome en ruta");
											getAgent().getComponentFeature(IBDIAgentFeature.class)
													.dispatchTopLevelGoal(super.move.new rute(this.getAgent(), this.velocidad,
															this.getPosition(), cidZone, this.rute[0], this.tipo));

											EliminarPositionRoute(0);
									}
								}

								break;
								
							default:
								//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de Calma");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											msg.new MensajeDeCalma(this.getAgent(), this.cidZone));
								break;
							}

						} else {
							
							//System.out.println(contextTeam);
							if (this.agentAyuda!=null) {
								// Como no tiene grupo
								// Enviar invitacion a Team a las
								// personas
								// que necesitan ayuda
								/*
								getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Creando Team");
								getAgent().getComponentFeature(IBDIAgentFeature.class)
										.dispatchTopLevelGoal(grupo.new AddPersonNeedHelp(this.getAgent(), this.cidZone,
												ArrayListToArray(this.cidsPeopleHelp), this.liderazgo));
								
								*/
								// Mensaje de team
								
								
								switch (Random.getIntRandom(1, 2)) {
								case 1:
									//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de Team");

									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											this.grupo.new MensajeDeTeam(this.getAgent(), this.cidZone, this.liderazgo));
									
									break;
								case 2:
									if (this.rute == null) {
										//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Moviendo");
										getAgent().getComponentFeature(IBDIAgentFeature.class)
												.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(),
														this.velocidad, this.getPosition(), cidZone, this.tipo));
									} else {
										if(this.rute[0]==null){
											//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de motivacion");
											getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
													msg.new MensajeDeMotivacion(this.getAgent(), this.cidZone));
										}else{
											//	getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+ ": Moviendome en ruta");
												getAgent().getComponentFeature(IBDIAgentFeature.class)
														.dispatchTopLevelGoal(super.move.new rute(this.getAgent(), this.velocidad,
																this.getPosition(), cidZone, this.rute[0], this.tipo));

												EliminarPositionRoute(0);
										}
									}
								default:
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											this.grupo.new MensajeDeTeam(this.getAgent(), this.cidZone, this.liderazgo));
									break;
								}
								
								
								
								
						
							} else {
								// Mensaje de team
								//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":agent ayuda es igual a null!");

								getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
										this.grupo.new MensajeDeTeam(this.getAgent(), this.cidZone, this.liderazgo));
							}
						}

					}

				}

			} else {// Si está en un edificio

				if (this.salidasDisponibles < 0) {
					// Solicitar salidas disponibles
					//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Buscando salidas disponibles");
					getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
							findsalidas.new salidas(this.getAgent(), this.conocimientoZona, this.cidEdifice));
				} else if (this.salidasDisponibles > 0) {

					if (this.agentAyuda == null ) {
						// Solicitar Personas que necesitan ayuda
						// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Buscando personas que necesitan ayuda");
						getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
								FindPersonHelpCapability.new FindPerson(this.getAgent(), this.cidPlant));

					} else {

						if (this.contextTeam) { // Si tengo un grupo
							switch (Random.getIntRandom(1, 2)) {
							// dosopciones O evacuar o enviar mensajes
							// al team

							case 1:
								switch (Random.getIntRandom(1, 4)) { // enviar
																		// mensajes
																		// al

								// grupo
								case 1:
									//		 getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de calma");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											msg.new MensajeDeCalma(this.getAgent(), this.cidPlant));
									break;
								case 2:
									//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de motivacion");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											msg.new MensajeDeMotivacion(this.getAgent(), this.cidPlant));
									break;

								case 3:
									//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mesaje de confianza");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											msg.new MensajeDeConfianza(this.getAgent(), this.cidPlant));
									break;
								case 4:
									//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mesaje de confianza");
									getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
											msg.new MensajeDeResguardo(this.getAgent(), this.cidPlant));
									break;
								default:
									getAgent().getComponentFeature(IBDIAgentFeature.class)
											.dispatchTopLevelGoal(this.suicidio.new HacerNada());
									break;
								}
								break;

							case 2: // evacuar
								//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Intentando evacuar");
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
								//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Conformando grupo");
								getAgent().getComponentFeature(IBDIAgentFeature.class)
										.dispatchTopLevelGoal(grupo.new AddPersonNeedHelp(this.getAgent(),
												this.cidPlant, ArrayListToArray(this.cidsPeopleHelp), this.liderazgo));
							}else{
								getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
										msg.new MensajeDePanico(this.getAgent(), this.cidPlant));
							}
						}

					}
				} else {

					// Solicitar salidas disponibles
					// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Buscando salidas disponibles");
					getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
							findsalidas.new salidas(this.getAgent(), this.conocimientoZona, this.cidEdifice));

				}
			}

		} else { // Si no esta temblando

			if (this.cidPlant == null) {
				// Si está en la calle
				//getLog().setDebug("Moviendome aleatoriamente");
				getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(super.move.new Aleatorio(
						this.getAgent(), this.velocidad, this.getPosition(), cidZone, this.tipo));
			} else {
				// Si está en un edificio
				// System.out.println("Estoy en un edifcio y no esta
				 //temblando");
				getAgent().getComponentFeature(IBDIAgentFeature.class)
						.dispatchTopLevelGoal(this.suicidio.new HacerNada());
			}

		}
	}

	/**
	 * Define el comportamiento de las personas dependientes
	 */
	private void dependientes() {
		// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+"
		// Soy Dependiente");

		if (this.contextSismo||seguirCorriendo) {
         
    			if (this.cidPlant == null) { // estan en el zone
    			    if(this.salud>this.umbralSalud){
    			    	if (this.cidlider == null) { // Si no tiene lider
        					switch (Random.getIntRandom(1, 4)) {
        					case 1:
        						//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de ayuda");
        						getAgent().getComponentFeature(IBDIAgentFeature.class)
        								.dispatchTopLevelGoal(msg.new MensajeAyuda(this.getAgent(), this.cidZone));
        						//contextResguardarse = true;
        						break;
        					case 2:
        						//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensa");
        						getAgent().getComponentFeature(IBDIAgentFeature.class)
        								.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
        										this.getPosition(), cidZone, this.tipo));
        						//contextResguardarse = false;
        						break;
        					case 3:
        						getAgent().getComponentFeature(IBDIAgentFeature.class)
        						.dispatchTopLevelGoal(super.resguardarse .new Resguardarse(this.getAgent(), cidZone));
        						break;
        						
        					case 4: 
        					      if(this.rute[0]==null){
          					    	//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de ayuda");
          								getAgent().getComponentFeature(IBDIAgentFeature.class)
          										.dispatchTopLevelGoal(msg.new MensajeAyuda(this.getAgent(), this.cidZone));
          								//contextResguardarse = true;
          					    	  
          					      }else{
          					    		//contextResguardarse = false;
          								// codigo para moverse por ruta
          								//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()
          								//	+ ":Dependiente, Moviendome en ruta");
          								getAgent().getComponentFeature(IBDIAgentFeature.class)
          										.dispatchTopLevelGoal(super.move.new rute(this.getAgent(), this.velocidad,
          												this.getPosition(), cidZone, this.rute[0], this.tipo));

          								EliminarPositionRoute(0);
          					      }
        					      break;
        					default:
        						getAgent().getComponentFeature(IBDIAgentFeature.class)
        						.dispatchTopLevelGoal(super.resguardarse .new Resguardarse(this.getAgent(), cidZone));
        				      //  contextResguardarse = true;
        						break;
        					}

        				} else {

        					if (this.rute == null) {
        						
        						switch (Random.getIntRandom(1, 2)) {
        						case 1:
        							//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensa");
        							getAgent().getComponentFeature(IBDIAgentFeature.class)
        									.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
        											this.getPosition(), cidZone, this.tipo));
        							//contextResguardarse = false;
        							break;
        						case 2:
        							getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
        									this.IdentificarZonas.new FindZonaSegura(this.getAgent(), this.cidZone));
        							break;
        						default:
        							getAgent().getComponentFeature(IBDIAgentFeature.class)
        							.dispatchTopLevelGoal(super.resguardarse .new Resguardarse(this.getAgent(), cidZone));
        							break;

        						}

        						
        						//getLog().setDebug("Movimiento Aleatorio");
        					//	getAgent().getComponentFeature(IBDIAgentFeature.class)
        							//	.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
        									//	this.getPosition(), cidZone, this.tipo));
        						//contextResguardarse = false;

        					} else {
        					      if(this.rute[0]==null){
        					    	//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de ayuda");
        								getAgent().getComponentFeature(IBDIAgentFeature.class)
        										.dispatchTopLevelGoal(msg.new MensajeAyuda(this.getAgent(), this.cidZone));
        								//contextResguardarse = true;
        					    	  
        					      }else{
        					    		//contextResguardarse = false;
        								// codigo para moverse por ruta
        								//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()
        								//	+ ":Dependiente, Moviendome en ruta");
        								getAgent().getComponentFeature(IBDIAgentFeature.class)
        										.dispatchTopLevelGoal(super.move.new rute(this.getAgent(), this.velocidad,
        												this.getPosition(), cidZone, this.rute[0], this.tipo));

        								EliminarPositionRoute(0);
        					      }
        					}

        				}
    			    }else{
    			    	switch (Random.getIntRandom(1, 4)) {
    					case 1:
    						//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensa");
    						getAgent().getComponentFeature(IBDIAgentFeature.class)
    								.dispatchTopLevelGoal(super.msg.new MensajeDePanico(this.getAgent(), cidZone));
    						//contextResguardarse = false;
    						break;
    					case 2:
    						getAgent().getComponentFeature(IBDIAgentFeature.class)
    						.dispatchTopLevelGoal(super.msg.new MensajeDePrimerosAux(this.getAgent(), cidZone));
    						break;
    					case 3:
    						getAgent().getComponentFeature(IBDIAgentFeature.class)
    						.dispatchTopLevelGoal(super.msg.new MensajeDeFrustracion(this.getAgent(), cidZone));
    						break;
    					case 4:
    						getAgent().getComponentFeature(IBDIAgentFeature.class)
    						.dispatchTopLevelGoal(super.msg.new MensajeAyuda(this.getAgent(), cidZone));
    						break;
    					default:
    						getAgent().getComponentFeature(IBDIAgentFeature.class)
    						.dispatchTopLevelGoal(super.msg.new MensajeDePanico(this.getAgent(), cidZone));
    						break;

    					}
    	            	
    			    }
    			} else { // Estan en un edificio
                     if(this.salud>this.umbralSalud){
         				if (this.cidlider == null) { // Si no tiene lider
        					//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de ayuda");
        					getAgent().getComponentFeature(IBDIAgentFeature.class)
        							.dispatchTopLevelGoal(msg.new MensajeAyuda(this.getAgent(), this.cidPlant));

        					contextResguardarse = true;

        				} else {

        					if (this.salud > 40) {
        						// evacuar con el lider

        						contextResguardarse = false;
        						// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Intentando evacuar con el grupo");
        						getAgent().getComponentFeature(IBDIAgentFeature.class)
        								.dispatchTopLevelGoal(EvacuarEdificio.new Evacuar(this.getAgent(),
        										this.conocimientoZona, this.cidPlant, this.cidEdifice, this.cidlider));
        					} else {
        						getAgent().getComponentFeature(IBDIAgentFeature.class)
        								.dispatchTopLevelGoal(msg.new MensajeDePrimerosAux(this.getAgent(), this.cidPlant));
        					}

        				}
                     }else{ //No tiene salud sufciente
                    	 
                    	 switch (Random.getIntRandom(1, 4)) {
     					case 1:
     						//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensa");
     						getAgent().getComponentFeature(IBDIAgentFeature.class)
     								.dispatchTopLevelGoal(super.msg.new MensajeDePanico(this.getAgent(), cidPlant));
     						//contextResguardarse = false;
     						break;
     					case 2:
     						getAgent().getComponentFeature(IBDIAgentFeature.class)
     						.dispatchTopLevelGoal(super.msg.new MensajeDePrimerosAux(this.getAgent(), cidPlant));
     						break;
     					case 3:
     						getAgent().getComponentFeature(IBDIAgentFeature.class)
     						.dispatchTopLevelGoal(super.msg.new MensajeDeFrustracion(this.getAgent(), cidPlant));
     						break;
     					case 4:
     						getAgent().getComponentFeature(IBDIAgentFeature.class)
     						.dispatchTopLevelGoal(super.msg.new MensajeAyuda(this.getAgent(), cidPlant));
     						break;
     					default:
     						getAgent().getComponentFeature(IBDIAgentFeature.class)
     						.dispatchTopLevelGoal(super.msg.new MensajeDePanico(this.getAgent(), cidPlant));
     						break;

     					}
     	            	
                     }

    			}
          
		} else {

			if (this.cidPlant == null) {
				// Si está en la calle
				//getLog().setDebug("Moviendo aleatoriamente");
				getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(super.move.new Aleatorio(
						this.getAgent(), this.velocidad, this.getPosition(), cidZone, this.tipo));
			} else {
				// Si está en un edificio
				// System.out.println("Estoy en un edifcio y no esta
				// temblando");
				//getLog().setDebug("No hago nada");
				getAgent().getComponentFeature(IBDIAgentFeature.class)
						.dispatchTopLevelGoal(this.suicidio.new HacerNada());

			}

		}
	}

	/**
	 * Define el comportamiento de las personas independientes
	 */
	private void independientes() {
		// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+"
		// Soy Independiente");
		if (this.contextSismo||seguirCorriendo) {
			if (this.cidPlant == null) { // si esta en el zone
				if (this.myDestiny == null) {
					switch (Random.getIntRandom(1, 5)) {
					case 1:
						//	getLog().setDebug("Movimiento aleatorio");
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
										this.getPosition(), cidZone, this.tipo));
						contextResguardarse = false;
						break;
					case 2:
						// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":enviando mensaje de hostilidad");
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(msg.new MensajeDeHostilidad(this.getAgent(), this.cidZone));
						break;

					case 3:
						//	getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de frustracion ");
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(msg.new MensajeDeFrustracion(this.getAgent(), this.cidZone));
						break;
					case 4:
						// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de panico");
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(msg.new MensajeDePanico(this.getAgent(), this.cidZone));
					case 5:
						// Solicitando destino
						//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Tratando de identificar punto seguro");
						getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
								this.IdentificarZonas.new FindZonaSegura(this.getAgent(), this.cidZone));

						break;

					default:
						getAgent().getComponentFeature(IBDIAgentFeature.class)
						.dispatchTopLevelGoal(msg.new MensajeDePanico(this.getAgent(), this.cidZone));
						break;
					}

				} else {

					if (this.rute == null) {
						//getLog().setDebug("Movimiento aleatorio");
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(super.move.new Aleatorio(this.getAgent(), this.velocidad,
										this.getPosition(), cidZone, this.tipo));
					} else {
						
						if(this.rute[0]!=null){
							//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()
							//+ "Independiente :Moviendome en ruta");
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(super.move.new rute(this.getAgent(), this.velocidad,
										this.getPosition(), cidZone, this.rute[0], this.tipo));

						EliminarPositionRoute(0);
						}else{
							getAgent().getComponentFeature(IBDIAgentFeature.class)
							.dispatchTopLevelGoal(this.suicidio.new HacerNada());
					        
						}
					}

				}

			} else { // Está en el edificio

				// getLog().setDebug("Estoy en un edificio");

				if (this.salidasDisponibles < 0) {
					// Solicitar salidas disponibles
					//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Buscando salidas disponibles");
					getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
							findsalidas.new salidas(this.getAgent(), this.conocimientoZona, this.cidEdifice));
				} else if (this.salidasDisponibles > 0) {
					switch (Random.getIntRandom(1, 2)) {
					case 1:
						// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":
						// Evacuando Independientemente");
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(EvacuarEdificio.new Evacuar(this.getAgent(),
										this.conocimientoZona, this.cidPlant, this.cidEdifice));

						break;

					case 2:
						switch (Random.getIntRandom(1, 3)) {
						case 1:
							//	getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Enviando mensaje de hsotilidad");
							getAgent().getComponentFeature(IBDIAgentFeature.class)
									.dispatchTopLevelGoal(msg.new MensajeDeHostilidad(this.getAgent(), this.cidPlant));
							break;

						case 2:
							//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de frustracion");
							getAgent().getComponentFeature(IBDIAgentFeature.class)
									.dispatchTopLevelGoal(msg.new MensajeDeFrustracion(this.getAgent(), this.cidPlant));
							break;
						case 3:
							//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de panico");
							getAgent().getComponentFeature(IBDIAgentFeature.class)
									.dispatchTopLevelGoal(msg.new MensajeDePanico(this.getAgent(), this.cidPlant));

							break;

						default:
							getAgent().getComponentFeature(IBDIAgentFeature.class)
									.dispatchTopLevelGoal(this.suicidio.new HacerNada());
							break;
						}

						break;

					default:
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(this.suicidio.new HacerNada());
						break;
					}
				} else {

					switch (Random.getIntRandom(1, 3)) {
					case 1:
						// getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de panico");
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(msg.new MensajeDePanico(this.getAgent(), this.cidPlant));
						break;
					case 2:
						//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+":Enviando mensaje de ayuda");
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(msg.new MensajeAyuda(this.getAgent(), this.cidPlant));
						break;
					case 3:
						//getLog().setDebug(getAgent().getComponentIdentifier().getLocalName()+": Me suicido!!");
						getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(
								this.suicidio.new SaltarDelEdificio(this.getAgent(), this.cidPlant));
					default:
						getAgent().getComponentFeature(IBDIAgentFeature.class)
								.dispatchTopLevelGoal(this.suicidio.new HacerNada());
						break;
					}

				}

			}
		} else {
			if (this.cidPlant == null) {
				// Si está en la calle
				// System.out.println("Me muevo en la calle");
				//getLog().setDebug("Moienvo aleatorioamente");
				getAgent().getComponentFeature(IBDIAgentFeature.class).dispatchTopLevelGoal(super.move.new Aleatorio(
						this.getAgent(), this.velocidad, this.getPosition(), cidZone, this.tipo));
			} else {
				// Si está en un edificio
				// System.out.println("Estoy en un edifcio y no esta
				// temblando");
				getAgent().getComponentFeature(IBDIAgentFeature.class)
						.dispatchTopLevelGoal(this.suicidio.new HacerNada());
			}

		}
	}

	@Override
	public int getSalud() {
		// TODO Auto-generated method stub
		return this.salud;
	}

	@Override
	public void setDestiny(Coordenada c) {
		this.solicitarRuta();
		
	}

	

	



}
