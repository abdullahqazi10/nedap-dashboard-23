package utwente.nedap.team23.nedapDashboard.model;

import java.security.Principal;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person implements Principal {
	
	private String name;
	private String surname;
	private long pid;
	
	public Person () {}

	public String getName() { return name; }

	public void setName(String name) { this.name = name; }

	public String getSurname() { return surname; }

	public void setSurname(String surname) { this.surname = surname; }
	
	public long getPersonID() { return pid; }
	
	public void setPersonID(long pid) { this.pid = pid; } 
}
