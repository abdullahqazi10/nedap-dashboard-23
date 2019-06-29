package utwente.nedap.team23.nedapDashboard.model.bean;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import utwente.nedap.team23.nedapDashboard.resources.OrganizationResource;

public class AccountBean {
	
	private @Context UriInfo uriInfo;
	private @DefaultValue("10") @QueryParam("limit") int limit;
	private @PathParam("organizationID") String orgID;
	private @QueryParam("person") Long perID;


	public UriInfo getUriInfo() { return uriInfo; }
	
	
	public void setUriInfo(UriInfo uriInfo) { this.uriInfo = uriInfo; }
	
	
	public int getLimit() { return limit; }
	
	
	public void setLimit(int limit) { this.limit = limit; }
	
	
	public String getOrgID() { return orgID; }


	public void setOrgID(String orgID) { this.orgID = orgID; }
	
	
	public Long getPerID() { return perID; }


	public void setPerID(Long perID) { this.perID = perID; }


	public String createSelfLink(long accID) {
		
		String self = uriInfo.getBaseUriBuilder().path(OrganizationResource.class, "delegateToAccounts")
		.resolveTemplate("organizationID", orgID)
		.path(Long.toString(accID))
		.build()
		.toString();
		
		return self;
	}	
}
