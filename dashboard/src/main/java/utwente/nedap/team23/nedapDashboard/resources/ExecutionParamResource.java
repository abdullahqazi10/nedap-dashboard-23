package utwente.nedap.team23.nedapDashboard.resources;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import utwente.nedap.team23.nedapDashboard.model.*;
import utwente.nedap.team23.nedapDashboard.model.bean.ExecutionParamBean;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utwente.nedap.team23.nedapDashboard.database.ResourceDAO;

/**
 * Resource location of parameters.
 * Class is invoked if the request matches the following URI: 
 * "/organizations/{orgID}/jobs/{jobID}/executions/{executionID}/execution-parameters".
 */
@RolesAllowed({"TECHNICIAN", "SUPPORT", "CUSTOMER"})
public class ExecutionParamResource {

	/**
	 * Returns a list of parameters for a given job execution.
	 * This resource is accessible to all roles: 
	 * <code>Role.TECHNICIAN</code>, </code>Role.SUPPORT</code> or
	 * <code>Role.CUSTOMER</code>.
	 * 
	 * Collection is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.
	 * 
	 * @param bean		bean being used to inject all information of the request
	 * 
	 * @return			a list of <code>BatchJobExecutionParams</code>
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getParametersFor(@BeanParam ExecutionParamBean bean) {

		try {
			ResourceDAO rService = ResourceDAO.instance;
			List<BatchJobExecutionParams> bjeps = rService.getParametersFor(bean.getExecutionID());
			for (BatchJobExecutionParams param : bjeps) {
				String uri = bean.createSelfLink();
				param.addLink("self", uri);
			}
			
			return Response.ok().entity(bjeps).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
}
