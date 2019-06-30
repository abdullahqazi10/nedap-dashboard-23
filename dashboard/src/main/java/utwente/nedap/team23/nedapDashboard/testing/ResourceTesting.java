package utwente.nedap.team23.nedapDashboard.testing;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Test;
import utwente.nedap.team23.nedapDashboard.model.BatchJobExecution;
import utwente.nedap.team23.nedapDashboard.model.BatchJobInstance;
import utwente.nedap.team23.nedapDashboard.model.Organization;


public class ResourceTesting extends APITesting {
	
	
	@Test
	public void organizationsTesting() {
		
		WebTarget orgTarget = restTarget.path("organizations");
		
		Response response = orgTarget.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		ArrayList<?> organizations = response.readEntity(ArrayList.class);
		assertEquals(6, organizations.size());  // scope of project just 6
		
		response = orgTarget.path("88888").request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		Organization riverIsland = response.readEntity(Organization.class);
		assertEquals("88888", riverIsland.getOrganizationID());
	}
	
	
	
	@Test
	public void jobsTest() {
		
		WebTarget jobTarget = restTarget.path("organizations/22724/jobs");
		
		Response response = jobTarget.queryParam("start", "2019-03-24")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		ArrayList<?> jobs = response.readEntity(ArrayList.class);
		assertEquals(11, jobs.size());
		
		response = jobTarget.path("4764384")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		BatchJobInstance instance = response.readEntity(BatchJobInstance.class);
		assertEquals((long) 4764384, instance.getJobInstanceID());	
	}
	
	
	
	@Test
	public void executionsTest() {
		
		WebTarget exeTarget = restTarget.path("organizations/22724/jobs/4764384/executions");
		Response response = exeTarget.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		ArrayList<?> executions = response.readEntity(ArrayList.class);
		assertEquals(4, executions.size());
	}
	
	
	@Test
	public void hateoasTest() {
		
		// Get execution
		WebTarget exeTarget = restTarget.path("organizations/22724/jobs/4764384/executions/4809215");
		Response response = exeTarget.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		BatchJobExecution execution = response.readEntity(BatchJobExecution.class);
		// extract link to step execution from all links
		String stepExeUri = execution.getLinks().get(2).getLink();
		System.out.println(stepExeUri);
		response = client.target(stepExeUri).request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		// array of steps
		ArrayList<?> exeSteps = response.readEntity(ArrayList.class);
		assertEquals(2, exeSteps.size());
	}	
}
