package com.truthyouth.commerce.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class AmazonClient {

	private AmazonS3 s3client;

	// getting aws S3 credentials from application.properties file

	@Value("${aws.s3.url}")
	private String endpointUrl;

	@Value("${aws.s3.audio.bucket}")
	private String bucketName;

	@Value("${aws.s3.access.key}")
	private String accessKey;

	@Value("${aws.s3.secret.key}")
	private String secretKey;
	
	@Value("${aws.region}")
	private String region;

	@PostConstruct
	private void initializeAmazon() {
		AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
		this.s3client = AmazonS3ClientBuilder.standard().withRegion(region).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();
	}

	public String uploadFile(MultipartFile multipartFile, String folder) {
		String fileUrl = "";
		try {
			File file = convertMultiPartToFile(multipartFile);
			String fileName = generateFileName(multipartFile);
			fileUrl = endpointUrl + folder + "/" + fileName;
			uploadFileTos3bucket(folder, fileName, file);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileUrl;
	}

	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

	private String generateFileName(MultipartFile multiPart) {
		if(multiPart.getOriginalFilename().length() > 30) {
		return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_").replaceAll("!","-").substring(0,30);
		}
		else {
			return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_").replaceAll("!","-");
		}
	}

	private void uploadFileTos3bucket(String folder, String fileName, File file) {
		try {
		s3client.putObject(new PutObjectRequest(bucketName + "/" + folder, fileName, file)
				.withCannedAcl(CannedAccessControlList.PublicRead));
		}
		catch(AmazonServiceException e) {
			System.out.println(e.getMessage());
		}
	}

	public String deleteFileFromS3Bucket(String fileUrl, String folder) {
		try {
			String fileName[] = fileUrl.split("adiusUserDocuments/");
			//System.out.println(fileName[1]);
			boolean check = s3client.doesObjectExist(bucketName, folder + fileName[1]);
			if(!check)
				return "File not found";
			s3client.deleteObject(new DeleteObjectRequest(bucketName,folder + fileName[1]));
			return "Successfully deleted";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}