/**
 * Created by Ricardo on 29/08/2016.
 */
/*Logica Config Simulacion */


var Simulacion  = function(lugar ,momento, duracion, intencidad, duracionSismo) {
    this.lugar = lugar;
    this.momento = momento;
    this.duracion = duracion;
    this.intencidad = intencidad;
    this.duracionSismo = duracionSismo;
};

var Edificio = function(id, personas, ubicacion, resistencia, pisos, salidas){
    this.id;
    this.personas =  personas;
    this.ubicacion = ubicacion;
    this.resistencia = resistencia;
    this.pisos = pisos;
    this.salidas = salidas;
};

var Persona = function(transeuntes , internos, edad , conocimiento, salud, liderazgo){
    this.transeuntes = transeuntes;
    this.edad = edad;
    this.conocimiento = conocimiento;
    this.salud = salud;
    this.liderazgo = liderazgo;
};

var AtencionEmergencia = function(tiempoRespuesta, personalBusquedaRescate , personalSalud,  PersonalSeguridad){
    this.tiempoRespuesta = tiempoRespuesta;
    this.personalBusquedaRescate = personalBusquedaRescate;
    this.personalSalud = personalSalud;
    this.PersonalSeguridad = PersonalSeguridad;
};

var EdificiosList = function(){
    this.edificiosList = [];
    this.index = 0;
};

var MarketList = function(){
    this.markerList = {};
};


EdificiosList.prototype.add = function(edificio){
    if(edificio instanceof Edificio){
        var index = this.edificiosList.length;
        edificio.id = index;
        this.edificiosList[index] = edificio;
    }
    else{
        console.error("Type Invalid: not is instance of Edificio.");
    }
};

EdificiosList.prototype.addUbicacion = function(latLng){
    this.edificiosList[this.index].ubicacion = latLng;
};

EdificiosList.prototype.next = function(){
    this.index ++;
};

EdificiosList.prototype.get = function(id){
    return this.edificiosList[id];
};

EdificiosList.prototype.reset = function(){
    this.edificiosList= [];
    this.index = 0;
};

MarketList.prototype.add = function(id, market){
    this.markerList[id] = market;
};

MarketList.prototype.get = function(id){
    return this.markerList[id];
};

MarketList.prototype.reset = function(){
    this.markerList = {};
};

Simulacion.prototype._set =  function setObject(object){
    for(key in this){
        if(object[key]){
            this[key] = object[key];
        }
    }
};

Edificio.prototype._set = function setObject(object){
    for(key in this){
        if(object[key]){
            this[key] = object[key];
        }
    }
};

Persona.prototype._set = function setObject(object){
    for(key in this){
        if(object[key]){
            this[key] = object[key];
        }
    }
};

AtencionEmergencia.prototype._set = function setObject(object){
    for(key in this){
        if(object[key]){
            this[key] = object[key];
        }
    }
};


/*
* funciones y procedimientos
* */
function queryElement(elemento , patron){
    var response = [];
    if(elemento.ELEMENT_NODE){
        var results = elemento.querySelectorAll(patron);
        for (i=0; i< results.length; i++){
            response[i] = results[i];
        }
    }
    else{
        console.error("invalid: Argument not is a ELEMENT_NODE");
    }
    return response;
}

/*
* recibe un array de elementos y genera un objeto de los campos sea por id o por name
* @param NodeList Array
* @return
**/
function getData(arrayElements){
    var objectId = {};
    var objectName ={};

    if(Array.isArray(arrayElements)){
        arrayElements.forEach(function(element){
            if(element.id){
                objectId[element.id] = getValueType(element) ;
            }
            if(element.name){
                objectName[element.name] = getValueType(element);
            }
        });
    }
    return {id: objectId, name: objectName};
}

function getValueType(element){
    try {
        switch (element.getAttribute("istype")){
            case 'int':
                return parseInt(element.value);
            case 'double':
                return parseFloat(element.value);
            case 'object':
                return JSON.parse(element.value);
            default:
                return element.value;
        }
    }
    catch(err) {
        console.error(err);
        return element.value;
    }
}

function getConfiguration(){
    var q1 = queryElement( $('#formSimulacionConfig')[0] , "input,select,textarea");
    var q2 = queryElement( $('#formConfAgentsPers')[0]       , "input,select,textarea");
    var q3 = queryElement( $('#formConfigAtenEmer')[0]   , "input,select,textarea");
    var edificios = edificiosList.edificiosList;

    var sim  = new Simulacion();
    var per  = new Persona();
    var eme = new AtencionEmergencia();
    sim._set(getData(q1).name);
    per._set(getData(q2).name);
    eme._set(getData(q3).name);
    return [sim,edificios,per,eme];
}
