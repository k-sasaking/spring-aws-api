package com.sample.api.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	Integer createArticle(){

		return 201;
	}

	@PostMapping("/update")
	Integer updateArticle(){
		
		
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

	@GetMapping("/aws/put_file/{fileName}")
	String putfile(
			@PathVariable("fileName")
			String fileName
			){
		String hashFileName = DigestUtils.sha256Hex(fileName);
		PutObjectResult result = getAmazonS3Client().putObject(AWS_S3_BUCKET, hashFileName, "updaload-test");
		return hashFileName;
	}

}