package sisobeem.agent.enviroment;

import java.util.ArrayList;
import jadex.bdiv3.annotation.Belief;
import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.Trigger;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.component.IArgumentsResultsFeature;
import jadex.bridge.service.component.IRequiredServicesFeature;
import jadex.commons.future.IFuture;
import jadex.commons.future.IResultListener;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.AgentCreated;
import jadex.micro.annotation.Description;
import jadex.micro.annotation.ProvidedService;
import jadex.micro.annotation.ProvidedServices;
import jadex.micro.annotation.RequiredService;
import jadex.micro.annotation.RequiredServices;
import sisobeem.artifacts.Coordenada;
import sisobeem.artifacts.Ubicacion;
import sisobeem.services.edificeServices.ISetBeliefEdificeService;
import sisobeem.services.plantServices.ISetBelifePlantService;
import sisobeem.utilities.Traslator;

@Agent
@Description("Agente Edificio: encargado de simular el ambiente edificio")
@RequiredServices({ @RequiredService(name = "ISetBelifePlantService", type = ISetBelifePlantService.class)

})
@ProvidedServices({ @ProvidedService(name = "ISetBelifeEdificeService", type = ISetBeliefEdificeService.class) })
public class EdificeAgentBDI extends EnviromentAgentBDI implements ISetBeliefEdificeService {

	ArrayList<IComponentIdentifier> cidsPlants;

	@Belief
	IComponentIdentifier cidZone;

	@Belief
	Coordenada myPosition;

	@Belief
	int salidas;

	@Belief
	float resistencia;

	@SuppressWarnings("unchecked")
	@AgentCreated
	public void init() {
		this.cidsPlants = new ArrayList<IComponentIdentifier>();
		// Accedemos a los argumentos del agente
		this.arguments = agent.getComponentFeature(IArgumentsResultsFeature.class).getArguments();

		// Obtenemos los cid de las personas en la Zona
		cidsPlants = (ArrayList<IComponentIdentifier>) arguments.get("cidsPlants");

		this.salidas = (int) arguments.get("salidas");
		this.resistencia = (float) arguments.get("resistencia");
		this.myPosition = Traslator.getTraslator().getCoordenada((Ubicacion) arguments.get("ubicacion"));

	}

	@AgentBody
	public void body() {

	}

	/**
	 * Metodo para enviar las coordenadas inciales a los agentes pisos
	 */
	public void sendEnviromentToPlants() {

		// System.out.println("Edifice: Enviando Creecias A los agentes");
		for (IComponentIdentifier piso : this.cidsPlants) {

			IFuture<ISetBelifePlantService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBelifePlantService.class, piso);

			// System.out.println("Tu Ubicacion es: "+c.getX()+" - "+c.getY()+"
			// Agente: "+person.getName());
			pisoService.addResultListener(new IResultListener<ISetBelifePlantService>() {

				@Override
				public void resultAvailable(ISetBelifePlantService result) {
					result.setEdifice(getAgent().getComponentIdentifier());
					result.setZone(cidZone);
				}

				@Override
				public void exceptionOccurred(Exception exception) {

				}

			});

		}
	}

	/**
	 * Metodo para enviar las coordenadas inciales a los agentes pisos
	 */
	@Plan(trigger = @Trigger(factchangeds = "intensidadSismo"))
	public void sendIntensidadSismoPlants() {
         
		 System.out.println("Edifice: Enviando Creecias A los agentes");
		for (IComponentIdentifier piso : this.cidsPlants) {

			IFuture<ISetBelifePlantService> pisoService = agent.getComponentFeature(IRequiredServicesFeature.class)
					.searchService(ISetBelifePlantService.class, piso);

			// System.out.println("Tu Ubicacion es: "+c.getX()+" - "+c.getY()+"
			// Agente: "+person.getName());
			pisoService.addResultListener(new IResultListener<ISetBelifePlantService>() {

				@Override
				public void resultAvailable(ISetBelifePlantService result) {
					result.setSismo(intensidadSismo);
				}

				@Override
				public void exceptionOccurred(Exception exception) {

				}

			});

		}
	}

	@Override
	public void setZone(IComponentIdentifier zone) {
		this.cidZone = zone;
		// Cuando se recibe el cidZone se envía a los agentes pisos
		sendEnviromentToPlants();
	}

	@Override
	public void setSismo(double intensidad) {
		this.intensidadSismo = intensidad;

		// System.out.println("temblando con intensidad de "+intensidad);
	}

}
