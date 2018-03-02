package org.superbiz.moviefun.blobstore;


import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import org.apache.tika.Tika;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;


public class S3Store implements BlobStore{
    AmazonS3Client s3Client;
    String s3BucketName;

    public S3Store(AmazonS3Client s3Client, String s3BucketName){
        this.s3Client = s3Client;
        this.s3BucketName = s3BucketName;
    }

    @Override
    public void put(Blob blob) throws IOException {
        if (!s3Client.doesBucketExist(s3BucketName))
            s3Client.createBucket(s3BucketName);
        ObjectMetadata om = new ObjectMetadata();
        om.setContentType(blob.contentType);
        s3Client.putObject(s3BucketName, blob.name, blob.inputStream, om);
    }

    @Override
    public Optional<Blob> get(String name) throws IOException {
        if (!s3Client.doesBucketExist(s3BucketName))
            return Optional.empty();
        S3Object s3Object = s3Client.getObject(s3BucketName, name);
        Blob retrievedBlob = new Blob(name, s3Object.getObjectContent(), s3Object.getObjectMetadata().getContentType());
        return Optional.of(retrievedBlob);
    }

    @Override
    public void deleteAll() {

    }
}