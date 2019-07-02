package utwente.nedap.team23.nedapDashboard.security;

import java.io.IOException;

import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import org.jose4j.lang.JoseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import utwente.nedap.team23.nedapDashboard.database.ResourceDAO;
import utwente.nedap.team23.nedapDashboard.model.Account;

public class TokenServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
			throws ServletException, IOException {

		String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
		if (authHeader != null && authHeader.substring(0, 5).equals("Basic")) {
			BasicAuthentication authenticator = new BasicAuthentication();
			Long user = authenticator.authenticate(authHeader);
			if (user != null) {
				try {

					ResourceDAO rService = ResourceDAO.instance;
					Account acc = rService.getAccountOf(user);
					AuthenticationTokenService tkService = new AuthenticationTokenService();
					String jwt = tkService.issueToken(acc);
					httpResponse.setStatus(204);
					httpResponse.setHeader("Bearer", jwt);	
					return;

				} catch(SQLException | JoseException | JsonProcessingException e) {
					
					e.printStackTrace();
					httpResponse.sendError(500, "Sorry a mistake happened. Try it later again!");
					return;			
				}
			} else {
				
				System.out.println("Unauthorized request or wrong credentials. \n" + httpRequest.getPathInfo());
				httpResponse.sendError(403, "Username or password is wrong.");
				return;
			}
		} else if (authHeader != null && authHeader.substring(0, 6).equals("Bearer")) {
			
	        RequestDispatcher rd = httpRequest.getRequestDispatcher("Jersey Web Application");  
	        rd.forward(httpRequest, httpResponse);  
	        return;	
		} else {
			
			System.out.println("Unauthorized request. \n" + httpRequest.getPathInfo());
			httpResponse.sendError(401, "You have no access to this resource!");
			return;
		}
	}		
}
