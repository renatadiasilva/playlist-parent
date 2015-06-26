package dei.uc.pt.aor.wserver.core;

import java.util.Set;

import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("/rest")
public class ApplicationConfig extends Application{

	@Override
	public Set<Class<?>> getClasses() {
	    Set<Class<?>> resources = new java.util.HashSet<>();
	    addRestResourceClasses(resources);
	    // extra.. for uploading files via form !
	    //resources.add(MultiPartFeature.class);
	    return resources;
	}
	

	/*
	 * Responsible for adding our "service" classes
	 */
	private void addRestResourceClasses(Set<Class<?>> resources) {
	    // resources.add(xpto.class);
		resources.add(UserService.class);
	}
	
}
