package com.depromeet.todo.infrastructure.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.depromeet.todo.domain.storage.StorageException;
import com.depromeet.todo.domain.storage.StorageService;
import com.depromeet.todo.infrastructure.thumbnail.ThumbnailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class AwsS3StorageService implements StorageService {
    private final AmazonS3 s3Client;
    private final String bucketName;
    private final ThumbnailService thumbnailService;

    public AwsS3StorageService(AmazonS3 s3Client,
                               @Value("${aws.s3.bucket.name}") String bucketName,
                               ThumbnailService thumbnailService) {
        this.s3Client = s3Client;
        this.bucketName = bucketName;
        this.thumbnailService = thumbnailService;
    }

    @Override
    public String save(MediaType mediaType, InputStream inputStream) throws StorageException {
        String filename = this.getRandomFileName();

        try (InputStream processedInputStream = this.createThumbnailIfImage(mediaType, inputStream)) {
            Bucket bucket = s3Client.listBuckets().stream()
                    .filter(it -> it.getName().equals(bucketName))
                    .findFirst()
                    .orElseThrow(() -> new StorageException("Bucket not found. name:" + bucketName));

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(mediaType.toString());

            s3Client.putObject(bucket.getName(), filename, processedInputStream, metadata);
            return "https://s3.ap-northeast-2.amazonaws.com/" + bucketName + "/" + filename;
        } catch (Exception e) {
            throw new StorageException("Failed to save object. filename:" + filename + ", mediaType:" + mediaType, e);
        }
    }

    private InputStream createThumbnailIfImage(MediaType mediaType, InputStream inputStream) throws IOException {
        if (!mediaType.isCompatibleWith(MimeType.valueOf("image/*"))) {
            throw new IllegalArgumentException("It's not allowed type. 'mediaType' must be compatible with 'image/*'. mediaType: " + mediaType);
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thumbnailService.createThumbnail(inputStream, byteArrayOutputStream);
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private String getRandomFileName() {
        return UUID.randomUUID().toString();
    }
}
