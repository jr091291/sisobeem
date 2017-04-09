/*
*variables globales
**/
var marketList = new MarketList();
var edificiosList = new EdificiosList();
var configuration = {};

/*
 * Inicializamos el mapa
 * */
map = new Map(google, "map", {
    center: {lat: 10.474245, lng:  -73.243633},
    zoom: 17,
    disableDefaultUI: true,
    clickableIcons: false,
    disableDoubleClickZoom: true,
    draggable: true,
    scrollwheel: true
});

/*
* inicializamos autocompletado de busqueda de lugares
* */
map.addAutocompleteBoxMapSearch(document.getElementById("searchPlace"),
    map.getMap, [] ,
    function(place, map){
        document.getElementById("lugarTerremoto").value = JSON.stringify(goToPlace(map,place, 20));
        edificiosList.reset();
        marketList.reset();
        $('#btnConfSimulacion').removeAttr('disabled');
    }
);

/*
* pintamos un lugar en el mapa
**/
function goToPlace(map, place, zoom){
    if (!place.geometry) {
        window.alert("Autocomplete's returned place contains no geometry");
        return null;
    }
    if (place.geometry.viewport) {
        map.fitBounds(place.geometry.viewport);
        document.getElementById("lugarTerremoto").value = JSON.stringify(map.getBounds());
    } else {
        var location = place.geometry.location;
        map.setCenter(location);
    }
    // Why 17? Because it looks good.
    (zoom) ? map.setZoom(parseInt(zoom)) : map.setZoom(17);
    return map.getBounds();
}

/*
* agregamos edificios a la lista de edificios
**/
function addEdificio(){
    var form = $("#formConfigEdi");
    var edificio = new Edificio();
    var datos =getData(queryElement(form[0], "input,select,textArea")).name;
    edificio._set(datos);
    edificiosList.add(edificio);
    form[0].reset();
    var numEdificios =edificiosList.edificiosList.length;
    $("#infoEdificios").html("Se ha Agregado un nuevo edificio, Existen "
            + numEdificios  +" edificios, y "
            + (numEdificios - edificiosList.index) + " por ubicar en el mapa.");
    $("#runBtn").attr('disabled', true);
}

/*
* crea un market con la imagen del edificio en el mapa
* */
function printEdificio(latLng, mapa){
    var list = edificiosList.edificiosList;
    if(edificiosList.index < list.length){
        var image = getImagen(list[edificiosList.index].pisos);
        latLng = {lat: latLng.lat(), lng: latLng.lng()};
        var marker = new google.maps.Marker({
            position: latLng ,
            map: mapa,
            icon: image
        });
        marketList.add(edificiosList.index, marker);
        edificiosList.addUbicacion(latLng);
        edificiosList.next();
    }
    else{
    	configuration = getConfiguration();
        if(confirm("¿Desea Guardar Esta Configuración?")){
        	saveConfiguration();
        };
        
        $("#runBtn").removeAttr('disabled');
    }
}

/*
* Retorna la imagen del edificio
* */
function getImagen(pisos){
    var image ="";
    if(pisos <= 2){
        image  = "/sisobeem/img/simulacion/edificio_pequeno.png";
    }
    else if(pisos<= 6){
        image  = "/sisobeem/img/simulacion/edificio_mediano.png";
    }

    else if(pisos<= 12){
        image  = "/sisobeem/img/simulacion/edificio_grande.png";
    }
    else{
        image  = "/sisobeem/img/simulacion/edificio_gigante.png";
    }
    return image
}

map.onclick(function(e, map){
    printEdificio(e.latLng, map);
});