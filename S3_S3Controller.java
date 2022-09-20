package com.example.aws.s3;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
public class S3Controller {
	
	@Autowired
	private S3Service s3Service;
	
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file)
	{
		return new ResponseEntity<String>(s3Service.uploadFile(file), HttpStatus.OK);
	}
	
	@GetMapping(value = "/download", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam(value = "fileName") String fileName)
	{
		byte[] data = s3Service.dowloadFile(fileName);
		ByteArrayResource resource = new ByteArrayResource(data);
		return new ResponseEntity<ByteArrayResource>(resource, HttpStatus.OK);
	}
	
	@GetMapping("/delete")
	public ResponseEntity<String> deleteFile(@RequestBody Map<String, String> mapObject)
	{
		return new ResponseEntity<String>(s3Service.deleteFile(mapObject.get("fileName")), HttpStatus.OK);
	}
}
