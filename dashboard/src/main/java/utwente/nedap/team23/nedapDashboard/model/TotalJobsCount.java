package utwente.nedap.team23.nedapDashboard.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TotalJobsCount {
	
	private long count;

	public long getCount() { return count; }

	public void setCount(long count) { this.count = count; }
}
