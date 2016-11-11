package sisobeem.websocket.Session;

public class DeviceSessionHandler3 extends DeviceSessionHandlerAbstract {
	
	  private static DeviceSessionHandler3 handler;
	  
      private DeviceSessionHandler3(){
    	  
      }
      
      /**
       * Aplicando el patron Silgenton;
       * @return 
       */
      public static DeviceSessionHandler3 getDeviceSessionHandler(){
          if(handler==null){
        	  handler = new DeviceSessionHandler3();
              return handler;
          }
         return handler;     
      }
      

      
}
