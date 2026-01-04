package com.varol.WellPass_Mananagement_System.implementation.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.varol.WellPass_Mananagement_System.service.storage.S3Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class S3ServiceImpl implements S3Service {

    @Override
    public String uploadFile(MultipartFile file, String key) {
        log.info("Uploading file to S3: {}", key);
        return "https://s3.amazonaws.com/wellpass/" + key;
    }

    @Override
    public byte[] downloadFile(String key) {
        log.info("Downloading file from S3: {}", key);
        return new byte[0];
    }

    @Override
    public void deleteFile(String key) {
        log.info("Deleting file from S3: {}", key);
    }

    @Override
    public String generatePresignedUrl(String key, int expirationMinutes) {
        log.info("Generating presigned URL for: {}", key);
        return "https://s3.amazonaws.com/wellpass/" + key + "?expires=" + expirationMinutes;
    }

    @Override
    public boolean doesFileExist(String key) {
        return false;
    }
}