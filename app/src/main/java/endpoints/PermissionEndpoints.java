package main.java.endpoints;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import main.java.services.JerseyServiceManager;

@Path("/permission")
public class PermissionEndpoints {
	
	@GET
	@Path("/checkpermission")
	@Produces(MediaType.APPLICATION_JSON)
	public Object checkPermission(@Context HttpServletRequest request,
            			@Context HttpServletResponse response,
            			@QueryParam("userId") @DefaultValue("false") final String userId,
            			@QueryParam("permissionId") @DefaultValue("false") final String permissionId) throws Exception {
		
		return new EndpointAction() {
			@Override
			public JSONObject action() throws Exception {
				JerseyServiceManager services = new JerseyServiceManager();
				return services.getPermissionServices().checkUserPermission(services, userId, permissionId);
			}
		}.doAction();

	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object deletePermission(@Context HttpServletRequest request,
            			@Context HttpServletResponse response,
            			@PathParam("id") String id) throws Exception {
		
		return new EndpointAction() {
			@Override
			public JSONObject action() throws Exception {
				JerseyServiceManager services = new JerseyServiceManager();
				return services.getPermissionServices().deletePermission(services, id);
			}
		}.doAction();

	}
	
}
