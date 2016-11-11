package sisobeem.websocket.Session;

public class DeviceSessionHandler21 extends DeviceSessionHandlerAbstract{
	
	  private static DeviceSessionHandler21 handler;
    
            
      private DeviceSessionHandler21(){
    	  
      }
      
      /**
       * Aplicando el patron Silgenton;
       * @return 
       */
      public static DeviceSessionHandler21 getDeviceSessionHandler(){
          if(handler==null){
        	  handler = new DeviceSessionHandler21();
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
