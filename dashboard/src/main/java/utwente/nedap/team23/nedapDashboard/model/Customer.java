package utwente.nedap.team23.nedapDashboard.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer extends Person {
	
	private String organizationID; 
	
	public Customer() {}
	
	
	public String getOrganizationID() { return organizationID; }
	
	
	public void setOrganizationID(String organizationID) { this.organizationID = organizationID; }
	
	@Override
	public String toString() {
		return super.getName() + " " + super.getSurname();
	}
}
