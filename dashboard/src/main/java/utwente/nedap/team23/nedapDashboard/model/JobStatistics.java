package utwente.nedap.team23.nedapDashboard.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JobStatistics {
	
	private long total;
	private long completed;
	private long failed;
	private long ongoing;
	private long stopped;
	private long unknown;
	private int start;
	private int end;
	
	
	public long getTotal() { return total; }


	public void setTotal(long total) { this.total = total; }


	public long getCompleted() { return completed; }
	
	
	public void setCompleted(long completed) { this.completed = completed; }
	
	
	public long getFailed() { return failed; }
	
	
	public void setFailed(long failed) { this.failed = failed; }
	
	
	public long getOngoing() { return ongoing; }
	
	
	public void setOngoing(long ongoing) { this.ongoing = ongoing; }
	
	
	public long getStopped() { return stopped; }


	public void setStopped(long stopped) { this.stopped = stopped; }


	public long getUnknown() { return unknown; }


	public void setUnknown(long unknown) { this.unknown = unknown; }


	public int getStart() { return start; }
	
	
	public void setStart(int start) { this.start = start; }
	
	
	public int getEnd() { return end; }
	
	
	public void setEnd(int end) { this.end = end; }
}
