package sisobeem.websocket.Session;

public class DeviceSessionHandler47 extends DeviceSessionHandlerAbstract{
	
	  private static DeviceSessionHandler47 handler;
    
            
      private DeviceSessionHandler47(){
    	  
      }
      
      /**
       * Aplicando el patron Silgenton;
       * @return 
       */
      public static DeviceSessionHandler47 getDeviceSessionHandler(){
          if(handler==null){
        	  handler = new DeviceSessionHandler47();
              return handler;
          }
         return handler;     
      }
      
   
      /**
      public List<Device> getDevices() {
          return new ArrayList<>(devices);
      }

      public void addDevice(Device device) {
      }

      public void removeDevice(int id) {
      }

      public void toggleDevice(int id) {
      }

      private Device getDeviceById(int id) {
          return null;
      }

      private JsonObject createAddMessage(Device device) {
          return null;
      }
      
      public void sendToAllConnectedSessions(JsonObject message) {
      }
   */

      
      
      
      
      
}
