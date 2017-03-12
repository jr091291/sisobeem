package sisobeem.capabilitys;

import static sisobeem.artifacts.Log.getLog;

import java.util.ArrayList;

import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Capability;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.model.MProcessableElement.ExcludeMode;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.services.zoneServices.IMapaService;
import sisobeem.utilities.Random;

@Capability
@RequiredServices({ @RequiredService(name = "IMapaService", type = IMapaService.class) })

public class MoveCapability {

	public MoveCapability() {

	}

	/**
	 * Meta movimiento Aleatorio
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class Aleatorio {

		@GoalParameter
		IInternalAccess agent;

		@GoalParameter
		protected int velocidad;

		@GoalParameter
		IComponentIdentifier zone;

		@GoalParameter
		protected Coordenada position;

		public Aleatorio(IInternalAccess agent, int velocidad, Coordenada position, IComponentIdentifier zone) {
			// System.out.println(agent.getComponentIdentifier().getLocalName());

			// System.out.println("Entro a la capacidad Aleatoria"+velocidad);
			// System.out.println(position.getX());
			this.agent = agent;
			this.velocidad = velocidad;
			this.position = position;
			this.zone = zone;
			aleatorio(agent, velocidad, position, zone);

		}

	}

	/**
	 * Movimiento Aleatorio 1.0.
	 */

	@Plan
	protected void aleatorio(IInternalAccess agent, int velocidad, Coordenada position, IComponentIdentifier zone) {
		// System.out.println("Entró en el trigger");
		// System.out.println("Plan movimiento aleatorio: "+velocidad+"
		// "+position.getX()+","+position.getY());
		// System.out.println(agent.getComponentIdentifier().getLocalName());

		try {
			Thread.sleep(velocidad * 1000);
		} catch (InterruptedException e) {

			System.out.println("error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("voy a moverme");

		IFuture<IMapaService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IMapaService.class, zone);

		// System.out.println(zoneService);
		// System.out.println("3");
		zoneService.addResultListener(new IResultListener<IMapaService>() {

			@Override
			public void resultAvailable(IMapaService result) {

				// System.out.println("resultado");

				// System.out.println(position.getX()+" -- "+position.getY());

				Coordenada nueva = getCoordenadaAleatoria(position);

				// System.out.println(getMyPosition().getX()+" -
				// "+getMyPosition().getY()+" to "+nueva.getX()+" -
				// "+nueva.getY());
				if (result.changePosition(nueva, agent.getComponentIdentifier()))
					setMyPosition(nueva);

			}

			@Override
			public void exceptionOccurred(Exception exception) {

			}

		});

	}

	/**
	 * Método para elegir al nueva posicion
	 * 
	 * @param posicionActual
	 * @return
	 */

	public Coordenada getCoordenadaAleatoria(Coordenada posicionActual) {

		try {
			Coordenada nuevaPosicion = new Coordenada();
			int aleatorio = Random.getIntRandom(1, 8);

			switch (aleatorio) {
			case 1:
				// Arriba
				nuevaPosicion.setX(posicionActual.getX());
				nuevaPosicion.setY(posicionActual.getY() + 1);
				break;
			case 2:
				nuevaPosicion.setX(posicionActual.getX());
				nuevaPosicion.setY(posicionActual.getY() - 1);
				break;
			case 3:
				// Izquierda
				nuevaPosicion.setX(posicionActual.getX() - 1);
				nuevaPosicion.setY(posicionActual.getY());
				break;
			case 4:
				// Derecha
				nuevaPosicion.setX(posicionActual.getX() + 1);
				nuevaPosicion.setY(posicionActual.getY());
				break;
			case 5:
				// Arriba derecha
				nuevaPosicion.setX(posicionActual.getX() + 1);
				nuevaPosicion.setY(posicionActual.getY() + 1);
				break;
			case 6:
				// Arriba izquierda
				nuevaPosicion.setX(posicionActual.getX() - 1);
				nuevaPosicion.setY(posicionActual.getY() + 1);
				break;
			case 7:
				// Abajo derecha
				nuevaPosicion.setX(posicionActual.getX() + 1);
				nuevaPosicion.setY(posicionActual.getY() - 1);
				break;
			case 8:
				// Abajo Izquierda
				nuevaPosicion.setX(posicionActual.getX() - 1);
				nuevaPosicion.setY(posicionActual.getY() - 1);
				break;
			default:
				break;
			}

			return nuevaPosicion;
		} catch (Exception e) {

			getLog().setError("Error  capturado, en la eleccion aleatoria del mvimiento");

		}
		return posicionActual;

	}



	/**
	 * Get the wordtable.
	 */
	@Belief
	public native Coordenada getMyPosition();

	@Belief
	public native void setMyPosition(Coordenada c);
	
	
	/**
	 * Meta movimiento Aleatorio
	 */
	@Goal(excludemode = ExcludeMode.Never)
	public class rute {

		@GoalParameter
		IInternalAccess agent;

		@GoalParameter
		protected int velocidad;

		@GoalParameter
		IComponentIdentifier zone;

		@GoalParameter
		protected Coordenada position;
		
		@GoalParameter
		protected Coordenada destino;

		public rute(IInternalAccess agent, int velocidad, Coordenada position, IComponentIdentifier zone, Coordenada destino) {
		
			this.agent = agent;
			this.velocidad = velocidad;
			this.position = position;
			this.destino = destino;
			
			ruta(agent, velocidad, position, zone,destino);

		}

	}

	/**
	 * Movimiento Aleatorio 1.0.
	 */

	@Plan
	protected void ruta(IInternalAccess agent, int velocidad, Coordenada position, IComponentIdentifier zone,Coordenada destino) {
		// System.out.println("Entró en el trigger");
		// System.out.println("Plan movimiento aleatorio: "+velocidad+"
		// "+position.getX()+","+position.getY());
		// System.out.println(agent.getComponentIdentifier().getLocalName());

		try {
			Thread.sleep(velocidad * 1000);
		} catch (InterruptedException e) {

			System.out.println("error");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// System.out.println("voy a moverme");

		IFuture<IMapaService> zoneService = agent.getComponentFeature(IRequiredServicesFeature.class)
				.searchService(IMapaService.class, zone);

		// System.out.println(zoneService);
		// System.out.println("3");
		zoneService.addResultListener(new IResultListener<IMapaService>() {

			@Override
			public void resultAvailable(IMapaService result) {

				// System.out.println("resultado");

				// System.out.println(position.getX()+" -- "+position.getY());

				Coordenada nueva = destino;

				// System.out.println(getMyPosition().getX()+" -
				// "+getMyPosition().getY()+" to "+nueva.getX()+" -
				// "+nueva.getY());
				if (result.changePosition(nueva, agent.getComponentIdentifier())){
					setMyPosition(nueva);
                    
				}
					
			}

			@Override
			public void exceptionOccurred(Exception exception) {

			}

		});

	}


}
