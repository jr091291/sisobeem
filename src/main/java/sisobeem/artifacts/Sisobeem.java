package sisobeem.artifacts;

public class Sisobeem {
	public static String parseString(int[] array){
		String data = "";
		for(int i : array){
			if(i==0 || i== array.length){
				data = data + array[i];
			}
			else{
			    data = data +","+ array[i];
			}
		}
		return data;
	}
	
	public static String[] parseArray(String data, String separator){
		String[] array = data.split(separator);
		return array;
	}
	
	public static int[] parseInt(String[] array){
		int result[] = new int[array.length];
		for(int i = 0; i< array.length; i++){
			result[i]= Integer.parseInt(array[i]);
		}
		return result;
	}
	
}
