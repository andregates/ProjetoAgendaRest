package services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import dao.interfaces.IContatoDao;
import dao.implementations.ContatoDaoImpl;
import dao.interfaces.IUsuarioDao;
import dao.implementations.UsuarioDaoImpl;
import exceptions.CustomNoContentException;
import models.OutputMessage;
import models.Contato;
import models.Usuario;
import security.Secured;

@Path("/contato")
public class ServiceContato {

	@POST
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response create(Contato contato, @Context SecurityContext securityContext) {
		
		IContatoDao contatoDao = new ContatoDaoImpl();
		IUsuarioDao usuarioDao = new UsuarioDaoImpl();
		try {
			
			String idUser = securityContext.getUserPrincipal().getName();//Id do usuario via token
			Usuario usuario = usuarioDao.findById(Integer.parseInt(idUser));
			
			if(usuario != null) {
				contato.setUsuario(usuario);
				contatoDao.save(contato);
			} else {
				return Response.status(Response.Status.NOT_MODIFIED).entity(contato).build();
			}

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
		} finally {
			usuarioDao.close();
			contatoDao.close();
		}

		return Response.status(Response.Status.CREATED).entity(new OutputMessage(200,"Contato Armazenado com Sucesso!!")).build();
	}
	
	@DELETE
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") int id, @Context SecurityContext securityContext) throws CustomNoContentException{
		IContatoDao ContatoDao = new ContatoDaoImpl();
		Contato contato = ContatoDao.findById(id);
		
		if (contato == null)
			throw new CustomNoContentException();
		
		if(contato.getUsuario() != null) {
			if(contato.getUsuario().getUsuarioId() == Integer.parseInt((securityContext.getUserPrincipal().getName()))) {
				ContatoDao.delete(contato);				
			} else {
				throw new CustomNoContentException();
			}
		}
		
		
		return Response.status(Response.Status.OK)
				.entity(new OutputMessage(200,"Contato removido com sucesso!"))
				.build();
	}

	@PUT
	@Secured
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Contato contato, @Context SecurityContext securityContext) {

		try {
			if(contato.getUsuario().getUsuarioId() == Integer.parseInt((securityContext.getUserPrincipal().getName()))) {
				IContatoDao contatoDao = new ContatoDaoImpl();
				contatoDao.save(contato);
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500, e.getMessage()))
					.build();
		}

		return Response.status(Response.Status.OK).entity(contato).build();

	}

	@GET
	@Secured
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response listById(@PathParam("id") int idContact, @Context SecurityContext securityContext) {
		try {
			IContatoDao ContatoDAO = new ContatoDaoImpl();
			int idUser = Integer.parseInt(securityContext.getUserPrincipal().getName());
			Contato obj = ContatoDAO.findById(idContact);
			
			if (obj == null) {
				return Response.status(Response.Status.NO_CONTENT).build();
			} else {
				if(idUser == obj.getUsuario().getUsuarioId())
					return Response.status(Response.Status.OK).entity(obj).build();
				else
					return Response.status(Response.Status.NO_CONTENT).build();

			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500, e.getMessage()))
					.build();
		}
	}

	@GET
	@Secured
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAll(@QueryParam("orderby") @DefaultValue("nome") String orderBy,
							@QueryParam("sort") @DefaultValue("asc") String sort,
							@Context SecurityContext securityContext) {
		try {
			IContatoDao ContatoDao = new ContatoDaoImpl();
			List<Contato> obj;
			int idUser = Integer.parseInt(securityContext.getUserPrincipal().getName());
			if (sort.equals("desc")) {
				obj = ContatoDao.findAllByContatosPorUsuario(idUser, orderBy);
			} else {
				obj = ContatoDao.findAllByContatosPorUsuario(idUser, orderBy);
			}
			if (obj == null) {
				return Response.status(Response.Status.NO_CONTENT).build();
			} else {
				return Response.status(Response.Status.OK).entity(obj).build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500, e.getMessage()))
					.build();
		}
		
		
	}
	
	
	


}
