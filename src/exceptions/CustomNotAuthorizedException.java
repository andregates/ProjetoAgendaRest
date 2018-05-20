package exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import models.OutputMessage;

public class CustomNotAuthorizedException extends WebApplicationException {
	public CustomNotAuthorizedException() {
		super(Response.status(Response.Status.FORBIDDEN).type(MediaType.APPLICATION_JSON).build());
	}

	public CustomNotAuthorizedException(String message) {
		super(Response.status(Response.Status.FORBIDDEN)
				.entity(new OutputMessage(Response.Status.FORBIDDEN.getStatusCode(), message))
				.type(MediaType.APPLICATION_JSON).build());
	}
}