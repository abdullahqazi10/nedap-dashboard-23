package utwente.nedap.team23.nedapDashboard.resources;


import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import utwente.nedap.team23.nedapDashboard.model.Account;
import utwente.nedap.team23.nedapDashboard.model.Customer;
import utwente.nedap.team23.nedapDashboard.security.AuthenticationTokenPayload;
import utwente.nedap.team23.nedapDashboard.security.AuthenticationTokenService;

@Path("dashboard")
// This will be moved to the Servlet..... this is just provisional
public class DashboardResource {

	/**
	 * Returns the Dashboard according to the requester's role.
	 * 
	 * @param csc		the <code>CusomSecurityContext</code>
	 * 
	 * @return			HTML file of the Dashboard
	 */
	@GET
	@Produces(MediaType.TEXT_HTML)
	@RolesAllowed({"TECHNICIAN", "SUPPORT", "CUSTOMER"})
	public Response getDashboard(@Context SecurityContext sc, @Context HttpServletResponse response) {


		final AuthenticationTokenPayload  details = (AuthenticationTokenPayload) sc.getUserPrincipal();

		if (details.getRole().equalsIgnoreCase("CUSTOMER")) {	
			response.setHeader("Location", "http://localhost:8080/di23_dashboard/customer/index.html");
			return Response.ok().build();		
		} else {
			response.setHeader("Location", "http://localhost:8080/di23_dashboard/employee/index.html");
			return Response.ok().build(); 
		}	
	}
}
