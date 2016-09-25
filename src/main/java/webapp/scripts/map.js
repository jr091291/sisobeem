
var  Map = (function(google, idMap, config){
    "use strict";
    var map = _create(idMap, config);

    function _create(idMap, config){
        return new google.maps.Map(document.getElementById(idMap), config);
    }

    function _geolocation(){
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                var pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };
                var infoWindow = new google.maps.InfoWindow({map: map});
                infoWindow.setPosition(pos);
                infoWindow.setContent('Location found.');
                map.setCenter(pos);
            }, function() {
                handleLocationError(true, infoWindow, map.getCenter());
            });
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter());
        }
    }


    /*
    * @arg idDiv  identificador input type[text, search]
    * @arg types ['address', 'establishment','geocode']
    * */
    function _autoCompleteSearch(input , map , typeSearch, callback){
        if(input.ELEMENT_NODE && input.type.toUpperCase() == 'TEXT'){
            var autocomplete = new google.maps.places.Autocomplete(input);
            autocomplete.bindTo('bounds', map);
            autocomplete.setTypes((typeSearch) ? typeSearch : []);

            autocomplete.addListener('place_changed', function() {
                var place = autocomplete.getPlace();
               if(typeof  callback == "function"){
                   callback(place, map, typeSearch);
               }
            });
        }
        else{
            console.error("The Element not is a DOM ELEMENT or not is of type [text, search] ");
        }
    }

    function handleLocationError(browserHasGeolocation, infoWindow, pos) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
            'Error: The Geolocation service failed.' :
            'Error: Your browser doesn\'t support geolocation.');
    }

    return{
        getMap:map,
        create:function(idMap, config){
            _create(idMap, config)
        },
        geolocation: function(){
            _geolocation()
        },
        addAutocompleteBoxMapSearch : function(idDiv, map, typesSearch, callback){
            _autoCompleteSearch(idDiv, map, typesSearch, callback);
        },
        onclick: function(callback){
            map.addListener('click',function(e){
                callback(e, map);
            });
        },
        setMarker: function(config){
            return new google.maps.Marker(config);
        }
    }
});

