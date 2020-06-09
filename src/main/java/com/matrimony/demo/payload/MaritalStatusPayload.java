package com.matrimony.demo.payload;

public class MaritalStatusPayload {
	
	private String maritalStatus;
	
	private int count;

	public MaritalStatusPayload(String maritalStatus, int count) {
		super();
		this.maritalStatus = maritalStatus;
		this.count = count;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	
	
}
