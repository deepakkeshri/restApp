package main.java.endpoints;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import main.java.services.JerseyServiceManager;

@Path("/role")
public class RoleEndpoints {

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getRole(
						@Context HttpServletRequest request,
            			@Context HttpServletResponse response,
        				@PathParam("id") String id) throws Exception {		
		return new EndpointAction() {

			@Override
			public JSONObject action() throws Exception {
				JerseyServiceManager services = new JerseyServiceManager();
				return services.getRoleServices().getRoleData(id);
			}
		}.doAction();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Object updateRole(final Object input,
						@Context HttpServletRequest request,
            			@Context HttpServletResponse response,
        				@PathParam("id") String id) throws Exception {
		
	
		return new EndpointAction() {
			@SuppressWarnings("unchecked")
			@Override
			public JSONObject action() throws Exception {
				JerseyServiceManager services = new JerseyServiceManager();
				return services.getRoleServices().updateRole(services, (Map<String, Object>) input, id);
			}
		}.doAction();
		
	}
	
}
