package sisobeem.websocket.Session;

public class DeviceSessionHandler48 extends DeviceSessionHandlerAbstract{
	
	  private static DeviceSessionHandler48 handler;
    
            
      private DeviceSessionHandler48(){
    	  
      }
      
      /**
       * Aplicando el patron Silgenton;
       * @return 
       */
      public static DeviceSessionHandler48 getDeviceSessionHandler(){
          if(handler==null){
        	  handler = new DeviceSessionHandler48();
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
