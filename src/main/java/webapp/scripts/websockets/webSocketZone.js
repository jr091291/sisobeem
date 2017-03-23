/**
 * Created by Ricardo on 31/08/2016.
 */
var BASE_URL = "ws://localhost:8080/sisobeem";
var intervalSismo = null;

var temblor = false;

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
var socketZone41 = new WebSocket(BASE_URL + "/simulacion/jadex41" );
var socketZone42 = new WebSocket(BASE_URL + "/simulacion/jadex42" );
var socketZone43 = new WebSocket(BASE_URL + "/simulacion/jadex43" );
var socketZone44 = new WebSocket(BASE_URL + "/simulacion/jadex44" );
var socketZone45 = new WebSocket(BASE_URL + "/simulacion/jadex45" );
var socketZone46 = new WebSocket(BASE_URL + "/simulacion/jadex46" );
var socketZone47 = new WebSocket(BASE_URL + "/simulacion/jadex47" );
var socketZone48 = new WebSocket(BASE_URL + "/simulacion/jadex48" );
var socketZone49 = new WebSocket(BASE_URL + "/simulacion/jadex49" );
var socketZone50 = new WebSocket(BASE_URL + "/simulacion/jadex50" );
var socketZone51 = new WebSocket(BASE_URL + "/simulacion/jadex51" );
var socketZone52 = new WebSocket(BASE_URL + "/simulacion/jadex52" );
var socketZone53 = new WebSocket(BASE_URL + "/simulacion/jadex53" );
var socketZone54 = new WebSocket(BASE_URL + "/simulacion/jadex54" );
var socketZone55 = new WebSocket(BASE_URL + "/simulacion/jadex55" );
var socketZone56 = new WebSocket(BASE_URL + "/simulacion/jadex56" );
var socketZone57 = new WebSocket(BASE_URL + "/simulacion/jadex57" );
var socketZone58 = new WebSocket(BASE_URL + "/simulacion/jadex58" );
var socketZone59 = new WebSocket(BASE_URL + "/simulacion/jadex59" );
var socketZone60 = new WebSocket(BASE_URL + "/simulacion/jadex60" );
var socketZoneZONE = new WebSocket(BASE_URL + "/simulacion/jadexZONE");

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


socketZone41.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone42.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone43.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone44.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone45.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone46.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone47.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone48.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone49.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone50.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone51.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone52.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone53.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone54.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone55.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone56.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone57.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone58.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone59.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};


socketZone60.onmessage = function(event) {
	factoryAction(JSON.parse(event.data));
};




function sendMensaje(mensaje) {
	socketZoneZONE.send(mensaje);	
}

function temblando(){
	$('#map').animateCss('shake');
}

function endTemblor(){
	clearInterval(intervalSismo);
}

socketZone.onclose = function(event){
	console.log("Cerro Socket");
};


/*Factory  de acciones*/
function factoryAction(data){
	
	switch (data.name) {
	case "move":
		var data = data.data;
		move(data.idAgent, data.moveTo, map.getMap, "/sisobeem/img/simulacion/caminando.png","/sisobeem/img/simulacion/"+data.Tipo+".png");
		break;
		
	case "punto":
		var imagen = "";
		data = data.data;
		if(data.Tipo == "seguro"){
			imagen = "/sisobeem/img/simulacion/seguro.png";
		}
		else{
			imagen = "/sisobeem/img/simulacion/inseguro.png";
		}
		if(imagen){
			var marketNew = new google.maps.Marker({
		        position: data.posicion ,
		        map: map.getMap,
		        icon: imagen
		    });
			marketNew.setMap(map.getMap);
		}
		break;
	
	case "estadistica":
		data = data.data;
		if(data.idAgent =="Zone"){
			
		}
		else{
		}
		break;
	
	case "derrumbe":
		var agent = data.data.idAgent;
		var arrayAgent = agent.split("EdificeAgent");
		var _market = null;
		if(arrayAgent.length>0 && arrayAgent.length > 1 ){
			if(arrayAgent[1]==""){
				_market = marketList.get(arrayAgent[0]);
			}
			else{
				_market = marketList.get(arrayAgent[1]);
			} 
		}
		if(_market){
			var icon = "/sisobeem/img/simulacion/edificio_derrumbado.png";
			var icon2 ="/sisobeem/img/simulacion/edificio_averiado.png";
			
			if(_market.icon != icon && _market.icon != icon2){
				changeIcon(_market,icon2, map.getMap);
		
				setTimeout(() => {
					changeIcon(_market, icon , map.getMap);
				}, 2000);
			}
		}
		break;
		
	case "muerto":
		var market = AgentsMarkets[data.data.idAgent];
		market.block = false;
		changeIcon(market,"/sisobeem/img/simulacion/muerto.png", map.getMap());
		break;	
	
	case "route":
		console.log("recibiendo mensaje de ruta");
		var data = data.data;
		var route = getRoute(data.agent, data.origen, data.destino, function(route){
			sendMensaje(JSON.stringify(route));
		});
		
	case "mensaje":
		data = data.data;
		
		if(!data.Tipo){
			if(data.estado){
			   if(temblor==false){
				   temblor= true;
					intervalSismo = setInterval(() => {
						temblando();
					}, 1000);;
			   }else{
				   
			   }
			}
			else{
				
				endTemblor();
				
			}
		}
		else{
			setMensage(data);
		}
		break;
	default:
		console.log("Accion " + data.name + " Desconocido" );
		break;
	}
}

function setMensage(data){
	switch (data.Tipo) {
		case "panico":
			mensaje(data, "/sisobeem/img/simulacion/mensaje_panico.png");
		break;
	
		case "hostil":
			mensaje(data, "/sisobeem/img/simulacion/mensaje_hostil.png");
		break;
		
		case "ayuda":
			mensaje(data, "/sisobeem/img/simulacion/mensaje_ayuda.png");
		break;
		
		case "frustracion":
			mensaje(data, "/sisobeem/img/simulacion/mensaje_frustacion.png");
		break;
		
		/*
		case "motivacion":
			mensaje(data, "/sisobeem/img/simulacion/mensaje_motivacion.png");
		break;
		
		case "calma":
			mensaje(data, "/sisobeem/img/simulacion/mensaje_calma.png");
		break;
		
		case "confianza":
			mensaje(data, "/sisobeem/img/simulacion/mensaje_confianza.png");
		break;
		
		case "resguardo":
			mensaje(data, "/sisobeem/img/simulacion/mensaje_resguardo.png");
		break;
		
		case "auxilios":
			mensaje(data, "/sisobeem/img/simulacion/mensaje_auxilios.png");
		break;
		*/
	}
}

function mensaje(data, icono){
	var market = AgentsMarkets[data.idAgent];
	if (market){
		market.block = true;

		if(market.icon != icono){
			market.block = false;
		}
		
		var icon = market.icon;
		changeIcon(market, icono, map.getMap);
		
		setTimeout(() => {
			market.block = false;
			changeIcon(market, icon, map.getMap);
		}, 2000);
	}
}

var AgentsMarkets = {};

var Route = function Route(agent, origen,destino, coordenadas){
	this.agent = agent;
	this.origen = origen;
	this.destino = destino;
	this.coordenadas = coordenadas;
}
var _marketInfoEsta = null;
function infoEstadisticaEdificio(data){
	var arrayAgent = data.idAgent.split("EdificeAgent");
	var _marketInfoEsta = null;
	if(arrayAgent.length>0 && arrayAgent.length > 1 ){
		if(arrayAgent[1]==""){
			_marketInfoEsta = marketList.get(arrayAgent[0]);
		}
		else{
			_marketInfoEsta = marketList.get(arrayAgent[1]);
		}
		if(_marketInfoEsta){
			var aux = "En Pie";
			if(data.derrumbado){
				aux = "Edificio Derrumbado";
		    }
			var infowindow = new google.maps.InfoWindow({
			    content: '<div id="headWindowEdifice">'+ aux +'</div>'+
			    '<div id="bodyWindowEdifice"> '+
				'  <div><strong>Mensajes De Ayuda: </strong>' + data.MsgAyuda+'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' + data.MsgDeCalma+'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' +  data.MsgDeConfianza +'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' +  data.MsgFrsutracion +'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' + data.MsgHostilidad +'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' + data.MsgMotivacion +'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' + data.MsgPanico +'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' + data.MsgPrimerosAux +'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' + data.MsgResguardo +'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' + data.PersonasAtrapadas +'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' +  data.PersonasMuertas +'<div>'+
				'  <div><strong>Mensajes De Ayuda: </strong>' +   data.Suicidios +'<div>'+
				'</div>',
			    maxWidth: 200
			  });
			
			_marketInfoEsta.addListener('click', function() {
			    infowindow.open(map.getMap, _marketInfoEsta);
			  });
		}
	}
}

/* Funciones de acciones*/
function move(idAgent, latLng, mapa, image, iconFinal){
	var market = AgentsMarkets[idAgent];
	
	if(market){
		if(market.position.lat()!= latLng.lat && market.position.lng() != latLng.lng){
			market.setMap(null);
			market.position =  new google.maps.LatLng(latLng.lat,latLng.lng);
			market.icon = image;
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
			market = marketNew;
		
	}
	setTimeout(() => {
		if(market.icon != iconFinal){
			market.setMap(null);
			market.icon = iconFinal;
			market.setMap(mapa);
		}
	}, 1800);	
}

function changeIcon(market, routeIcon, mapa){
	if(market && !market.block){
		market.setMap(null);
		market.icon =  routeIcon;
		market.setMap(mapa);	
	}
}
	
function getRoute(agent, origen, destino, callback){
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
		    	callback(new Route(agent ,origen, destino, response.routes[0].overview_path));
		    } else {
		        callback( new Route(agent ,origen, destino, []));
		    }
	});
}



