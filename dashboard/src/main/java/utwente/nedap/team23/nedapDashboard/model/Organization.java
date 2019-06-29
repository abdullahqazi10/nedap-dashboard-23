package utwente.nedap.team23.nedapDashboard.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Organization {

	private String organizationID;
	private String name;
	private String email;
	private String contact_no;
	private List<Link> links;
	
	public Organization() {}

	
	public String getOrganizationID() { return organizationID; }

	
	public void setOrganizationID(String organizationID) { this.organizationID = organizationID; }

	
	public String getName() { return name; }

	
	public void setName(String name) { this.name = name; }

	
	public String getEmail() { return email; }

	
	public void setEmail(String email) { this.email = email; }

	
	public String getContact_no() { return contact_no; }

	
	public void setContact_no(String contact_no) { this.contact_no = contact_no; }
	
	
	public List<Link> getLinks() { return links; }

	
	public void setLinks(List<Link> links) { this.links = links; }
	
	
	public void addLink(String rel, String uri) {
		
		if (links == null) { links = new ArrayList<>(); }
		
		Link link = new Link();
		link.setRel(rel);
		link.setLink(uri);
		links.add(link);
	}
}
