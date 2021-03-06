package utwente.nedap.team23.nedapDashboard.testing;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class StatusCodesTesting extends APITesting {
	
	
	@Before
	public void testSetup() {
		super.setup();
	}
	
	@Test
	public void statusCode200Test() {
		
		// Some calls to endpoints
		Response response = restTarget.path("organizations")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		assertEquals(200, response.getStatus());
		
		response = restTarget.path("organizations/jobs")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		assertEquals(200, response.getStatus());
		
		response = restTarget.path("organizations/21844/jobs/4898794/executions/"
				+ "4943633/step-executions")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		assertEquals(200, response.getStatus());
		
	}
	
	
	
	@Test
	// No content.... if hard credentials correct - request for token
	public void statusCode204Test() {
		
		Response response = client.target(DOMAIN + "di23_dashboard/v1/oauth/token")
				.request().header("Authorization", "Basic " + TECHNICIAN).get();
		assertEquals(204, response.getStatus());
		
	}
	
	
	@Test
	// Unauthorized if request without token or without hard credentials
	public void statusCode401Test() {
		
		// without credentials
		Response response = client.target(DOMAIN + "di23_dashboard/v1/oauth/token")
				.request().get();
		assertEquals(401, response.getStatus());
		
		// without token
		response = restTarget.path("organizations/accounts").request().get();
		assertEquals(401, response.getStatus());
		
		
	}
	
	@Test
	// Forbidden if request with incorrect credentials
	public void statusCode403Test() {
		
		// with wrong credentials
		Response response = client.target(DOMAIN + "di23_dashboard/v1/oauth/token")
				.request().header("Authorization", "Basic " + WRONGCREDENTIALS).get();
		assertEquals(response.getStatus(), 403);
	}
}
