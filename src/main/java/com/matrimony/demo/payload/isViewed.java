package com.matrimony.demo.payload;

import com.fasterxml.jackson.annotation.JsonProperty;

public class isViewed {

	private Boolean isViewed;

	@JsonProperty("isViewed")
	public Boolean getIsViewed() {
		return isViewed;
	}

	public void setIsViewed(Boolean isViewed) {
		this.isViewed = isViewed;
	}
	
}
