/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.implementations.CredencialDaoImpl;
import dao.implementations.UsuarioDaoImpl;
import dao.interfaces.ICredencialDao;
import dao.interfaces.IUsuarioDao;
import models.Credencial;
import models.OutputMessage;
import models.Usuario;
import security.TokenUtil;

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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.criterion.Order;

/**
 *
 * @author André Gomes
 */
@Path("/usuario")
public class ServiceUsuario {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Credencial c) {
        try {
        	ICredencialDao credencialDAO = new CredencialDaoImpl();
        	String token = TokenUtil.criaToken(c.getPwd());
        	c.setToken(token);
            credencialDAO.save(c);
            
            
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new OutputMessage(500, e.getMessage()))
                    .build();
        }
        c.setToken(null);
        return Response
                .status(Response.Status.CREATED)
                .entity(c)
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int id) {
    	IUsuarioDao usuarioDAO = new UsuarioDaoImpl();
        Usuario obj = usuarioDAO.findById(id);
        if (obj == null) {
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
        }
        try {
        	usuarioDAO.delete(obj);
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new OutputMessage(500, e.getMessage()))
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(new OutputMessage(200, "Objeto removido."))
                .build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(Usuario t) {
        try {
            IUsuarioDao usuarioDAO = new UsuarioDaoImpl();
            usuarioDAO.save(t);
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new OutputMessage(500, e.getMessage()))
                    .build();
        }
        return Response
                .status(Response.Status.OK)
                .entity(t)
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listById(@PathParam("id") int id) {
        try {
        	IUsuarioDao usuarioDAO = new UsuarioDaoImpl();
            Usuario obj = usuarioDAO.findById(id);
            if (obj == null) {
                return Response
                        .status(Response.Status.NO_CONTENT)
                        .build();
            } else {
                return Response
                        .status(Response.Status.OK)
                        .entity(obj)
                        .build();
            }
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new OutputMessage(500, e.getMessage()))
                    .build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll(
            @QueryParam("orderby") @DefaultValue("id") String orderBy,
            @QueryParam("sort") @DefaultValue("asc") String sort) {
        try {
        	IUsuarioDao usuarioDAO = new UsuarioDaoImpl();
            List<Usuario> obj;
            if (sort.equals("desc")) {
                obj = usuarioDAO.findAll(Order.desc(orderBy));
            } else {
                obj = usuarioDAO.findAll(Order.asc(orderBy));
            }
            if (obj == null) {
                return Response
                        .status(Response.Status.NO_CONTENT)
                        .build();
            } else {
                return Response
                        .status(Response.Status.OK)
                        .entity(obj)
                        .build();
            }
        } catch (Exception e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new OutputMessage(500, e.getMessage()))
                    .build();
        }
    }

}
