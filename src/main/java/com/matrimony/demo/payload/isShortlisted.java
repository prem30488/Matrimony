package com.matrimony.demo.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class isShortlisted {

	private Boolean isShortlisted;

	@JsonProperty("isShortlisted")
	public Boolean getIsShortlisted() {
		return isShortlisted;
	}

	public void setIsShortlisted(Boolean isShortlisted) {
		this.isShortlisted = isShortlisted;
	}
	
	
}
