package com.example.aws.s3;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.HeadBucketRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import software.amazon.ion.IonException;

@Service
public class S3Service {

	@Value("${AWS.bucketName}")
	private String awsBucketName;
//	private String awsBucketName = "javas3bucket";
	
	@Autowired
	private AmazonS3 s3Client;
	
	public void createBucket()
	{
		try 
		{
			if(s3Client.doesBucketExistV2(awsBucketName))
				System.out.println("Bucket Already Exist... Please choose different BUCKET NAME :-)");
			else {
				s3Client.createBucket(awsBucketName);
				System.out.println("Bucket created Successfully");
				}
			} catch(Exception ex) {
			System.out.println(ex.getMessage());
			}
	}
	
	public byte[] dowloadFile(String fileName)
	{
		S3Object s3Object = s3Client.getObject(new GetObjectRequest(awsBucketName, fileName));
		S3ObjectInputStream inputStream = s3Object.getObjectContent();
		try {
			byte[] content = IOUtils.toByteArray(inputStream);
			return content;
		}catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		return null;
	}
	
	public String deleteFile(String fileName)
	{
		s3Client.deleteObject(awsBucketName, fileName);
		return "file delted";
	}
		
	public String uploadFile(MultipartFile file)
	{
		try {
			if(s3Client.headBucket(new HeadBucketRequest(awsBucketName))!=null)
			{
				String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
				s3Client.putObject(new PutObjectRequest(awsBucketName, fileName, convertMultiPartFile(file)));
				
				return "uploaded success";
			}else {
				createBucket();
				uploadFile(file);
				
				return "uploaded success";
			}
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
			return "uploaded failed";
		}
	}
	
	private File convertMultiPartFile(MultipartFile file)
	{
		File convertedFile = new File(file.getOriginalFilename());
		try(FileOutputStream fos = new FileOutputStream(convertedFile))
		{
			fos.write(file.getBytes());
		} catch(IOException ex) {
			System.out.println(ex.getMessage());
		}
		
		return convertedFile;
	}
}
