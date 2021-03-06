package utwente.nedap.team23.nedapDashboard.security;

import java.io.IOException;
import java.time.Month;

import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jwa.AlgorithmConstraints.ConstraintType;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.NumericDate;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.jose4j.lang.JoseException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import utwente.nedap.team23.nedapDashboard.model.Account;
import utwente.nedap.team23.nedapDashboard.model.Customer;
import utwente.nedap.team23.nedapDashboard.model.Person;



// Secret HMAC key will probably replaced by generated RSA private/public key pairs.
public class AuthenticationTokenService {

	//Secret key nobody knows! Psht..........
	private final byte[] SECRET_KEY = "Nedap Retail Dashboard 2019-----".getBytes();

	
	/**
	 * Returns a JSON Web Token (JWT) if the requester 
	 * has provided valid hard credentials.
	 * 
	 * @param userPrincipal					user principal from <code>SecurityContext</code>
	 * 
	 * @return								a JWT
	 * 
	 * @throws JoseException				thrown if the created JWT is invalid			
	 * 
	 * @throws JsonProcessingException		thrown if claim is not marshable to JSON
	 */
	public String issueToken(Account acc) throws JoseException, JsonProcessingException  {

		AuthenticationTokenPayload claims = issueTokenPayload(acc);
		JsonWebSignature jws = new JsonWebSignature();
		jws.setPayload(claims.toJson());
		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
		jws.setKey(new HmacKey(SECRET_KEY));
		jws.setDoKeyValidation(false);
		String jwt = jws.getCompactSerialization();

		return jwt;
	}
	
	

	/**
	 * Returns the claim that was provided in the JWT.
	 * Result is null in case of an exception.
	 * 
	 * @param jwt							JWT received from the request
	 * 
	 * @return								claim of the received request
	 * 
	 * @throws JoseException				if JWT is invalid
	 * 
	 * @throws InvalidJwtException			if JWT is invalid
	 * 
	 * @throws MalformedClaimException		thrown if claim was tampered
	 */
	public AuthenticationTokenPayload validateToken(String jwt)
			throws JoseException, MalformedClaimException, InvalidJwtException {

		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
				.setRequireExpirationTime() 
				.setAllowedClockSkewInSeconds(30) 
				.setVerificationKey(new HmacKey(SECRET_KEY)) 
				.setJwsAlgorithmConstraints( 
						new AlgorithmConstraints(ConstraintType.WHITELIST, 
								AlgorithmIdentifiers.HMAC_SHA256))
				.build(); 
	

			JwtClaims jwtClaims = jwtConsumer.processToClaims(jwt);		// throws exception if JWT invalid
			return issueTokenPayload(jwtClaims);
	}

	
	
	/**
	 * Returns a <code>AuthenticationTokenPayload</code> from a given account.
	 * These information will be contained in the payload of the JWT.
	 *  
	 * @param acc		<code>Account</code> of the requester in question
	 * 
	 * @return			an <code>AuthenticationTokenPayload</code>
	 * @throws JsonProcessingException 
	 */
	private AuthenticationTokenPayload issueTokenPayload(Account acc) throws JsonProcessingException {
		
		long issuedAt = NumericDate.now().getValue();
		NumericDate expirationDate = NumericDate.now();
		expirationDate.addSeconds(28800); // 8hrs - valid for one (normal) shift

		AuthenticationTokenPayload authenticationTokenDetails = 
				new AuthenticationTokenPayload.Builder()
				.withSub(Long.toString(acc.getOwner().getPersonID()))
				.withUsername(acc.getOwner().toString())
				.withIssuer("Nedap")
				.withOrganization(getOrganization(acc.getOwner()))
				.withRole(acc.getRole())
				.withIssuedDate(issuedAt)
				.withExpirationDate(expirationDate.getValue())
				.build();

		return authenticationTokenDetails;
	}
	
	
	/**
	 * Returns a <code>AuthenticationTokenPayload</code> from a given account.
	 * Information will be used for the <code>CustomSecurityContext</code> such
	 * that JAX-RS (Jersey) can check for role-based authorization.
	 *  
	 * @param claim		claims contained in the request represented by 
	 * 					<code>JwtClaims</code>
	 * @return			an <code>AuthenticationTokenPayload</code>
	 * @throws MalformedClaimException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	private AuthenticationTokenPayload issueTokenPayload(JwtClaims claim) throws MalformedClaimException {
		
		AuthenticationTokenPayload authenticationTokenDetails = 
				new AuthenticationTokenPayload.Builder()
				.withSub(claim.getSubject())
				.withIssuer(claim.getIssuer())
				.withOrganization(claim.getClaimValue("organization", String.class))
				.withRole(claim.getClaimValue("role", String.class))
				.withUsername(claim.getClaimValue("name", String.class))
				.withIssuedDate(claim.getClaimValue("iat", Long.class))
				.withExpirationDate(claim.getClaimValue("exp", Long.class))
				.build();
		
		return authenticationTokenDetails;
		
	}
	
	private String getOrganization(Person per) {
		
		String organization;
		
		if (per instanceof Customer) {
			organization = ((Customer) per).getOrganizationID();
		} else {
			organization = "Nedap";
		}
		return organization;
	}
	
	
	// Testing
	public static void main(String[] args) 
			throws JoseException, MalformedClaimException, IOException, InvalidJwtException {

//		Account principal = new Account();
//		Person owner = new Staff();
//		owner.setName("Julian");
//		owner.setPersonID(118824);
//		owner.setSurname("Navarro Di Pasquale");
		
//		NumericDate date = NumericDate.now();
//		date.addSeconds(120);
//		System.out.println(date);
//		principal.setAccount_id(234);
//		principal.setCreated("2019-06-24");
//		principal.setOwner(owner);
//		principal.setRole("TECHNICIAN");
//		JwtClaims claims = new JwtClaims();
//		claims.setIssuedAt(NumericDate.now());
//		claims.setExpirationTimeMinutesInTheFuture(1);
//		System.out.println(claims.toJson());		
//
//		AuthenticationTokenService service = new AuthenticationTokenService();
//		String jwt = service.issueToken(principal);
//		System.out.println(jwt);
//		AuthenticationTokenPayload valid = service.validateToken(jwt);	
//		System.out.println(valid.getName());
		
		
		System.out.println(Month.FEBRUARY.length(false));
	}
}
