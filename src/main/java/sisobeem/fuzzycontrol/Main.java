package sisobeem.fuzzycontrol;

import java.util.ArrayList;
import java.util.Arrays;

import com.engineer.fuzzylogiccontroller.abstracts.core.VariableEntrada;
import com.engineer.fuzzylogiccontroller.abstracts.core.VariableSalida;
import com.engineer.fuzzylogiccontroller.artifacts.Punto;
import com.engineer.fuzzylogiccontroller.artifacts.linguisticLabels.EtiquetaTrapezoidal;
import com.engineer.fuzzylogiccontroller.artifacts.linguisticLabels.EtiquetaTriangular;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		VariableEntrada edad = new VariableEntrada("edad", new Punto(4, 90), "años");
		EtiquetaTrapezoidal joven = new EtiquetaTrapezoidal(new Punto(4, 12), "niño", new Punto(4, 18));
		EtiquetaTrapezoidal adulto = new EtiquetaTrapezoidal(new Punto(30, 50), "Adulto", new Punto(14, 65));
		EtiquetaTrapezoidal anciano = new EtiquetaTrapezoidal(new Punto(80, 90), "anciano", new Punto(50, 90));
		edad.setEtiquetas(new ArrayList<>(Arrays.asList(joven,adulto, anciano)));
		
		/*	
		VariableEntrada phyco = new VariableEntrada("Psicologia", new Punto(0, 100), "Puntos");
		EtiquetaTriangular histerico = new EtiquetaTriangular(0, "Histérico", new Punto(0, 50));
		EtiquetaTriangular depresivo = new EtiquetaTriangular(50, "Depresivo", new Punto(0, 100));
		EtiquetaTriangular obsesivo = new EtiquetaTriangular(100, "Obsesivo", new Punto(50, 100));
		phyco.setEtiquetas(new ArrayList<>(Arrays.asList(histerico,depresivo, obsesivo)));
		*/
		VariableEntrada salud = new VariableEntrada("salud", new Punto(0, 100), "%");
		EtiquetaTrapezoidal herido = new EtiquetaTrapezoidal(new Punto(0, 10), "herido", new Punto(0, 40));
		EtiquetaTrapezoidal estable = new EtiquetaTrapezoidal(new Punto(40, 60), "estable", new Punto(20, 80));
		EtiquetaTrapezoidal saludable = new EtiquetaTrapezoidal(new Punto(90, 100), "saludable", new Punto(60, 100));
		salud.setEtiquetas(new ArrayList<>(Arrays.asList(herido,estable, saludable)));
		
		VariableEntrada formacion = new VariableEntrada("Nivel Formacion", new Punto(0, 100), "%");
		EtiquetaTriangular formacionBajo = new EtiquetaTriangular(0, "bajo", new Punto(0, 50));
		EtiquetaTriangular formacionMedia = new EtiquetaTriangular(50, "medio", new Punto(0, 100));
		EtiquetaTriangular formacionAlta = new EtiquetaTriangular(100, "alto", new Punto(50, 100));
		formacion.setEtiquetas(new ArrayList<>(Arrays.asList(formacionBajo,formacionMedia, formacionAlta)));
		
		VariableEntrada liderazgo = new VariableEntrada("Nivel de Liderazgo", new Punto(0, 100), "%");
		EtiquetaTrapezoidal liderazgoPoco = new EtiquetaTrapezoidal(new Punto(0, 10), "Poco", new Punto(0, 40));
		EtiquetaTrapezoidal liderazgoNormal = new EtiquetaTrapezoidal(new Punto(45, 55), "Normal", new Punto(30, 75));
		EtiquetaTrapezoidal liderazgoAlto = new EtiquetaTrapezoidal(new Punto(90, 100), "Alto", new Punto(60, 100));
		liderazgo.setEtiquetas(new ArrayList<>(Arrays.asList(liderazgoPoco,liderazgoNormal, liderazgoAlto)));
		
		VariableEntrada conocimientoZona = new VariableEntrada("Conocimiento De La Zona", new Punto(0, 100), "%");
		EtiquetaTriangular conocimientoBajo = new EtiquetaTriangular(0, "bajo", new Punto(0, 50));
		EtiquetaTriangular conocimientoMedio = new EtiquetaTriangular(50, "medio", new Punto(0, 100));
		EtiquetaTriangular conocimientoAlto = new EtiquetaTriangular(100, "alto", new Punto(50, 100));
		conocimientoZona.setEtiquetas(new ArrayList<>(Arrays.asList(conocimientoBajo,conocimientoMedio, conocimientoAlto)));
		
		VariableEntrada riesgo = new VariableEntrada("Nivel De Riesgo", new Punto(0, 100), "%");
		EtiquetaTrapezoidal riesgoModerado = new EtiquetaTrapezoidal(new Punto(0, 5), "moderado", new Punto(0, 40));
		EtiquetaTrapezoidal riesgoImportante = new EtiquetaTrapezoidal(new Punto(40, 60), "importante", new Punto(20, 80));
		EtiquetaTrapezoidal riesgoIntorelable = new EtiquetaTrapezoidal(new Punto(95, 100), "intorelable", new Punto(60, 100));
		riesgo.setEtiquetas(new ArrayList<>(Arrays.asList(riesgoModerado,riesgoImportante, riesgoIntorelable)));
		
		VariableSalida confianza = new VariableSalida("Nivel De Confianza", new Punto(0, 100), "%");
		EtiquetaTriangular confianzaBaja = new EtiquetaTriangular(0, "Bajo", new Punto(0, 50));
		EtiquetaTriangular confianzaMedia = new EtiquetaTriangular(50, "Medio", new Punto(0, 100));
		EtiquetaTriangular confianzaAlta = new EtiquetaTriangular(100, "Alto", new Punto(50, 100));
		confianza.setEtiquetas(new ArrayList<>(Arrays.asList(confianzaBaja,confianzaMedia, confianzaAlta)));
		
		VariableSalida miedo = new VariableSalida("Nivel De Miedo", new Punto(0, 100), "%");
		EtiquetaTriangular miedoBajo = new EtiquetaTriangular(0, "Poco", new Punto(0, 50));
		EtiquetaTriangular miedoMedio = new EtiquetaTriangular(50, "Normal", new Punto(0, 100));
		EtiquetaTriangular miedoAlto = new EtiquetaTriangular(100, "Mucho", new Punto(50, 100));
		miedo.setEtiquetas(new ArrayList<>(Arrays.asList(miedoBajo,miedoMedio, miedoAlto)));
		
		VariableSalida tristeza = new VariableSalida("Nivel De Tristeza", new Punto(0, 100), "%");
		EtiquetaTriangular tristezaBaja = new EtiquetaTriangular(0, "Baja", new Punto(0, 50));
		EtiquetaTriangular tristezaNormal = new EtiquetaTriangular(50, "Normal", new Punto(0, 100));
		EtiquetaTriangular tristezaAlta = new EtiquetaTriangular(100, "Alta", new Punto(50, 100));
		tristeza.setEtiquetas(new ArrayList<>(Arrays.asList(tristezaBaja,tristezaNormal, tristezaAlta)));
		
		VariableSalida enojo = new VariableSalida("Nivel De Enojo", new Punto(0, 100), "%");
		EtiquetaTriangular enojoBaja = new EtiquetaTriangular(0, "Bajo", new Punto(0, 50));
		EtiquetaTriangular enojoNormal = new EtiquetaTriangular(50, "Medio", new Punto(0, 100));
		EtiquetaTriangular enojoAlta = new EtiquetaTriangular(100, "Alto", new Punto(50, 100));
		enojo.setEtiquetas(new ArrayList<>(Arrays.asList(enojoBaja,enojoNormal, enojoAlta)));
	}

}
