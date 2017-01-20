/**
 * Created by Ricardo on 31/08/2016.
 */
var BASE_URL = "ws://localhost:8080/sisobeem";

var socketSimulacion = new WebSocket(BASE_URL+ '/simulacion/config');

socketSimulacion.onmessage = function(event) {
    var mensaje = JSON.parse(event.data);
    //console.log(mensaje);
};

function sendConfig(config) {
    socketSimulacion.send(JSON.stringify(config));
}
