package sisobeem.websocket.Session;

public class DeviceSessionHandler27 extends DeviceSessionHandlerAbstract{
	
	  private static DeviceSessionHandler27 handler;
    
            
      private DeviceSessionHandler27(){
    	  
      }
      
      /**
       * Aplicando el patron Silgenton;
       * @return 
       */
      public static DeviceSessionHandler27 getDeviceSessionHandler(){
          if(handler==null){
        	  handler = new DeviceSessionHandler27();
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
