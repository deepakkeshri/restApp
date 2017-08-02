package main.java.endpoints;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import main.java.exception.InputException;

public abstract class EndpointAction {
	
	  public abstract JSONObject action() throws Exception;
	  
	  public Object doAction() throws Exception {
		JSONObject response = null;
		try {
			response = this.action();
		}  catch(Exception e) {
			handleException(e);
		}
		return Response.ok(response.toString()).build();
	  }
	  
	  public void handleException(Exception exception) throws Exception {
		  
		  JSONObject json = null;
		  Response response = null;
		  if (exception instanceof InputException) {
			  InputException ex = (InputException) exception;
			  json = buildErrorJson(HttpServletResponse.SC_BAD_REQUEST, ex.getErrorCode(), exception.getMessage());
			  response = buildResponse(HttpServletResponse.SC_BAD_REQUEST, json);
		  } else {
			  json = buildErrorJson(HttpServletResponse.SC_BAD_REQUEST, "1000", exception.getMessage());
			  response = buildResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, json);
		  }
		  throw new WebApplicationException((Throwable) null, response);
	  }
	  
	  public static Response buildResponse(int pHttpErrorCode, JSONObject pJson) {
		  String entity = null;
		  if(pJson != null)
			  entity = pJson.toString();
		  Response response = Response.status(pHttpErrorCode)
				  .type(MediaType.APPLICATION_JSON)
				  .entity(new GenericEntity<String>(entity){}).build();
		  return response;
	  }
	  
	  public static JSONObject buildErrorJson(int status, String errorCode, String message) throws JSONException {
		  JSONObject json = new JSONObject();
		  json.put("status", status);
		  json.put("errorCode", errorCode);
		  json.put("message", message);
		  return json;
	  }

}
