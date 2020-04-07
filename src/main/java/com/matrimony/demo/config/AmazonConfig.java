package com.matrimony.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {

	@Bean
	public AmazonS3 s3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(
				"AKIAIGMJGWZBVB7DO4NQ", "iScuSIDLUzY1vgTxVD86+lc2LZdavSr9CQTsA883"
				);
		return AmazonS3ClientBuilder
				.standard()
				.withRegion("ap-south-1")
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.build();
	}
}