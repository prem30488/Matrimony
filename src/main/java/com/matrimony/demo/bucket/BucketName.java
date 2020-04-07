package com.matrimony.demo.bucket;

public enum BucketName {
	
	PROFILE_IMAGE("matrimony-images");

	private final String bucketName;
	
	BucketName(String bucketName){
		this.bucketName = bucketName;
	}
	
	public String getBucketName() {
		return bucketName;
	}
}
