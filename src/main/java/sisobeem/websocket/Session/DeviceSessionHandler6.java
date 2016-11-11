package sisobeem.websocket.Session;

public class DeviceSessionHandler6 extends DeviceSessionHandlerAbstract{
	
	  private static DeviceSessionHandler6 handler;
    
            
      private DeviceSessionHandler6(){
    	  
      }
      
      /**
       * Aplicando el patron Silgenton;
       * @return 
       */
      public static DeviceSessionHandler6 getDeviceSessionHandler(){
          if(handler==null){
        	  handler = new DeviceSessionHandler6();
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
