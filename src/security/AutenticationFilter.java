package security;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import exceptions.CustomNotAuthorizedException;
import models.Credencial;

@Provider
@Secured
@Priority(Priorities.AUTHENTICATION)
public class AutenticationFilter implements ContainerRequestFilter {
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		// Obtem o valor do dado do HEADER AUTHORIZATION da requisição HTTP
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		// Verifica existe o header e se ele está no formato correto
		if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
			throw new CustomNotAuthorizedException("Usuário não está logado.");
		}
		// Obtem o token
		String token = authorizationHeader.substring("Bearer".length()).trim();

		try {
			// Valida o token
			Credencial c = TokenUtil.validaToken(token);
			
			requestContext.setSecurityContext(new SecurityContext() {
				
				@Override
				public boolean isUserInRole(String role) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean isSecure() {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public Principal getUserPrincipal() {
					// TODO Auto-generated method stub
					return new Principal() {

						/** getName() == id user */
						@Override
						public String getName() {
							// usuario/ID/etc que veio do banco após a validacao do token
							return Integer.toString(c.getUsuario().getUsuarioId());
						}

					};	
				}
				
				@Override
				public String getAuthenticationScheme() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			
			
		} catch (Exception e) {
			// Aborta a execução
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
		}
		

		
	}

}