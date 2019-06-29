package utwente.nedap.team23.nedapDashboard.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BatchJobExecutionParams {
	
	private long jobExecutionID;
	private String typeCD;
	private String keyName;
	private String stringVal;
	private String dateVal;
	private long longVal;
	private double doubleVal;
	private String identifying;
	private List<Link> links;
	
	public BatchJobExecutionParams() {}

	public long getJobExecutionID() { return jobExecutionID; }
	

	public void setJobExecutionID(long jobExecutionID) { this.jobExecutionID = jobExecutionID; }
	

	public String getTypeCD() { return typeCD; }
	

	public void setTypeCD(String typeCD) { this.typeCD = typeCD; }
	

	public String getKeyName() { return keyName; }
	

	
	public void setKeyName(String keyName) { this.keyName = keyName; }

	
	public String getStringVal() { return stringVal; }

	
	public void setStringVal(String stringVal) { this.stringVal = stringVal; }

	
	public String getDateVal() { return dateVal; }

	
	public void setDateVal(String dateVal) { this.dateVal = dateVal; }

	
	public long getLongVal() { return longVal; }

	
	public void setLongVal(long longVal) { this.longVal = longVal; }

	
	public double getDoubleVal() { return doubleVal; }

	
	public void setDoubleVal(double doubleVal) { this.doubleVal = doubleVal; }

	
	public String getIdentifying() { return identifying; }

	
	public void setIdentifying(String identifying) { this.identifying = identifying; }
	
	
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

