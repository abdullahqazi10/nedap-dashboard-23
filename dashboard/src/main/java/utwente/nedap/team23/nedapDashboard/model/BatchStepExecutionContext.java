package utwente.nedap.team23.nedapDashboard.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BatchStepExecutionContext {
	
	private long stepExecutionID;
	private String shortContext;
	private String serializedContext;
	private List<Link> links;
	
	public BatchStepExecutionContext() {}

	
	public long getStepExecutionID() { return stepExecutionID; }

	
	public void setStepExecutionID(long stepExecutionID) { this.stepExecutionID = stepExecutionID; }

	
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
