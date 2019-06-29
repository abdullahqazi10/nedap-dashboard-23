package utwente.nedap.team23.nedapDashboard.security;

import java.security.Principal;
import javax.ws.rs.core.SecurityContext;


/**
 * Security context that is provided to the JAX-RS (Jersey) framework
 * such that the role-based authorization can be conducted.
 */
public class CustomSecurityContext implements SecurityContext {

	// Account has to much information.......
	private AuthenticationTokenPayload claim;
	private final String authScheme = SecurityContext.BASIC_AUTH;


	public CustomSecurityContext(AuthenticationTokenPayload claim) { this.claim = claim; }

	/**
	 * Returns the user principal, i.e information about the user/account.
	 */
	@Override
	public Principal getUserPrincipal() { return claim; }

	/**
	 * Returns the role of the user/account.
	 */
	@Override
	public boolean isUserInRole(String role) { return claim.getRole().equalsIgnoreCase(role); }

	/**
	 * Returns a boolean indicating whether the resource request 
	 * is issued through a secured connection (HTTPS)
	 */
	@Override
	public boolean isSecure() { return false; }

	/**
	 * Returns the authentication scheme being used in the authentication process.
	 */
	@Override
	public String getAuthenticationScheme() { return authScheme; }
}
