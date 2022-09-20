package com.example.aws.sns;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;

@RestController
@RequestMapping("/sns")
public class SNSController {

	private String topicARn = "arn:aws:sns:us-west-2:806845153344:javasns";
	
	@Autowired
	private AmazonSNSClient snsClient;
	
	@RequestMapping("/addSubscription")
	public String addSubscription(@RequestParam("email") String email)
	{
		SubscribeRequest request = new SubscribeRequest(topicARn, "email", email);
		snsClient.subscribe(request);
		return "Subscription is pending to confirm please check your mail";
	}
	
	@GetMapping("/sendNotification")
	public String publishMessageToTopic()
	{
		PublishRequest publish = new PublishRequest(topicARn, "Hi welcome" , "Subject : Welcome Mail");
		snsClient.publish(publish);
		return "message published";
	}
}
