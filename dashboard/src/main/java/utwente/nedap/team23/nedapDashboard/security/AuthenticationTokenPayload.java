package utwente.nedap.team23.nedapDashboard.security;

import java.security.Principal;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Object that represents the payload of a JSON Web Token (JWT).
 */
public final class AuthenticationTokenPayload implements Principal {
	
    private final String sub;
    private final String name;
    private final String iss;
    private final String role;
    private final String organization;
    private final long iat;
    private final long exp;

    private AuthenticationTokenPayload(String id, String name, String issuedBy, String role, 
    		String organization, long issuedDate, long expirationDate) {
        this.sub = id;
        this.name = name;
        this.iss = issuedBy;
        this.role = role;
        this.organization = organization;
        this.iat = issuedDate;
        this.exp = expirationDate;
    }
    
    public String getOrganization() { return organization; }

	  
    public String getSub() { return sub; }

	
    public String getIssuer() { return iss; }

	
    public String getRole() { return role; }

	
    public long getIat() { return iat; }

	
    public long getExp() { return exp; }
    
    
	public String getName() {  return name; }

        
    public String toJson() throws JsonProcessingException {
    	
    	ObjectMapper obj = new ObjectMapper();
    	String jsonClaim = obj.writeValueAsString(this); 
    	
    	return jsonClaim;
    }

    
    /**
     * Builder for <code>AuthenticationTokenDetails</code>
     */
    public static class Builder {

        private String sub;
        private String issuedBy;
        private String username;
        private String role; 
        private String organization;
        private long iat;
        private long exp;

        public Builder withSub(String id) {
            this.sub = id;
            return this;
        }

        public Builder withUsername(String username) {
            this.username = username;
            return this;
        }
        
        
        public Builder withRole(String role) {
        	this.role = role;
        	return this;
        }
        
        public Builder withOrganization(String orgID) {
        	this.organization = orgID;
        	return this;
        }
        
        public Builder withIssuedDate(long issuedDate) {
        	this.iat = issuedDate;
        	return this;
        }


        public Builder withExpirationDate(long expirationDate) {
            this.exp = expirationDate;
            return this;
        }

        public Builder withIssuer(String issuedBy) {
            this.issuedBy = issuedBy;
            return this;
        }

        public AuthenticationTokenPayload build() {
            return new AuthenticationTokenPayload(sub, username, issuedBy, 
            		role, organization, iat, exp);
        }
    }
}
  


