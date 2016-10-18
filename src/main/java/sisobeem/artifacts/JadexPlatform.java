package sisobeem.artifacts;

import java.util.ArrayList;

import jadex.base.Starter;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IExternalAccess;
import jadex.bridge.service.RequiredServiceInfo;
import jadex.bridge.service.search.SServiceProvider;
import jadex.bridge.service.types.cms.CreationInfo;
import jadex.bridge.service.types.cms.IComponentManagementService;

public class JadexPlatform {
	
	private IExternalAccess  platform;
	private IComponentManagementService cms;
	
	/*Constructors*/
	
	public JadexPlatform() {
		 this.platform = Starter.createPlatform(new String[]{
					"-gui", "false",
					"-welcome", "false",
					"-cli", "false",
					"-printpass", "false"
				}).get();
		 
		 this.cms = SServiceProvider.getService(platform, 
					IComponentManagementService.class, 
					RequiredServiceInfo.SCOPE_PLATFORM).get();
	}
	
	/* Methods Get and Set*/
	public IExternalAccess getPlatform() {
		return platform;
	}

	public void setPlatform(IExternalAccess platform) {
		this.platform = platform;
	}

	public IComponentManagementService getCms() {
		return cms;
	}

	public void setCms(IComponentManagementService cms) {
		this.cms = cms;
	}

	/*Functions and Methods*/
	public IComponentIdentifier BuildComponent(String name, String model, CreationInfo info){
		return cms.createComponent(name, model, info).getFirstResult();
	}
	
	public ArrayList<IComponentIdentifier> BuildComponents(String model, int quantity){
		ArrayList<IComponentIdentifier> agents =  new ArrayList<>();
		for(int i=0 ; i< quantity;i++){
			agents.add(cms.createComponent(model, null).getFirstResult());
		}
		return agents;
	}

	public ArrayList<IComponentIdentifier> BuildComponents(String model, ArrayList<CreationInfo> infos  ){
	ArrayList<IComponentIdentifier> agents =  new ArrayList<>();
	   for (CreationInfo info : infos) {
		   agents.add(cms.createComponent(model, info).getFirstResult());
	  }
		return agents;
	}
	
	
	public ArrayList<IComponentIdentifier> BuildComponents(String model, String[]names){
		ArrayList<IComponentIdentifier> agents =  new ArrayList<>();		
		
		for(int i=0 ; i< names.length;i++){
			agents.add(cms.createComponent(names[i] , model, null).getFirstResult());
		}
		return agents;
	}
}
