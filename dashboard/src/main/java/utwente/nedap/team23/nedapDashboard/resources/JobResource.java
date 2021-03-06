package utwente.nedap.team23.nedapDashboard.resources;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import utwente.nedap.team23.nedapDashboard.database.ResourceDAO;
import utwente.nedap.team23.nedapDashboard.model.*;
import utwente.nedap.team23.nedapDashboard.model.bean.JobBean;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

/**
 * Resource location of jobs.
 * * This class is triggered if the URI corresponds to 
 * "/organizations/{orgID}/jobs/" or  "/organizations/{orgID}/jobs/{jobID}/[...]".
 * 
 * In case of being in role of <code>Role.TECHNICIAN</code> or </code>Role.SUPPORT</code>,
 *  no specific organization (id) necessarily has to be provided. 
 */
@Path("jobs")
@RolesAllowed({"TECHNICIAN", "SUPPORT", "CUSTOMER"})
public class JobResource {
	
	/**
	 * Delegates the request to the handler of executions.
	 * Inherits the authorization roles from the class.
	 * 
	 * @return		an <code>ExecutionResource</code>
	 */
	@Path("{jobInstanceID}/executions")
	public ExecutionResource delegateTo() { return new ExecutionResource(); }


	/**
	 * Returns a list of (all) jobs. 
	 * Access to this resource is permitted to all roles: 
	 * <code>Role.TECHNICIAN</code>, </code>Role.SUPPORT</code> or 
	 * <code>Role.CUSTOMER</code>.
	 * 
	 * Collection is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.
	 *  
	 * @param bean		bean being used to inject all information of the request
	 * 
	 * @return			list of <code>BatchJobInstance</code>
	 */
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getJobs(@BeanParam JobBean bean, @Context SecurityContext sc) {

		try {
			ResourceDAO rService = ResourceDAO.instance;
			List<BatchJobInstance> jobs;
			if (bean.getOrganizationID() != null) {

				jobs = rService.getJobInstances(bean.getLimit(), bean.getOrganizationID(),
						bean.getStart(), bean.getEnd());			
			} else { 
				if (!sc.isUserInRole("CUSTOMER")) {
					jobs = rService.getJobInstances(bean.getLimit(), bean.getStart(),
							bean.getEnd()); 
				} else {

					// Had to implement it - JAX RS has a bug
					// and doesnt check for role when customer makes a 
					// request to /organizations/jobs/ because of delegation (I guess)
					return Response.status(403)
							.entity("You have no access to this resource!").build();
				}
			}

			for (BatchJobInstance job : jobs) {
				String uri = bean.createSelfLink(job.getJobInstanceID(), job.getOrgID());
				String exe = bean.createExeLink(job.getJobInstanceID(), job.getOrgID());
				job.addLink("self", uri);
				job.addLink("executions", exe);
			}

			return Response.ok().entity(jobs).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		} 	
	}


	/**
	 * Returns a specific job with the given job id. 
	 * Access to this resource is permitted to all roles: 
	 * <code>Role.TECHNICIAN</code>, </code>Role.SUPPORT</code> or 
	 * <code>Role.CUSTOMER</code>.
	 * 
	 * Resource is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.
	 *  
	 * @param bean		bean being used to inject all information of the request
	 * 
	 * @return			a <code>BatchJobInstance</code>
	 */
	@GET
	@Path("{jobInstanceID}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getJobWithInstanceID(@BeanParam JobBean bean, 
			@PathParam("jobInstanceID") long iID) {

		try {
			ResourceDAO rService = ResourceDAO.instance;
			BatchJobInstance job = rService.getJobInstance(iID, bean.getOrganizationID());

			if (job == null) { 
				return Response.status(Response.Status.NOT_FOUND)
						.entity("Resource not Found.").build(); 
			} 
		
			String uri = bean.createSelfLink(job.getJobInstanceID(), bean.getOrganizationID());
			job.addLink("self", uri);

			return Response.ok().entity(job).build();

		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
}
