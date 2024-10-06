package com.isi.booking.room;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class AwsS3Service {

    private final String bucketName = "bookinghotelimages";
   // @Value("${aws.s3.access-key}")
    private final String awsS3AccessKey = "AKIA3C6FMDEGXPCNDQP3";

   // @Value("${aws.s3.secret-key}")
    private final String awsS3SecretKey = "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";

    public String saveImageToS3(MultipartFile roomPhotoUrl){
        String s3LocationImage = null ;
        try {
            String s3FileName = roomPhotoUrl.getOriginalFilename();
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey,awsS3SecretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                    .withRegion(Regions.US_EAST_1)
                    .build();
            InputStream inputStream = roomPhotoUrl.getInputStream() ;
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3FileName, inputStream, metadata);
            s3Client.putObject(putObjectRequest);
            return "https://" + bucketName + ".s3.amazonaws.com/" + s3FileName;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
