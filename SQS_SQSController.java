package com.example.aws.sqs;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sqs")
public class SQSController {
	
	@Value("${AWS.queue.url}")
	private String awsQueueURL;

	@Autowired
	private QueueMessagingTemplate messageTemplate;
	
	@GetMapping("/sendMessage")
	public void sendMessageToQueue(@RequestParam("message") String message)
	{
		messageTemplate.send(awsQueueURL, MessageBuilder.withPayload(message).build());
	}
	
	@SqsListener("javaqueue")
	public void loadSqsMessage(String message) throws InterruptedException
	{
		List<String> list = new ArrayList<String>();
		list.add(message);
		Thread.sleep(5000);
		System.out.println(list);
	}
}
