package utwente.nedap.team23.nedapDashboard.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BatchJobExecutionContext {
	
	private long jobExecutionID;
	private String shortContext;
	private String serializedContext;
	private List<Link> links;
	
	public BatchJobExecutionContext() {}

	public long getJobExecutionID() { return jobExecutionID; }

	
	public void setJobExecutionID(long jobExecutionID) { this.jobExecutionID = jobExecutionID; }

	
	public String getShortContext() { return shortContext; }

	
	public void setShortContext(String shortContext) { this.shortContext = shortContext; }

	
	public String getSerializedContext() { return serializedContext; }

	
	public void setSerializedContext(String serializedContext) { this.serializedContext = serializedContext; }
	
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
