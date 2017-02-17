package sisobeem.utilities;

public  class Random {
    
	
	/**
	 * Método que devuelve edades aleatorias
	 * @param cantidad  Numero de edades
	 * @param bajo      porcentaje edades bajas
	 * @param medio     porcentaje edades medias
	 * @param alto      porcentaje edades bajas
	 * @return int[] Edades
	 */
	public static int[] getEdadRandom(int cantidad, int bajo , int medio, int alto){
	    //Calculo individuos reales en cada porcentaje
		int cBajo = (cantidad*bajo)/100;
		int cMedio = (cantidad*medio)/100;
		
		int[] edades = new int[cantidad];
		
		//Selecionamos la edad en rango predicho
		for (int i = 0; i < edades.length; i++) {
			if(cBajo>0){
				edades[i] = getIntRandom(10, 25);
				cBajo--;
			}else if (cMedio>0){
				edades[i] = getIntRandom(25, 45);
				cMedio--;
			}else{
				edades[i] = getIntRandom(45, 90);
			}
		}
		
		return desordenarArray(edades);
		
	}
	
	/**
	 * Método que devuelve conocimiento aleatorio
	 * @param cantidad
	 * @param bajo
	 * @param medio
	 * @param alto
	 * @return
	 */
    public static double[] getConocimientoRandom(int cantidad, int bajo , int medio, int alto){
		
		int cBajo = (cantidad*bajo)/100;
		int cMedio = (cantidad*medio)/100;
		
		double[] conocimientos = new double[cantidad];
		
		for (int i = 0; i < conocimientos.length; i++) {
			if(cBajo>0){
				conocimientos[i] = 0;
				cBajo--;
			}else if (cMedio>0){
				conocimientos[i] = 1;
				cMedio--;
			}else{
				conocimientos[i] = 2;
			}
		}
		
		return desordenarArray(conocimientos);
		
	 }
    
    /**
     * Método que devuelve los valores aleatorios para la salud de los agentes
     * @param cantidad
     * @param porcentajeSaludBaja
     * @return saluds[]
     */
    public static int[] getSaludRamdon(int cantidad,float porcentajeSaludBaja){
    	
    	int cBajo = (int) ((cantidad*porcentajeSaludBaja)/100);
    	
    	int[] salud = new int[cantidad];
    	
		for (int i = 0; i < salud.length; i++) {
			if(cBajo>0){
				salud[i] = getIntRandom(0, 70);
				cBajo--;
			}else{
				salud[i] = getIntRandom(70, 100);
			}
		}
		
		return desordenarArray(salud);	
    }
    
    /**
    * Método que devuelve los valores aleatorios para la salud de los agentes
    * @param cantidad
    * @param porcentajeSaludBaja
    * @return saluds[]
    */
   public static double[] getLiderazgoRamdon(int cantidad,float porcentajeSaludBaja){
   	
   	int cBajo = (int) ((cantidad*porcentajeSaludBaja)/100);
   	
   	double[] salud = new double[cantidad];
   	
		for (int i = 0; i < salud.length; i++) {
			if(cBajo>0){
				salud[i] = getIntRandom(50,100);
				cBajo--;
			}else{
				salud[i] = getIntRandom(0,50);
			}
		}
		
		return desordenarArray(salud);
		
   	
   }
	
	
	public static int getIntRandom(int desde, int hasta){
		return (int) (Math.random()*(hasta-desde+1)+desde);
	}
	
	   public static double getDoubleRandom(double desde, double hasta){
			return  (Math.random()*(hasta-desde+1)+desde);
		}

	/**
	 * Método para desordenar un array Aleatoriamente
	 * @param ordenado
	 * @return
	 */
	public static int[] desordenarArray(int[] ordenado){
		
		int[] desordenado = new int[ordenado.length];
		Boolean[] usado = new Boolean[ordenado.length];

		for (int i=0; i < usado.length; i++){
			 usado[i]=false;
		}
		
		int index=0;
		for (int i=0; i < desordenado.length; i++){ 
			do {
				 index = (getIntRandom(0, ordenado.length-1));
			} while (usado[index]);
			
			 desordenado[i] = ordenado[index];
			 usado[index]=true;
	     }
		return desordenado;
	}
	
	public static double[] desordenarArray(double[] ordenado){
		
		double[] desordenado = new double[ordenado.length];
		Boolean[] usado = new Boolean[ordenado.length];

		for (int i=0; i < usado.length; i++){
			 usado[i]=false;
		}
		
		int index=0;
		for (int i=0; i < desordenado.length; i++){ 
			do {
				 index = (getIntRandom(0, ordenado.length-1));
			} while (usado[index]);
			
			 desordenado[i] = ordenado[index];
			 usado[index]=true;
	     }
		return desordenado;
	}

}