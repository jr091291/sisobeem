/**
 * Created by Ricardo on 27/08/2016.
 */
/*
 * Configuramos plugin jquery con animate.css
 **/


$.fn.extend({
    animateCss: function (animationName) {
        var animationEnd = 'webkitAnimationEnd mozAnimationEnd MSAnimationEnd oanimationend animationend';
        $(this).addClass('animated ' + animationName).one(animationEnd, function() {
            $(this).removeClass('animated ' + animationName);
        });
    }
});

/*
* tooltips
* */
$(function () {
    $('[data-toggle="tooltip"]').tooltip()
    loadConfig("confPred");
});

function loadConfig(selectIdDiv){
	var conf = localStorage.sisobeemConfiguration;
	if(conf){
		conf= JSON.parse(conf);
	}
	
	for(name in conf ){
	      $("#"+selectIdDiv).append($("<option></option>").val(name).html(name));
	}
}
/*
* ajusta el alto del mapa
* */
document.getElementById("map").style.height = window.innerHeight - 80+ "px";

/**
 * eventos manejadores de los modales
 * */

function endConfig(){
    showModalConfig();
    alert('Por favor agregue los edificio , haciendo Click en el mapa');
}

function showModalConfig(id){
    if(!id){
        var modals= $('.modal').modal("hide");
        return;
    }
    var modals= $('.modal').toArray();
    modals.forEach(function(modal){
        var _id =modal.id;
        modal = $('#'+ _id);
        if(_id !== id){
                modal.modal('hide')
        }
        else {
            modal.modal();
        }
    });
}

/*
* inicializacion range
* */
$("#porcAdultos").slider({
    formatter: function(value) {
        var baja  = value[0];
        var media = value[1] - value[0];
        var alta  = 100 - value[1];
        return "NIÑOS: " +  baja +" % ; ADULTOS: "+ media+ " % ; MAYORES: "+ alta+ " %"
    }
});

$("#porcConoZona").slider({
    formatter: function(value) {
        var baja  = value[0];
        var media = value[1] - value[0];
        var alta  = 100 - value[1];
        return "TRANCEUNTE: " +  baja +" % ; SEUDOTRANCEUNTE: "+ media+ " % ; RESIDENTE: "+ alta+ " %"
    }
});

$("#porcSalud").slider({
    formatter: function(value) {
        return 'DISCAPACITADOS: ' + value + " %";
    }
});

$("#porcLider").slider({
    formatter: function(value) {
        return 'LIDERES: ' + value + " %";
    }
});

$('#porcAtenEmer').slider({
    formatter: function(value) {
        return  value + "%";
    }
});

$('#sismoresistencia').slider({
    formatter: function(value) {
        return  value + "%";
    }
});

/*
 * INICIALIZACION VALIDACION DE FORMULARIOS
 * */
$("#formSimulacionConfig").parsley().on("field:validated", function(e) {
    if(e.isValid()){
        $('#formConfSimbtn').removeAttr('disabled');
        $('#btnConAgents').removeAttr('disabled');
    }
    else{
        $('#formConfSimbtn').attr('disabled',true);
        $('#btnConAgents').attr('disabled', true);
    }
});

$("#formConfAgentsPers").parsley().on("field:validated", function(e) {
    if(e.isValid()){
        $('#confAgentsbtn').removeAttr('disabled');
        $('#btnConAgentsEmer').removeAttr('disabled');
    }
    else{
        $('#confAgentsbtn').attr("disabled", true);
        $('#btnConAgentsEmer').attr("disabled", true);
    }
});

$("#formConfigAtenEmer").parsley().on("field:validated", function(e) {
    if (e.isValid()) {
        $('#confAgentsEmerbtn').removeAttr('disabled');
    }
    else {
        $('#confAgentsEmerbtn').attr('disabled', true);
    }
});

$("#formConfigEdi").parsley().on("field:validated", function(e) {
    if (e.isValid()) {
        $('#addEdificiobtn').removeAttr('disabled');
        $('#confAgentsEdibtn').removeAttr('disabled');
        $('#btnAddEdificio').removeAttr('disabled');
        
    }
    else {
        $('#addEdificiobtn').attr('disabled', true);
        $('#confAgentsEdibtn').attr('disabled',true);
        $('#btnAddEdificio').attr('disabled', true);
    }
});

new ParsleyConfigEs(parsley);

/*
* Manejador asistente virtual
* */
var tour = introJs();
tour.setOption('tooltipPosition', 'auto');
tour.setOption('positionPrecedence', ['left', 'right', 'bottom', 'top']);

initTour(tour);
helpActive();

$('#asistenteVirtual')[0].onclick = function(e){
    toggleTour(tour);
};

function  toggleTour(tour){
    if(localStorage.getItem('tour') == 'active'){
        localStorage.tour = 'disable';
        $('#asistenteVirtual')[0].style.color = 'red';
    }
    else {
        localStorage.setItem('tour','active');
        $('#asistenteVirtual')[0].style.color = '#00ff00';
    }
}

function initTour(){
    var state =localStorage.getItem('tour');
    if(state == undefined || state == 'active'){
        localStorage.setItem('tour','active');
        $('#asistenteVirtual')[0].style.color = '#00ff00'
        tour.start();
    }
    else {
        $('#asistenteVirtual')[0].style.color = 'red';
    }
}

function helpActive (){
    var state =localStorage.getItem('tour');
    if(state === 'active'){
        /*
         * INICIALIZACION AYUDA CAMPOS
         * */
        $("#formSimulacionConfig").progression({
            tooltipPosition: 'right',
            tooltipOffset: '50',
            showProgressBar: true,
            showHelper: true,
            tooltipFontSize: '14',
            tooltipFontColor: 'fff',
            progressBarBackground: 'fff',
            progressBarColor: '6EA5E1',
            tooltipBackgroundColor:'a2cbfa',
            tooltipPadding: '10',
            tooltipAnimate: true
        });

        $("#formConfAgentsPers").progression({
            tooltipWidth: '200',
            tooltipPosition: 'right',
            tooltipOffset: '20'
        });

        $("#formConfigAtenEmer").progression({
            tooltipWidth: '200',
            tooltipPosition: 'right',
            tooltipOffset: '50'
        });

        $("#formConfigEdi").progression({
            tooltipWidth: '200',
            tooltipPosition: 'right',
            tooltipOffset: '50'
        });
    }
}

$(document).ready(function(){

	/*MANEJADORES DE EVENTOS*/
	document.getElementById("runBtn").onclick = function (){
		sendConfig(configuration);s
		$('#runBtn').attr('disabled', true);
	}
	
});