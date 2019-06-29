package utwente.nedap.team23.nedapDashboard.resources;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import utwente.nedap.team23.nedapDashboard.database.DatabaseDAO;
import utwente.nedap.team23.nedapDashboard.model.*;
import utwente.nedap.team23.nedapDashboard.model.bean.OrganizationBean;
import utwente.nedap.team23.nedapDashboard.model.bean.StepExecutionBean;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource location of step executions.
 * This class is triggered if the URI corresponds to 
 * "/organizations/{orgID}/jobs/{jobID}/executions/{executionID}/step-executions".
 */
@RolesAllowed({"TECHNICIAN", "SUPPORT", "CUSTOMER"})
public class StepExecutionResource {
	
	
	/**
	 * Returns a list of steps from <strong>one</strong> job execution.
	 * Access to this resource is permitted to all roles:
	 * <code>Role.TECHNICIAN</code>, </code>Role.SUPPORT</code> or
	 * <code>Role.CUSTOMER</code>.
	 * 
	 * Collection is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.
	 * 
	 * @param bean		bean being used to inject all information of the request
	 * 
	 * @return			a list of <code>BatchStepExecution</code>
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getStepExecutions(@BeanParam StepExecutionBean bean) {

		try {
			DatabaseDAO dbService = new DatabaseDAO();
			List<BatchStepExecution> stepExecutions = dbService.getStepExecutionFor(bean.getStatus(), 
					bean.getExecutionID());
			for (BatchStepExecution step : stepExecutions) {
				String uri = bean.createSelfLink();
				step.addLink("self", uri);
			}

			return Response.ok().entity(stepExecutions).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
}
