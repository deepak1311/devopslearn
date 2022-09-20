package com.example.aws.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AwsS3Config {
	
	@Value("${AWS.accessKey}")
	private String awsAccessKey;
//	private String awsAccessKey = "AKIA3XW5YABABAFQPJVR";
	
	@Value("${AWS.secretKey}")
	private String awsSecretKey;
//	private String awsSecretKey = "+VrnJLUCWtUrODvN8420wE014FrIds/JDY+5wp61";
	
	@Value("${aws.region}")
	private String awsBucketRegion;
//	private String awsBucketRegion = "us-west-2";
	
	@Bean
	public AmazonS3 s3Client()
	{
		AWSCredentials cred = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(cred))
				.withRegion(awsBucketRegion)
				.build();
	}
	
//	@Bean
//	public void bucketCreation()
//	{
//		try 
//		{
//			if(s3Client.headBucket(new HeadBucketRequest(awsBucketName))!=null)
//				System.out.println("Bucket Available in your account");
//			else if(s3Client.doesBucketExistV2(awsBucketName))
//				System.out.println("Bucket Already Exist... Please choose different BUCKET NAME :-)");
//			else {
//				s3Client.createBucket(awsBucketName);
//				System.out.println("Bucket created Successfully");
//				}
//			} catch(Exception ex) {
//			System.out.println(ex.getMessage());
//			}
//		}
}
