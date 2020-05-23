package org.example;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        // this will work if we have configured AWS in the local pc.
        final AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().build();
/*
        // if aws is configured we can authenticate this way

        BasicAWSCredentials awsCredentials = new BasicAWSCredentials("IIIIIIDDDDDDD",
                " keyyyyyyyyyyyyyyyyyy");

        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard().withCredentials(
                new AWSStaticCredentialsProvider(awsCredentials)).build();
*/

        // listing S3 buckets by this
        List<Bucket> buckets = amazonS3.listBuckets();
        buckets.stream().forEach( b -> {
            System.out.println("Bucket name: "+b.getName() + ", " +
                    "Bucket owner: "+b.getOwner().getDisplayName()+ ", "+
                    "Bucket creation date: "+ b.getCreationDate());
        });

        // Create new S3 bucket
        Bucket bucket = amazonS3.createBucket("test-dev-1010-ffff");
        System.out.println(bucket.getName());
        amazonS3.putObject(bucket.getName(),
                "yy",
                "yyyy yy yy y yyy");

        // read S3 contents
        S3Object object = amazonS3.getObject(bucket.getName(), "yy");
        BufferedReader reader = new BufferedReader(new InputStreamReader(object.getObjectContent()));
        String line;
        while ((line = reader.readLine()) != null){
            System.out.println(line);
        }

        // delete object from bucket
        amazonS3.deleteObject(bucket.getName(), "yy");
        // delete bucket itself. But before we delete we have to empty bucket.
        amazonS3.deleteBucket(bucket.getName());
    }
}
