package dei.uc.pt.aor.wserver.core;

import java.util.Set;

import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("/rest")
public class ApplicationConfig extends Application{

	@Override
	public Set<Class<?>> getClasses() {
	    Set<Class<?>> resources = new java.util.HashSet<>();
	    addRestResourceClasses(resources);
	    return resources;
	}
	

	/*
	 * Responsible for adding our "service" classes
	 */
	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(UserService.class);
		resources.add(PlaylistService.class);
		resources.add(SongService.class);
	}
	
}
