package services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dao.implementations.CredencialDaoImpl;
import dao.interfaces.ICredencialDao;
import models.Credencial;
import models.OutputMessage;

@Path("/autenticacao")
public class ServiceAutentication {
	
	private Credencial cReturn;
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticaUser(Credencial c) {
		try {
				ICredencialDao credencialDAO = new CredencialDaoImpl();
				cReturn=credencialDAO.validarUsuario(c.getUsername(), c.getPwd());
				
				if (cReturn==null)
					return Response.status(Response.Status.OK).entity(new OutputMessage(403, "Permissão Negada")).build();
				else
					return Response.status(Response.Status.OK).entity(new OutputMessage(200, cReturn.getToken())).build();
		} catch (Exception e) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity(new OutputMessage(Response.Status.UNAUTHORIZED.getStatusCode(),
							"Não autorizado: " + e.getMessage()))
					.build();
		}
	}

}