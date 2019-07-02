package utwente.nedap.team23.nedapDashboard.resources;

import java.sql.SQLException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import utwente.nedap.team23.nedapDashboard.database.ResourceDAO;
import utwente.nedap.team23.nedapDashboard.model.*;
import utwente.nedap.team23.nedapDashboard.model.bean.OrganizationBean;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource location of accounts. 
 *  * This class is triggered if the URI corresponds to 
 * "/organizations/" or "/organizations/{orgID}/[...]".
 */
@Path("organizations")
@RolesAllowed({"TECHNICIAN", "SUPPORT", "CUSTOMER"})
public class OrganizationResource {
	
	
	/**
	 * Delegates the request to the handler of jobs.
	 * Inherits the authorization roles from the class.
	 * 
	 * @return		a <code>JobResource</code>
	 */
	@Path("{organizationID}/jobs")
	public JobResource delegateToJobs() { return new JobResource(); }
	
	
	/**
	 * Delegates the request to the handler of jobs. This delegation is 
	 * only permitted to user with certain priviliges, i.e if the requester
	 * is of <code>Role.TECHNICIAN</code> or of </code>Role.SUPPORT</code>.
	 * 
	 * @return		a <code>JobResource</code>
	 */
	@Path("jobs")
	@RolesAllowed({"TECHNICIAN, SUPPORT"})
	public JobResource delegateToJobsWithPrivilege() { return new JobResource(); }
	
	
	/**
	 * Delegates the request to the handler of jobs.
	 * Inherits the authorization roles from the class.
	 * 
	 * @return		a <code>AccountResource</code>
	 */
	@Path("{organizationID}/accounts")
	public AccountResource delegateToAccounts() { return new AccountResource(); }
	

	/**
	 * Returns a list of (all) organizations. Access to this
	 * resource is permitted to the following roles: 
	 * <code>Role.TECHNICIAN</code> and </code>Role.SUPPORT</code>.
	 * 
	 * Collection is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.
	 *  
	 * @param bean		bean being used to inject all information of the request
	 * 
	 * @return			list of <code>Organization</code>
	 */
	@GET
	@RolesAllowed({"TECHNICIAN", "SUPPORT"})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOrganizations(@BeanParam OrganizationBean bean) {

		try {
			ResourceDAO rService = ResourceDAO.instance;
			List<Organization> organizations = rService.getAllOrganizations(bean.getLimit());
			for (Organization org : organizations) {
				String self = bean.createSelfLink(org.getOrganizationID());
				org.addLink("self", self);
			}
			
			return Response.ok().entity(organizations).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
	
	
	/**
	 * Returns an organization resource, with the given 
	 * organization id. Access to this resource is permitted 
	 * to the following roles: <code>Role.TECHNICIAN</code> 
	 * and </code>Role.SUPPORT</code>.
	 * 
	 * Access to this
	 * resource is permitted to the following roles: 
	 * <code>Role.TECHNICIAN</code> and </code>Role.SUPPORT</code>.
	 * 
	 * @param bean 		bean being used to inject all information of the request
	 * 
	 * @param orgID     the id of the organization to be obtained
	 * 
	 * @return			an <code>Organization</code>
	 */
	@GET
	@Path("{organizationID}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getOrganization(@BeanParam OrganizationBean bean, 
			@PathParam("organizationID") String orgID) {
		
		try {
			ResourceDAO rService = ResourceDAO.instance;
			Organization org = rService.getOrganization(orgID);
			String self = bean.createSelfLink(orgID);
			org.addLink("self", self);
			
			return Response.ok().entity(org).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
}
