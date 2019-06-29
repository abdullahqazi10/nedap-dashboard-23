package utwente.nedap.team23.nedapDashboard.model.bean;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import utwente.nedap.team23.nedapDashboard.resources.ExecutionResource;
import utwente.nedap.team23.nedapDashboard.resources.JobResource;
import utwente.nedap.team23.nedapDashboard.resources.OrganizationResource;

public class StepExecutionBean {
	
	private @PathParam("organizationID") String orgID;
	private @PathParam("jobInstanceID") long instanceID;
	private @PathParam("executionID") long exeID;
	private @QueryParam("status") String status;
 	private @Context UriInfo uriInfo;
	
	public long getExecutionID() { return exeID; }
	

	public String getStatus() { return status; }

	
	public void setStatus(String status) { this.status = status; }

	
	public String createSelfLink() {
		
		String uri = uriInfo.getBaseUriBuilder().path(OrganizationResource.class)
		.path(OrganizationResource.class, "delegateToJobs")
		.resolveTemplate("organizationID", orgID)
		.path(JobResource.class, "delegateTo")
		.resolveTemplate("jobInstanceID", instanceID)
		.path(ExecutionResource.class, "delegateToStepExe")
		.resolveTemplate("executionID", exeID)
		.toString();
		
		return uri;
	}
}
