package sisobeem.websocket.Session;

public class DeviceSessionHandler2 extends DeviceSessionHandlerAbstract {
	
	  private static DeviceSessionHandler2 handler;
            
      private DeviceSessionHandler2(){
    	  
      }
      
      /**
       * Aplicando el patron Silgenton;
       * @return 
       */
      public static DeviceSessionHandler2 getDeviceSessionHandler(){
          if(handler==null){
        	  handler = new DeviceSessionHandler2();
              return handler;
          }
         return handler;     
      }
      
  
      
}
