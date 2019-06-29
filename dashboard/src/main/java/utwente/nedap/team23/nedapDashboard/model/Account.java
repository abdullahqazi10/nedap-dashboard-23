package utwente.nedap.team23.nedapDashboard.model;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account implements Principal {

	private long account_id;
	private String created;
	private Person owner;
	private String role;
	private List<Link> links;
	
	public Account() {}

	public long getAccount_id() { return account_id; }

	
	public void setAccount_id(long account_id) { this.account_id = account_id; }

	
	public String getCreated() { return created; }

	
	public void setCreated(String created) { this.created = created; }

	
	public Person getOwner() { return owner; }

	
	public void setOwner(Person owner) { this.owner = owner; }

	
	public String getRole() { return role; }

	
	public void setRole(String role) { this.role = role; }

	
	public List<Link> getLinks() { return links; }

	
	public void setLinks(List<Link> links) { this.links = links; }
	
	
	public void addLink(String rel, String uri) {
		
		if (links == null) { links = new ArrayList<>(); }
		
		Link link = new Link();
		link.setRel(rel);
		link.setLink(uri);
		links.add(link);
	}
	
	public enum Role {
		TECHNICIAN, SUPPORT, CUSTOMER
	}

	@Override
	public String getName() { return owner.getName() + " " + owner.getSurname(); }	
}