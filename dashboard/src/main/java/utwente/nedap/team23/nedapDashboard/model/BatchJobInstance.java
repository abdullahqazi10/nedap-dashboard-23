package utwente.nedap.team23.nedapDashboard.model;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.DenyAll;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BatchJobInstance {
	
	private long jobInstanceID;
	private long version;
	private String jobName;
	private String jobKey;
	private String status;
	private String orgID;
	private String from;
	private List<Link> links;
	
	public BatchJobInstance() {}

	
	public long getJobInstanceID() { return jobInstanceID; }

	
	public void setJobInstanceID(long jobInstanceID) { this.jobInstanceID = jobInstanceID; }


	public long getVersion() { return version; }


	public void setVersion(long version) { this.version = version; }


	public String getJobName() { return jobName; }


	public void setJobName(String jobName) { this.jobName = jobName; }

	
	public String getJobKey() { return jobKey; }


	public void setJobKey(String jobKey) { this.jobKey = jobKey; }
	
	
	public String getStatus() { return status; }


	public void setStatus(String status) { this.status = status; }


	public List<Link> getLinks() { return links; }


	public void setLinks(List<Link> links) { this.links = links; }
	
	
	public String getOrgID() { return orgID; }

	
	public void setOrgID(String orgID) { this.orgID = orgID; }


	public String getOrganizationName() { return from; }


	public void setOrganizationName(String from) { this.from = from; }


	public void addLink(String rel, String uri) {
		
		if (links == null) { links = new ArrayList<>(); }
		
		Link link = new Link();
		link.setRel(rel);
		link.setLink(uri);
		links.add(link);
	}	
}
