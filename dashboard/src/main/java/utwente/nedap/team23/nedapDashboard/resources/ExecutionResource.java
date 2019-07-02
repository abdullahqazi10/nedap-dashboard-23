package utwente.nedap.team23.nedapDashboard.resources;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import utwente.nedap.team23.nedapDashboard.model.*;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utwente.nedap.team23.nedapDashboard.database.ResourceDAO;
import utwente.nedap.team23.nedapDashboard.model.bean.ExecutionBean;

/**
 * Resource location of executions.
 * Class is invoked if the request matches one of the following 
 * URI's: "/organizations/{orgID}/jobs/{jobID}/executions/", 
 * "/organizations/{orgID}/jobs/{jobID}/executions/{executionID}/[...]",
 *  
 *  In case of being in role of <code>Role.TECHNICIAN</code> or </code>Role.SUPPORT</code>,
 *  no specific organization (id) necessarily has to be provided. 
 */
@Path("executions")
@RolesAllowed({"TECHNICIAN", "SUPPORT", "CUSTOMER"})
public class ExecutionResource {
	
	
	
	/**
	 * Delegates the request to the handler of execution parameters.
	 * Inherits the authorization roles from the class.
	 * 
	 * @return		an <code>ExecutionParamResource</code>
	 */
	@Path("{executionID}/execution-parameters")
	public ExecutionParamResource delegateToParam() { return new ExecutionParamResource(); }

	
	/**
	 * Delegates the request to the handler of execution contexts.
	 * Inherits the authorization roles from the class.
	 * 
	 * @return		an <code>ExecutionContextResource</code>
	 */
	@Path("{executionID}/execution-contexts")
	public ExecutionContextResource delegateToContext() { return new ExecutionContextResource(); }

	
	/**
	 * Delegates the request to the handler of step executions.
	 * Inherits the authorization roles from the class.
	 * 
	 * @return		a <code>StepExecutionResource</code>
	 */
	@Path("{executionID}/step-executions")
	public StepExecutionResource delegateToStepExe() { return new StepExecutionResource(); }
	
	
	
	
	/**
	 * Returns a list of (all) executions. Access to this
	 * resource is permitted to all roles: <code>Role.TECHNICIAN</code>, 
	 * </code>Role.SUPPORT</code> or <code>Role.CUSTOMER</code>.
	 * 
	 * Collection is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.
	 *  
	 * @param bean		bean being used to inject all information of the request
	 * 
	 * @return			list of <code>BatchJobExecution</code>
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response getAllExecutions(@BeanParam ExecutionBean bean) {

		try {
			ResourceDAO rService = ResourceDAO.instance;
			List<BatchJobExecution> executions;

			if (bean.getStatus() != null) { 
				executions = rService.getExecutionsWithStatus(bean.getOrganizationID(), 
						bean.getInstanceID(), bean.getStatus());
			} else { executions = rService.getExecutions(bean.getInstanceID()); }
			
			for (BatchJobExecution exe : executions) {
				String self = bean.createSelfLink(exe.getJobExecutionID());
				String params = bean.createLinkForParams(exe.getJobExecutionID());
				String step = bean.createLinkForStepExe(exe.getJobExecutionID());
				String context = bean.createLinkForContext(exe.getJobExecutionID());
				String job = bean.createJobInstanceLink();
				exe.addLink("self", self);
				exe.addLink("params", params);
				exe.addLink("steps", step);
				exe.addLink("contexts", context);
				exe.addLink("jobInstance", job);
			}
 
			return Response.ok().entity(executions).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
			   .entity("Sorry a mistake happened. Try it later again!").build();
		} 	
	}
	
	
	/**
	 * Returns a specific (job) executions, with the given execution
	 * id. Access is permitted to all roles: <code>Role.TECHNICIAN</code>, 
	 * </code>Role.SUPPORT</code> or <code>Role.CUSTOMER</code>.
	 * 
	 * Resource is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.

	 * 
	 * @param bean		bean being used to inject all information of the request
	 * 
	 * @param eID		id of the (job) execution
	 * 
	 * @return			a <code>BatchJobExecution</code>
	 */
	@GET
	@Path("/{executionID}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response gettJobExecutionWithID(@BeanParam ExecutionBean bean,
			@PathParam("executionID") long eID) {

		try {
			ResourceDAO dbService = ResourceDAO.instance;
			BatchJobExecution bje = dbService.getExecution(bean.getInstanceID(), eID);
			String self = bean.createSelfLink(bje.getJobExecutionID());
			String params = bean.createLinkForParams(bje.getJobExecutionID());
			String step = bean.createLinkForStepExe(bje.getJobExecutionID());
			String context = bean.createLinkForContext(bje.getJobExecutionID());
			String job = bean.createJobInstanceLink();
			bje.addLink("self", self);
			bje.addLink("params", params);
			bje.addLink("steps", step);
			bje.addLink("contexts", context);
			bje.addLink("jobInstance", job);

			return Response.ok().entity(bje).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					   .entity("Sorry a mistake happened. Try it later again!").build();	
		}
	}
}
