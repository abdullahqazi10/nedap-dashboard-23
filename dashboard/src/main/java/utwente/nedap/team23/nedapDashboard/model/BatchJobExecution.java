package utwente.nedap.team23.nedapDashboard.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BatchJobExecution {
	
	// XML Parser cannot read Date-objects
	private long jobExecutionID;
	private long version;
	private long jobInstanceID;
	private String createTime;			
	private String startTime;
	private String endTime;
	private String status;
	private String exitCode;
	private String exitMessage;
	private String lastUpdated;
	private List<Link> links;
	
	public BatchJobExecution() {}

	
	public long getJobExecutionID() { return jobExecutionID; }

	
	public void setJobExecutionID(long jobExecutionID) { this.jobExecutionID = jobExecutionID; }

	
	public long getVersion() { return version; }

	
	public void setVersion(long version) { this.version = version; }

	
	public long getJobInstanceID() { return jobInstanceID; }

	
	public void setJobInstanceID(long jobInstanceID) { this.jobInstanceID = jobInstanceID; }

	
	public String getCreateTime() { return createTime; }

	
	public void setCreateTime(String createTime) { this.createTime = createTime; }

	
	public String getStartTime() { return startTime; }

	
	public void setStartTime(String startTime) { this.startTime = startTime; }

	
	public String getEndTime() { return endTime; }

	
	public void setEndTime(String endTime) { this.endTime = endTime; }

	
	public String getStatus() { return status; }

	
	public void setStatus(String status) { this.status = status; }

	
	public String getExitCode() { return exitCode; }

	
	public void setExitCode(String exitCode) { this.exitCode = exitCode; }

	
	public String getExitMessage() { return exitMessage; }

	
	public void setExitMessage(String exitMessage) { this.exitMessage = exitMessage; }

	
	public String getLastUpdated() { return lastUpdated; }

	
	public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
	
	
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
