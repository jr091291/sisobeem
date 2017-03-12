package sisobeem.websocket.Session;

public class DeviceSessionHandlerZONE extends DeviceSessionHandlerAbstract{
	
	  private static DeviceSessionHandlerZONE handler;
    
            
      private DeviceSessionHandlerZONE(){
    	  
      }
      
      /**
       * Aplicando el patron Silgenton;
       * @return 
       */
      public static DeviceSessionHandlerZONE getDeviceSessionHandler(){
          if(handler==null){
        	  handler = new DeviceSessionHandlerZONE();
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
