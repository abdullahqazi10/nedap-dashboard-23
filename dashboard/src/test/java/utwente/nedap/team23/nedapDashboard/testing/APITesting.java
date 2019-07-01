package utwente.nedap.team23.nedapDashboard.testing;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.internal.util.Base64;
import org.junit.Before;

public abstract class APITesting {

	public static final String DOMAIN = "http://localhost:8080/";
	public static final String WRONGCREDENTIALS = Base64.encodeAsString(("118824:wrong".getBytes()));
	public static final String TECHNICIAN = Base64.encodeAsString("118824:password1".getBytes());
	public static final String CUSTOMER = Base64.encodeAsString("101010:password5".getBytes());
	
	
	protected Client client;
	protected WebTarget restTarget;
	protected String jwtTechnician;		// once we have one its valid for 8 hrs
	
	
	public void setup() {
		
		client = ClientBuilder.newClient();
		restTarget = client.target(DOMAIN + "di23_dashboard/rest/v1/");
		Response response = client.target(DOMAIN + "di23_dashboard/v1/oauth/token")		// get Token
				.request().header("Authorization", "Basic " + TECHNICIAN).get();
		
		jwtTechnician = "Bearer " + response.getHeaderString("Bearer"); 						// without token no access to any of the resources
		
	}
}
