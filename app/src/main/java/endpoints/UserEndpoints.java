package main.java.endpoints;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import main.java.services.JerseyServiceManager;

@Path("/user")
public class UserEndpoints {

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getUserPermissions(@Context HttpServletRequest request,
            			@Context HttpServletResponse response,
        				@PathParam("id") String id) throws Exception {
		
		return new EndpointAction() {

			@Override
			public JSONObject action() throws Exception {
				JerseyServiceManager services = new JerseyServiceManager();
				return services.getUserServices().getUserPermissionNames(id);
			}
			
		}.doAction();
		
	}
	
}
