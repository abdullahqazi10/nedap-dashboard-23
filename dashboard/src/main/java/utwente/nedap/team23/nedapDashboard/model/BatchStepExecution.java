package utwente.nedap.team23.nedapDashboard.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
//XML parser cannot read Date-objects
public class BatchStepExecution {
	
	private long stepExecutionID;
	private long version;
	private String stepName;
	private long jobExecutionID;
	private String startTime;
	private String endTime;
	private String status;
	private long commitCount;
	private long readCount;
	private long filterCount;
	private long writeCount;
	private long readSkipCount;
	private long writeSkipCount;
	private long processSkipCount;
	private long rollBackCount;
	private String exitCode;
	private String exitMessage;
	private String lastUpdated;
	private BatchStepExecutionContext stepContext;
	private List<Link> links;
	
	public BatchStepExecution() {}

	public long getStepExecutionID() { return stepExecutionID; }

	
	public void setStepExecutionID(long stepExecutionID) { this.stepExecutionID = stepExecutionID; }

	
	public long getVersion() { return version; }

	
	public void setVersion(long version) { this.version = version; }

	
	public String getStepName() { return stepName; }

	
	public void setStepName(String stepName) { this.stepName = stepName; }

	
	public long getJobExecutionID() { return jobExecutionID; }

	
	public void setJobExecutionID(long jobExecutionID) { this.jobExecutionID = jobExecutionID; }

	
	public String getStartTime() { return startTime; }

	
	public void setStartTime(String startTime) { this.startTime = startTime; }

	
	public String getEndTime() { return endTime; }

	
	public void setEndTime(String endTime) { this.endTime = endTime; }

	
	public String getStatus() { return status; }

	
	public void setStatus(String status) { this.status = status; }

	
	public long getCommitCount() { return commitCount; }

	
	public void setCommitCount(long commitCount) { this.commitCount = commitCount; }

	
	public long getReadCount() { return readCount; }

	
	public void setReadCount(long readCount) { this.readCount = readCount; }

	
	public long getFilterCount() { return filterCount; }

	
	public void setFilterCount(long filterCount) { this.filterCount = filterCount; }

	
	public long getWriteCount() { return writeCount; }

	
	public void setWriteCount(long writeCount) { this.writeCount = writeCount; }

	
	public long getReadSkipCount() { return readSkipCount; }

	
	public void setReadSkipCount(long readSkipCount) { this.readSkipCount = readSkipCount; }

	
	public long getWriteSkipCount() { return writeSkipCount; }

	
	public void setWriteSkipCount(long writeSkipCount) { this.writeSkipCount = writeSkipCount; }

	
	public long getProcessSkipCount() { return processSkipCount; }

	
	public void setProcessSkipCount(long processSkipCount) { this.processSkipCount = processSkipCount; }

	
	public long getRollBackCount() { return rollBackCount; }

	
	public void setRollBackCount(long rollBackCount) { this.rollBackCount = rollBackCount; }

	
	public String getExitCode() { return exitCode; }

	
	public void setExitCode(String exitCode) { this.exitCode = exitCode; }

	
	public String getExitMessage() { return exitMessage; }

	
	public void setExitMessage(String exitMessage) { this.exitMessage = exitMessage; }

	
	public String getLastUpdated() { return lastUpdated; }

	
	public void setLastUpdated(String lastUpdated) { this.lastUpdated = lastUpdated; }
	
	
	public BatchStepExecutionContext getContext() { return stepContext; }

	public void setContext(BatchStepExecutionContext context) { this.stepContext = context; }
	

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
