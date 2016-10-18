/**
 * Created by Ricardo on 31/08/2016.
 */
var BASE_URL = "ws://localhost:8080/sisobeem";

var socketSimulacion = new WebSocket(BASE_URL+ '/simulacion/config');

socketSimulacion.onmessage = function(event) {
    var mensaje = JSON.parse(event.data);
    console.log(mensaje);
};

function sendConfig(config) {
    socketSimulacion.send(JSON.stringify(config));
}

/*
 * ejecucion de la simulacion
 * */

$('#runBtn')[0].onclick = function(){
    var config = getConfiguration();
    socketSimulacion.send(
        config.SimulationConfig,
        config.PersonsConfig,
        config.EdificesConfig,
        config.EmergencyConfig
    );
};

function formatString(json){
	result = "";
	json = JSON.stringify(json);
	for(i = 0 ; i < json.length ; i++ ){
		if(json[i] == "'" || json[i] == '"'){
			result+= "\\\"";
			continue;
		}
		else{
			result += json[i];
		}
		
	}
	return result;
}