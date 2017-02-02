/**
 * Created by Ricardo on 31/08/2016.
 */
var BASE_URL = "ws://localhost:8080/sisobeem";

var socketZone = new WebSocket(BASE_URL + "/simulacion/jadex");
var socketZone2 = new WebSocket(BASE_URL + "/simulacion/jadex2" );
var socketZone3 = new WebSocket(BASE_URL + "/simulacion/jadex3" );
var socketZone4 = new WebSocket(BASE_URL + "/simulacion/jadex4" );
var socketZone5 = new WebSocket(BASE_URL + "/simulacion/jadex5" );
var socketZone6 = new WebSocket(BASE_URL + "/simulacion/jadex6");
var socketZone7 = new WebSocket(BASE_URL + "/simulacion/jadex7" );
var socketZone8 = new WebSocket(BASE_URL + "/simulacion/jadex8" );
var socketZone9 = new WebSocket(BASE_URL + "/simulacion/jadex9" );
var socketZone10 = new WebSocket(BASE_URL + "/simulacion/jadex10" );
var socketZone11 = new WebSocket(BASE_URL + "/simulacion/jadex11");
var socketZone12 = new WebSocket(BASE_URL + "/simulacion/jadex12" );
var socketZone13 = new WebSocket(BASE_URL + "/simulacion/jadex13" );
var socketZone14 = new WebSocket(BASE_URL + "/simulacion/jadex14" );
var socketZone15 = new WebSocket(BASE_URL + "/simulacion/jadex15" );
var socketZone16 = new WebSocket(BASE_URL + "/simulacion/jadex16");
var socketZone17 = new WebSocket(BASE_URL + "/simulacion/jadex17" );
var socketZone18 = new WebSocket(BASE_URL + "/simulacion/jadex18" );
var socketZone19 = new WebSocket(BASE_URL + "/simulacion/jadex19" );
var socketZone20 = new WebSocket(BASE_URL + "/simulacion/jadex20" );
var socketZone21 = new WebSocket(BASE_URL + "/simulacion/jadex21");
var socketZone22 = new WebSocket(BASE_URL + "/simulacion/jadex22" );
var socketZone23 = new WebSocket(BASE_URL + "/simulacion/jadex23" );
var socketZone24 = new WebSocket(BASE_URL + "/simulacion/jadex24" );
var socketZone25 = new WebSocket(BASE_URL + "/simulacion/jadex25" );
var socketZone26 = new WebSocket(BASE_URL + "/simulacion/jadex26");
var socketZone27 = new WebSocket(BASE_URL + "/simulacion/jadex27" );
var socketZone28 = new WebSocket(BASE_URL + "/simulacion/jadex28" );
var socketZone29 = new WebSocket(BASE_URL + "/simulacion/jadex29" );
var socketZone30 = new WebSocket(BASE_URL + "/simulacion/jadex30" );
var socketZone31 = new WebSocket(BASE_URL + "/simulacion/jadex31");
var socketZone32 = new WebSocket(BASE_URL + "/simulacion/jadex32" );
var socketZone33 = new WebSocket(BASE_URL + "/simulacion/jadex33" );
var socketZone34 = new WebSocket(BASE_URL + "/simulacion/jadex34" );
var socketZone35 = new WebSocket(BASE_URL + "/simulacion/jadex35" );
var socketZone36 = new WebSocket(BASE_URL + "/simulacion/jadex36");
var socketZone37 = new WebSocket(BASE_URL + "/simulacion/jadex37" );
var socketZone38 = new WebSocket(BASE_URL + "/simulacion/jadex38" );
var socketZone39 = new WebSocket(BASE_URL + "/simulacion/jadex39" );
var socketZone40 = new WebSocket(BASE_URL + "/simulacion/jadex40" );



var contador=0;
socketZone.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone2.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone3.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone4.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone5.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone6.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone7.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone8.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone9.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone10.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone11.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone12.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone13.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone14.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone15.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone16.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone17.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone18.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone19.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone20.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone21.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone22.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone23.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone24.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone25.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone26.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone27.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone28.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone29.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone30.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone31.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone32.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone33.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone34.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone35.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone36.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone37.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone38.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone39.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};

socketZone40.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};




function sendMensaje(mensaje) {
	socketZone.send(mensaje);	
}



socketZone.onclose = function(event){
	console.log("Cerro Socket");
};



/*Factory  de acciones*/
function factoryAction(data){
	
	switch (data.name) {
	case "move":
		var data = data.data;
		move(data.idAgent, data.moveTo, map.getMap, "/sisobeem/img/simulacion/agent_civil.png");
		contador++;
		console.log(contador);
		break;
	case "route":
		console.log("recibiendo mensaje de ruta");
		var data = data.data;
		var route = getRoute(data.origen, data.destino);
		sendMensaje(route);
	default:
		console.log("Accion " + data.name + " Desconocido" );
		break;
	}
}

var AgentsMarkets = {};
var Route = function Route(origen,destino, coordenadas){
	this.origen = origen;
	this.destino = destino;
	this.coordenadas = coordenadas;
}

/* Funciones de acciones*/
function move(idAgent, latLng, mapa, image){
	var market = AgentsMarkets[idAgent];
	
	if(market){
		if(market.position.lat()!= latLng.lat && market.position.lng() != latLng.lng){
			market.setMap(null);
			market.position =  new google.maps.LatLng(latLng.lat,latLng.lng);
			market.setMap(mapa);
		}
	}
	else{
			var marketNew = new google.maps.Marker({
		        position: latLng ,
		        map: mapa,
		        icon: image
		    });
			marketNew.setMap(mapa);
			AgentsMarkets[idAgent] = marketNew;
		
	}
	
}

function getRoute(origen, destino){
	 var directionsService = new google.maps.DirectionsService;
	 
	 directionsService.route(
		  {
		    origin: origen,
		    destination: destino,
		    travelMode: google.maps.TravelMode.WALKING,
		    provideRouteAlternatives: true
		  }, function(response, status) {
		    // Route the directions and pass the response to a function to create
		    // markers for each step.
		    if (status === google.maps.DirectionsStatus.OK) {
				return new Route(origen, destino, response.routes[0].overview_path);
		    } else {
		        return new Route(origen, destino, []);
		    }
	});
}




