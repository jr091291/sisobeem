/**
 * Created by Ricardo on 31/08/2016.
 */
var BASE_URL = "ws://localhost:8080/sisobeem/agent/zone";

var socketZone = new WebSocket(BASE_URL);

socketZone.onmessage = function(event) {
    var mensaje = event;
    console.log(mensaje);
};

function sendMensaje(mensaje) {
	socketZone.send("hola");
}
