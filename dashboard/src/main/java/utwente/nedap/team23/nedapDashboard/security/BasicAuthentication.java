package utwente.nedap.team23.nedapDashboard.security;



import java.sql.SQLException;
import java.util.Base64;
import java.util.StringTokenizer;
import utwente.nedap.team23.nedapDashboard.database.DatabaseDAO;

// Inside of Database password will in the future be stored as hash values, mixed with a salt
// (incoming request with username:password has to be manipulated properly then))
// to avoid password (easy) decryption in case of a data(base) breach
public class BasicAuthentication {
	
	private static final String AUTHENTICATION_SCHEME = "Basic";
	
	/**
	 * Returns the ID of the person that issued a request to a resource,
	 * in case the authentication was successful.
	 * 
	 * @param authorization		username and password encoded in Base64
	 * 
	 * @return		user id
	 */
	public Long authenticate(String authorization) {
		
		final String encodedUserPassword = authorization.replaceFirst(AUTHENTICATION_SCHEME + " ", "");
		String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final Long username = Long.parseLong(tokenizer.nextToken());
		final String password = tokenizer.nextToken();
		
		try {
			DatabaseDAO dbService = new DatabaseDAO();
			final String dbPassw = dbService.getPasswordOf(username);
			Long userID = dbPassw.equals(password) ? username : null;
			
			return userID;		
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getSQLState());
		}
	}
}
