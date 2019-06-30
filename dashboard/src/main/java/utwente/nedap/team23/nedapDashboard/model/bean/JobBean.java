package utwente.nedap.team23.nedapDashboard.model.bean;

import java.sql.Date;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import utwente.nedap.team23.nedapDashboard.resources.OrganizationResource;

public class JobBean {
	
	private @DefaultValue("50") @QueryParam("limit") int limit;
	private @QueryParam("start") Date start;
	private @QueryParam("end") Date end; 
	private @PathParam("organizationID") String organizationID;
	private @Context UriInfo uriInfo;

	public int getLimit() { return limit; }
	
	
	public void setLimit(int limit) { this.limit = limit; }
	
	
	public Date getStart() { return start; }


	public void setStart(Date start) { this.start = start; }


	public Date getEnd() { return end; }


	public void setEnd(Date end) { this.end = end; }


	public String getOrganizationID() { return organizationID; }


	public void setOrganizationID(String organizationID) { this.organizationID = organizationID; }


	public UriInfo getUriInfo() { return uriInfo; }


	public void setUriInfo(UriInfo uriInfo) { this.uriInfo = uriInfo; }

	
	public String createSelfLink(Long instanceID, String orgID) {
			
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path(OrganizationResource.class)
		.path(OrganizationResource.class, "delegateToJobs")
		.resolveTemplate("organizationID", orgID);
		if (instanceID != null) { uriBuilder.path(Long.toString(instanceID)); }
		uriBuilder.build();
		
		return uriBuilder.toString();
	}
	
	public String createExeLink(Long instanceID, String orgID) {
		
		String uri = uriInfo.getBaseUriBuilder().path(OrganizationResource.class)
		.path(OrganizationResource.class, "delegateToJobs")
		.resolveTemplate("organizationID", orgID)
		.path(Long.toString(instanceID))
		.path("executions")
		.build().toString();
		
		return uri;
	}
	
}
