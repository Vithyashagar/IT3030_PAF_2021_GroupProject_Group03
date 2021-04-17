package model;

public class Concept {
	
	//Attributes
	private int conceptID;
	private String conceptNo;
	private String conceptName;
	private String conceptDesc;
	private String startDate;
	private String deadline;
	private double pledgeGoal;
	private String reward;
	private String status;
	private String workUpdt;
	private String researcherID;
	private String manufactID;
	
	
	//Setters
	public void setConceptID(int conceptID) {
		this.conceptID = conceptID;
	}
	
	public void setConceptNo(String conceptNo) {
		this.conceptNo = conceptNo;
	}
	
	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}
	
	public void setConceptDesc(String conceptDesc) {
		this.conceptDesc = conceptDesc;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	
	public void setPledgeGoal(double pledgeGoal) {
		this.pledgeGoal = pledgeGoal;
	}
	
	public void setReward(String reward) {
		this.reward = reward;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setWorkUpdt(String workUpdt) {
		this.workUpdt = workUpdt;
	}
	
	public void setResearcherID(String researcherID) {
		this.researcherID = researcherID;
	}
	
	public void setManufactID(String manufactID) {
		this.manufactID = manufactID;
	}

	
	
	//Getters
	public int getConceptID() {
		return conceptID;
	}

	public String getConceptNo() {
		return conceptNo;
	}

	public String getConceptName() {
		return conceptName;
	}

	public String getConceptDesc() {
		return conceptDesc;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getDeadline() {
		return deadline;
	}

	public double getPledgeGoal() {
		return pledgeGoal;
	}

	public String getReward() {
		return reward;
	}

	public String getStatus() {
		return status;
	}

	public String getWorkUpdt() {
		return workUpdt;
	}

	public String getResearcherID() {
		return researcherID;
	}

	public String getManufactID() {
		return manufactID;
	}
	
	
	
	
	
	

}
