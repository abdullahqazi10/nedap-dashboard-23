package utwente.nedap.team23.nedapDashboard.resources;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
//import java.util.List;
import javax.ws.rs.GET;
import utwente.nedap.team23.nedapDashboard.model.*;
import utwente.nedap.team23.nedapDashboard.model.bean.ExecutionContextBean;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import utwente.nedap.team23.nedapDashboard.database.DatabaseDAO;

/**
 * Resource location of execution contexts.
 * This class is triggered if the URI corresponds to 
 * "/organizations/{orgID}/jobs/{jobID}/executions/{executionID}/execution-contexts".
 */
@PermitAll
@RolesAllowed({"TECHNICIAN", "SUPPORT", "CUSTOMER"})
public class ExecutionContextResource {
	
	/**
	 * Returns a list of context from <strong>one</strong> job execution.
	 * Access to this resource is permitted to all roles:
	 * <code>Role.TECHNICIAN</code>, </code>Role.SUPPORT</code> or
	 * <code>Role.CUSTOMER</code>.
	 * 
	 * Collection is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.
	 * 
	 * @param bean		bean being used to inject all information of the request
	 * 
	 * @return			a list of <code>BatchJobExecutionContext</code>
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getContextFor(@BeanParam ExecutionContextBean bean) {

		try {
			DatabaseDAO dbService = new DatabaseDAO();
			List<BatchJobExecutionContext> contexts = dbService.getContextsFor(bean.getExecutionID());
			for (BatchJobExecutionContext context : contexts) {
				String uri = bean.createSelfLink();
				context.addLink("self", uri);
			}

			return Response.ok().entity(contexts).build();
		} catch(SQLException e) {
			e.printStackTrace();
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
}