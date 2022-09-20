package com.example.aws.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
public class AwsS3Config {
	
	@Value("${AWS.accessKey}")
	private String awsAccessKey;
//	private String awsAccessKey = "AKIA3XW5YABABAFQPJVR";
	
	@Value("${AWS.secretKey}")
	private String awsSecretKey;
//	private String awsSecretKey = "+VrnJLUCWtUrODvN8420wE014FrIds/JDY+5wp61";
	
	@Value("${AWS.s3.region}")
	private String awsBucketRegion;
//	private String awsBucketRegion = "us-west-2";
	
	@Value("${AWS.queue.region}")
	private String awsSqsRegion;
	
	@Value("${AWS.queue.name}")
	private String awsQueueName;
	
	@Value("${AWS.queue.url}")
	private String awsQueueURL;
	
	@Value("${AWS.sns.region}")
	private String awsSNSRegion;

//	@Bean
//	public AmazonS3 s3Client()
//	{
//		AWSCredentials cred = new BasicAWSCredentials(awsAccessKey, awsSecretKey);
//		return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(cred))
//				.withRegion(awsBucketRegion)
//				.build();
//	}
	
//	@Bean
//	public QueueMessagingTemplate queueMessagingTemplate()
//	{
//		return new QueueMessagingTemplate(amazonSQSAsync());
//	}
//	
//	@Primary
//	@Bean
//	public AmazonSQSAsync amazonSQSAsync() {
//		return AmazonSQSAsyncClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
//				.withRegion(awsSqsRegion)
//				.build();
//	}
	
	@Primary
	@Bean
	public AmazonSNSClient getSNSClient()
	{
		return (AmazonSNSClient) AmazonSNSClientBuilder.standard().withRegion(awsSNSRegion)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey, awsSecretKey)))
				.build();
	}
}
