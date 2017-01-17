package sisobeem.artifacts;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.helpers.Loader;
public class Log {
	
	private final static Logger log = Logger.getLogger(Log.class);
	
	  private static Log ObLog;
	  
	  private Log (){
		  URL url = Loader.getResource("log4j.properties");
		  
		  PropertyConfigurator.configure(url);
		//  Logger.addAppender(new FileAppender(new PatternLayout(),"prueba.log", false));
	  }
	  
	  /**
	   * Singelton
	   * @return
	   */
	  public static Log getLog(){
	          if(ObLog==null){
	        	  ObLog = new Log();
	              return ObLog;
	          }
	         return ObLog;     
	   }
	  	  
	  public void setWarn(String x){
		  log.warn(x);
	  }
	  
	  public void setError(String x){
		  log.error(x);
	  }
	  
	  public void setFatal(String x){
		  log.fatal(x);
	  }
	  
	  public void setInfo(String x){
		  log.info(x);
	  }
	  
	  public void setDebug(String x){
		  log.debug(x);
	  }
	   

}
