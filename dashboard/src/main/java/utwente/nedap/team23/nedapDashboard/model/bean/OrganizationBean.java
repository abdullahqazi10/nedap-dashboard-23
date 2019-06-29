package utwente.nedap.team23.nedapDashboard.model.bean;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import utwente.nedap.team23.nedapDashboard.resources.OrganizationResource;

public class OrganizationBean {
	
	private @Context UriInfo uriInfo;
	private @DefaultValue("20") @QueryParam("limit") int limit;

	
	public UriInfo getUriInfo() { return uriInfo; }
	
	
	public void setUriInfo(UriInfo uriInfo) { this.uriInfo = uriInfo; }
	
	
	public int getLimit() { return limit; }


	public void setLimit(int limit) { this.limit = limit; }


	public String createSelfLink(String organizationID) {
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path(OrganizationResource.class);
	
		if (organizationID != null) { uriBuilder.path(organizationID); }
		uriBuilder.build();
		
		return uriBuilder.toString();
	}
	
	/*
	public String createResourcesLink() {
		String uri = uriInfo.getBaseUriBuilder().path(OrganizationResource.class)
		.path("{organizationID}")
		.build()
		.toString();
		
		return uri;		
	}
	*/
}
