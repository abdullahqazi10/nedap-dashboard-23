package utwente.nedap.team23.nedapDashboard.resources;

import java.sql.SQLException;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import utwente.nedap.team23.nedapDashboard.database.*;
import utwente.nedap.team23.nedapDashboard.model.*;
import utwente.nedap.team23.nedapDashboard.model.bean.AccountBean;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Resource location of accounts. Triggered if the 
 * URI of the request matches to "/organizations/accounts"
 * or "/organizations/accounts/{accountID}".
 */
public class AccountResource {

	/**
	 * Returns a list of accounts, provided the requester has
	 * the required authorization. Roles that are allowed to have access
	 * are <code>Role.TECHNICIAN</code> and </code>Role.SUPPORT</code>.
	 * 
	 * Collection is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.
	 * 
	 * @param bean	bean being used to inject all information of the request
	 * 
	 * @return		a list of <code>Account</code>
	 */
	@GET
	@RolesAllowed({"TECHNICIAN", "SUPPORT"})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getAllAccounts(@BeanParam AccountBean bean) {
		
		try {
			DatabaseDAO dbService = new DatabaseDAO();
			List<Account> accounts = dbService.getAccounts(bean.getLimit(), bean.getOrgID());
			for (Account acc : accounts) {
				String uri = bean.createSelfLink(acc.getAccount_id());
				acc.addLink("self", uri);
			}
			
			return Response.ok().entity(accounts).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
	
	/**
	 * Returns the account based on the given account id. 
	 * Returns an empty set if that account does not exists (for now).
	 * To access this resource one has to have the following roles: 
	 * <code>Role.TECHNICIAN</code>, </code>Role.SUPPORT</code> or
	 * <code>Role.CUSTOMER</code>.
	 * 
	 * Resource is returned either as <code>MediaType.APPLICATION_XML</code> or 
	 * <code>MediaType.APPLICATION_JSON</code>.
	 * 
	 * 
	 * @param accID		id of the account
	 * 
	 * @param bean		bean being used to inject all information of the request
	 * 
	 * @return			an <code>Account</code>
	 */
	@GET
	@Path("{accountID}")
	@RolesAllowed({"TECHNICIAN", "SUPPORT", "CUSTOMER"})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Response getAccount(@PathParam("accountID") long accID, @BeanParam AccountBean bean) {

		try {

			DatabaseDAO dbService = new DatabaseDAO();
			Account acc;
			if (bean.getPerID() == null) {
			acc = dbService.getAccountOf(accID, bean.getOrgID());			
			
			} else { acc = dbService.getAccountOf(bean.getPerID()); } // get account with owner id
			// get account with owner id

			return Response.ok().entity(acc).build();
		} catch(SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Sorry a mistake happened. Try it later again!").build();
		}
	}
}
