package utwente.nedap.team23.nedapDashboard.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Staff extends Person {
	 
	private String birthDate;
	private String email;
	
	public Staff() {}
	

	public String getBirthDate() { return birthDate; }


	public void setBirthDate(String birthDate) { this.birthDate = birthDate; }


	public String getEmail() { return email; }


	public void setEmail(String email) { this.email = email; }
	
	@Override
	public String toString() {
		return super.getName() + " " + super.getSurname();
	}
}
