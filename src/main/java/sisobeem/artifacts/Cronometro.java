package sisobeem.artifacts;

public class Cronometro  {
	
	private Thread hilo;
	private boolean cronometroActivo;


	private Integer  segundos = 0, milesimas = 0;
	
	public Cronometro() {
		// TODO Auto-generated constructor stub
	}
	
    /**
     * Método que incia el Cronometro
     */
    public void iniciarCronometro() {
        cronometroActivo = true;
        hilo = new Thread(){
        	@Override
        	public void run() {
        		super.run();
        		
        		  
           	            //Mientras cronometroActivo sea verdadero entonces seguira
        	            //aumentando el tiempo
        	            while( cronometroActivo )
        	            {
        	                try {
								Thread.sleep( 4 );
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
        	                //Incrementamos 4 milesimas de segundo
        	                milesimas += 4;
        	                 
        	                //Cuando llega a 1000 osea 1 segundo aumenta 1 segundo
        	                //y las milesimas de segundo de nuevo a 0
        	                if( milesimas == 1000 )
        	                {
        	                    milesimas = 0;
        	                    segundos += 1;
        	                    
        	                  //  System.out.println(segundos);
        	                   
        	                    //Si los segundos llegan a 60 entonces aumenta 1 los minutos
        	                    //y los segundos vuelven a 0
        	          
        	                }

        	            }	
        	}
        };
        hilo.start();
    }
    
    /**
     * Método para detener el conometro
     */
    @SuppressWarnings("deprecation")
	public void pararCronometro(){
        cronometroActivo = false;
        hilo.stop();
    }
    
    public void restablecer(){
    	pararCronometro();
    	segundos = 0; milesimas = 0;
    }

     
    /**
     * Metodos accesores
     */
	 

	public Integer getSegundos() {
		return segundos;
	}

	public Integer getMilesimas() {
		return milesimas;
	}
}