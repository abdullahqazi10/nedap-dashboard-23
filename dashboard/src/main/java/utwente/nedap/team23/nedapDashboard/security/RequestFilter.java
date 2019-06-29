package utwente.nedap.team23.nedapDashboard.security;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.lang.JoseException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import utwente.nedap.team23.nedapDashboard.database.DatabaseDAO;
import utwente.nedap.team23.nedapDashboard.model.Account;

/**
 * Interceptor class that intercepts a request to check authentication
 * and autherization of the requester in question.
 */
@Provider
@PreMatching 
public class RequestFilter implements ContainerRequestFilter {

	private @Context ResourceInfo resourceInfo;
	private @Context HttpServletResponse response;

	/**
	 * Returns the context of the request, containing necessary information
	 * such that the servlet can do the role-matching properly and other concerns.
	 * A request is aborted if the requester has no access to the resource, authentication 
	 * has failed or due to a server error.
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {	

		String authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);	
		System.out.println(authHeader);
		if (authHeader.substring(0, 6).equals("Bearer")) {
			AuthenticationTokenService tkService = new AuthenticationTokenService();
			String jwt = authHeader.replace("Bearer ", "");
			try {
				
				AuthenticationTokenPayload payload = tkService.validateToken(jwt);
				requestContext.setSecurityContext(new CustomSecurityContext(payload));
				return;
			} catch (MalformedClaimException | JoseException | InvalidJwtException e) {
				e.printStackTrace();
				requestContext.abortWith(Response.status(Response.Status.FORBIDDEN)
						.entity("Invalid Token!").build());
				return;	
			}
		} else {
			response.setHeader("Location", "http://localhost:8080/nedapDashboard/login.html");
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
					.entity("You have no access to this resource!").build());		
		}
	}
}	

