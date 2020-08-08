package com.sample.api.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import com.sample.api.entity.Article;
import com.sample.api.service.PostService;

@RestController
@CrossOrigin
@RequestMapping(path = "/api", produces =  MediaType.APPLICATION_JSON_VALUE)
public class SampleController {
	
	@Value("${cloud.aws.credentials.accessKey}")
	String awsAccessKey;
	
	@Value("${cloud.aws.credentials.secretKey}")
	String awsSecretKey;
	
	@Value("${cloud.aws.region.static}")
	String awsRegion;
	
	final String AWS_S3_BUCKET = "sample-vue-test-bucket";
	
	@Autowired
	PostService postService;	
	
	AmazonS3 getAmazonS3Client() {
		return AmazonS3ClientBuilder
				.standard()
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccessKey,awsSecretKey)))
				.withRegion(awsRegion)
				.build();
	}
	
	@GetMapping("/hello")
	String hello(){
		return "Hello Springboot Restful";
				
	}

	@GetMapping("/list")
	List<Article> getAllArticles(){
		return postService.getAllArticles();
				
	}
	
	@GetMapping("/detail/{id}")
	Optional<Article> getArticle(
			@PathVariable("id")
			Integer id
		){
		
		return postService.getArticleById(id);				
	}

	@PostMapping("/create")
	String createArticle(
			@RequestParam("postName") String name,
			@RequestParam("postText") String text,
			@RequestParam("postImage") String imageFileName,
	        @RequestParam(value = "postImageData", required = true) MultipartFile imageFile
			){
		String fileName = putS3(imageFileName, convert(imageFile));
		Article article = new Article(name, text, fileName);
		postService.createArticle(article);
		return "OK";
	}
	
	@PostMapping("/update/{id}")
	Integer updateArticle(
			@PathVariable("id")
			Integer id
			){
		
		
		return 201;
	}
	
	@PostMapping("/delete/{id}")
	Integer deleteArticle(
			@PathVariable("id")
			Integer id
		){
		
		return 201;
	}
	
	@GetMapping("/aws")
	List<String> filelist(){
				
		ListObjectsV2Result result = getAmazonS3Client().listObjectsV2(AWS_S3_BUCKET);
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		List<String> fileList = new ArrayList<String>();
		for (S3ObjectSummary os : objects) {
		    fileList.add(os.getKey());
		}			
		return fileList;
	}

	String putS3(
			String fileName, File file
			){
		String hashFileName = DigestUtils.sha256Hex(fileName);
		PutObjectResult result = getAmazonS3Client().putObject(AWS_S3_BUCKET, hashFileName, file);
		return hashFileName;
	}

	File convert(MultipartFile file) 
	{  
		File convFile = null;
		try {
			convFile = new File(file.getOriginalFilename()); 
		    convFile.createNewFile(); 
		    FileOutputStream fos = new FileOutputStream(convFile); 
		    fos.write(file.getBytes());
		    fos.close();
		}catch(IOException e) {
			
		}
	    return convFile; 
	} 
	

}