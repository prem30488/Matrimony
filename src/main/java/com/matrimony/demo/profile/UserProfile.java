package com.matrimony.demo.profile;

import java.util.Objects;
import java.util.Optional;

public class UserProfile {
	private Long userProfileId;
	private String username;
	private String userProfileImageLink; //S3 key
	
	public UserProfile(Long long1, String username, String userProfileImageLink) {
		super();
		this.userProfileId = long1;
		this.username = username;
		this.userProfileImageLink = userProfileImageLink;
	}

	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Optional<String> getUserProfileImageLink() {
		return Optional.ofNullable(userProfileImageLink);
	}

	public void setUserProfileImageLink(String userProfileImageLink) {
		this.userProfileImageLink = userProfileImageLink;
	}

	@Override
	public boolean equals(Object o) {
		if(this==o) {
			return true;
		}
		if(o==null || getClass() != o.getClass()) {
			return false;
		}
		UserProfile that = (UserProfile) o;
		
		return Objects.equals(userProfileId, that.userProfileId) &&
				Objects.equals(username, that.username) &&
				Objects.equals(userProfileImageLink, that.userProfileImageLink);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(userProfileId,username,userProfileImageLink);
		/*
		 * final int prime = 31; int result = 1; result = prime * result +
		 * ((userProfileId == null) ? 0 : userProfileId.hashCode()); result = prime *
		 * result + ((userProfileImageLink == null) ? 0 :
		 * userProfileImageLink.hashCode()); result = prime * result + ((username ==
		 * null) ? 0 : username.hashCode()); return result;
		 */
	}
}
