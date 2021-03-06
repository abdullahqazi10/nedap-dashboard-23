package utwente.nedap.team23.nedapDashboard.testing;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class SecurityTesting extends APITesting {
	
	private String jwtCustomer;
	
	@Before
	public void SecurityTesting() {
		super.setup();
		// Request token as Customer
		restTarget = client.target(DOMAIN + "di23_dashboard/rest/v1/");
		Response response = client.target(DOMAIN + "di23_dashboard/v1/oauth/token")		
				.request().header("Authorization", "Basic " + CUSTOMER).get();
		jwtCustomer = "Bearer " + response.getHeaderString("Bearer");

	}


	@Test
	public void UnauthorizedAccess() {
		
		// without credentials
		Response response = client.target(DOMAIN + "di23_dashboard/v1/oauth/token")
				.request().get();
		assertEquals(401, response.getStatus());
		
		// without token
		response = restTarget.path("organizations/accounts").request().get();
		assertEquals(401, response.getStatus());		
	}
	
	
	@Test 
	public void ForbiddenAccess() {
		
		// JSON Web Token taken from Postman history which is not valid anymore
		String jwtTech = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMTg4MjQiLCJuYW1lIjoiSn"
				+ "VsaWFuIE5hdmFycm8iLCJyb2xlIjoidGVjaG5pY2lhbiIsIm9yZ2FuaXphd"
				+ "GlvbiI6bnVsbCwiaWF0IjoxNTYxNjg2OTY0LCJleHAiOjE1NjE3MTU3NjQsIm"
				+ "lzc3VlciI6Ik5lZGFwIn0.hStRzLCHtOa8jfHoAVMMU0WWG9KPUp963PTnC5lAjAg";
		
		Response response = restTarget.path("organizations/jobs")  // resource only acc. to tech/support
				.request(MediaType.APPLICATION_JSON).header("Authorization", jwtTech).get();
		assertEquals(403, response.getStatus());
		
		// Token request with wrong credentials
		response = client.target(DOMAIN + "di23_dashboard/v1/oauth/token")
				.request().header("Authorization", "Basic " + WRONGCREDENTIALS).get();
		assertEquals(response.getStatus(), 403);
		
		// Access to resources only accessible to technicians and support
		response = restTarget.path("organizations/jobs") 
				.request(MediaType.APPLICATION_JSON).header("Authorization", jwtCustomer).get();
		assertEquals(403, response.getStatus());
	}
	
	
	@Test
	public void AccessGranted() {
		
		// Some calls to endpoints
		Response response = restTarget.path("organizations/12345/accounts/5")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		System.out.println(response.getStatus());
		assertEquals(200, response.getStatus());
		
		response = restTarget.path("organizations/jobs")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtTechnician).get();
		System.out.println(response.getStatus());
		assertEquals(200, response.getStatus());
		
		response = restTarget.path("organizations/21844/jobs/4898812/executions/")
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", jwtCustomer).get();
		System.out.println(response.getStatus());
		assertEquals(200, response.getStatus());	
	}
}
