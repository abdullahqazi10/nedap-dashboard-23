package utwente.nedap.team23.nedapDashboard.model.bean;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import utwente.nedap.team23.nedapDashboard.resources.JobResource;
import utwente.nedap.team23.nedapDashboard.resources.OrganizationResource;

// Probably gonna construct an abstract in order to reduce redundancy
public class ExecutionBean {

	@PathParam("jobInstanceID") Long instanceID;			// always just one given
	@PathParam("organizationID") String organizationID;		// ""
	@QueryParam("parameters") boolean params;
	@QueryParam("context") boolean context;
	@QueryParam("status") String status;
	private @Context UriInfo uriInfo;

	public UriInfo getUriInfo() { return uriInfo; }


	public void setUriInfo(UriInfo uriInfo) { this.uriInfo = uriInfo; }
	
	
	public long getInstanceID() { return instanceID; }


	public void setInstanceID(long instanceID) { this.instanceID = instanceID; }
	

	public String getOrganizationID() { return organizationID; }


	public void setOrganizationID(String organizationID) { this.organizationID = organizationID; }


	public void setInstanceID(Long instanceID) { this.instanceID = instanceID; }


	public String getStatus() { return status; }


	public void setStatus(String status) { this.status = status; }
	
	public boolean isParams() { return params; }


	public void setParams(boolean params) { this.params = params; }


	public boolean isContext() { return context; }


	public void setContext(boolean context) { this.context = context; }


	public String createSelfLink(Long executionID) {
		String uri = uriInfo.getBaseUriBuilder().path(OrganizationResource.class)
		.path(OrganizationResource.class, "delegateToJobs")
		.resolveTemplate("organizationID", organizationID)
		.path(JobResource.class, "delegateTo")
		.resolveTemplate("jobInstanceID", instanceID)
		.path(Long.toString(executionID))
		.build().toString(); 

		return uri;
	}
	
	public String createJobInstanceLink() {
		
		String uri = uriInfo.getBaseUriBuilder().path(OrganizationResource.class)
		.path(OrganizationResource.class, "delegateToJobs")
		.resolveTemplate("organizationID", organizationID)
		.path(Long.toString(instanceID))
		.build()
		.toString();
		
		return uri;
	}
	
	
	public String createLinkForStepExe(Long executionID) {
		String uri = UriBuilder.fromPath(createSelfLink(executionID))
		.path("step-executions")
		.build().toString();
		
		return uri;
	}
	
	
	public String createLinkForContext(Long executionID) {
		
		String uri = UriBuilder.fromPath(createSelfLink(executionID))
		.path("execution-contexts")
		.build().toString();
		
		return uri;
	}
	
	
	public String createLinkForParams(Long executionID) {
		
		String uri = UriBuilder.fromPath(createSelfLink(executionID))
		.path("execution-parameters")
		.build().toString();
		
		return uri;
	}	
}
