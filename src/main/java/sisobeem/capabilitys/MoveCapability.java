package sisobeem.capabilitys;

import java.util.ArrayList;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Capability;
import jadex.bdiv3.annotation.Goal;
import jadex.bdiv3.annotation.GoalParameter;
import jadex.bdiv3.annotation.Plan;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IInternalAccess;
import jadex.bridge.component.IExecutionFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Random;
import sisobeem.services.zoneServices.IMapaService;

@Capability
@RequiredServices({
	@RequiredService(name ="IMapaService",type=IMapaService.class)
})

public class MoveCapability {
   
	public MoveCapability()
	{
	
	}
	
	/**
	 * Meta movimiento Aleatorio
	 */
	@Goal
	public class Aleatorio
	{

		@GoalParameter
		IInternalAccess agent;
		
		@GoalParameter
		protected int velocidad;
		
		@GoalParameter
		protected Coordenada position;
		
		public Aleatorio(IInternalAccess agent,int velocidad, Coordenada position,IComponentIdentifier zone)
		{  
	//	  System.out.println(agent.getComponentIdentifier().getLocalName());
		 
      //    System.out.println("Entro a la capacidad Aleatoria"+velocidad);
       //   System.out.println(zone.toString());
		 	this.agent = agent;
            this.velocidad = velocidad;
			this.position = position;
		 	aleatorio(agent,velocidad, position,zone);
		}
		
	
	
	
	}
	

	/**
	 *  Movimiento Aleatorio 1.0.
	 */

	@Plan
	protected void aleatorio(IInternalAccess agent,int velocidad, Coordenada position, IComponentIdentifier zone)
	{  
		//System.out.println("Entró en el trigger");
	//	System.out.println("Plan movimiento aleatorio: "+velocidad+" "+position.getX()+","+position.getY());
	  //  System.out.println(agent.getComponentIdentifier().getLocalName());
		
	    agent.getComponentFeature(IExecutionFeature.class).waitForDelay(velocidad+000).get();
		
		
		IFuture<IMapaService> zoneService= agent.getComponentFeature(IRequiredServicesFeature.class).searchService(IMapaService.class,zone);
		  
			//	System.out.println("Tu Ubicacion es: "+c.getX()+" - "+c.getY()+" Agente: "+person.getName());
			   zoneService.addResultListener( new IResultListener<IMapaService>(){

				@Override
				public void resultAvailable(IMapaService result) {
                         
					     Coordenada nueva = getCoordenadaAleatoria(position);
					     System.out.println(getMyPosition().getX()+" - "+getMyPosition().getY()+" to "+nueva.getX()+" - "+nueva.getY());
						 if(result.changePosition(nueva, agent.getComponentIdentifier())) setMyPosition(nueva);
						 
						 
				}

				@Override
				public void exceptionOccurred(Exception exception) {
					
				}
				   
			   });
		
	}
	
	

	

	
	
	/**
	 * Método para elegir al nueva posicion
	 * @param posicionActual
	 * @return
	 */
	
	public Coordenada getCoordenadaAleatoria(Coordenada posicionActual){
		   
		    Coordenada nuevaPosicion = new Coordenada();
	        int aleatorio = Random.getIntRandom(1, 8);

	        switch(aleatorio){
	               case 1:
	            	     //Arriba
	                     nuevaPosicion.setX(posicionActual.getX());
	                     nuevaPosicion.setY(posicionActual.getY()+1);
	                     break;
	               case 2: 
	                     nuevaPosicion.setX(posicionActual.getX());
	                     nuevaPosicion.setY(posicionActual.getY()-1);
	                     break;
	               case 3: 
	            	     //Izquierda
	                     nuevaPosicion.setX(posicionActual.getX()-1);
	                     nuevaPosicion.setY(posicionActual.getY());
	                     break;
	               case 4:
	            	     //Derecha
	                     nuevaPosicion.setX(posicionActual.getX()+1);
	                     nuevaPosicion.setY(posicionActual.getY());
	                     break;
	               case 5:
	                     //Arriba derecha
	                     nuevaPosicion.setX(posicionActual.getX()+1);
	                     nuevaPosicion.setY(posicionActual.getY()+1);
	                     break;
	               case 6:
	                     //Arriba izquierda
	                     nuevaPosicion.setX(posicionActual.getX()-1);
	                     nuevaPosicion.setY(posicionActual.getY()+1);
	                     break;
 	               case 7:
	                     //Abajo derecha
	                     nuevaPosicion.setX(posicionActual.getX()+1);
	                     nuevaPosicion.setY(posicionActual.getY()-1);
	                     break;
	               case 8:
	                     //Abajo Izquierda
	                     nuevaPosicion.setX(posicionActual.getX()-1);
	                     nuevaPosicion.setY(posicionActual.getY()-1);
	                     break;
	               default:
	                     break;
	           }
	        
	        return nuevaPosicion;
		
	}
	
	
	/**
	 * Meta movimiento a sitio especifico
	 */
	
	
	@Goal
	public class Ruta
	{
		@GoalParameter
		protected ArrayList<Coordenada> ruta;
	
		public Ruta(ArrayList<Coordenada> r)
		{
			this.ruta = r;
		}
	}
	
	/**
	 *  Get the wordtable.
	*/
	@Belief
	public native Coordenada getMyPosition();
	
	
	@Belief
	public native void setMyPosition(Coordenada c);
	
	
	

}
