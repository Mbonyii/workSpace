package com.varol.WellPass_Mananagement_System.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    
    String uploadFile(MultipartFile file, String key);
    
    byte[] downloadFile(String key);
    
    void deleteFile(String key);
    
    String generatePresignedUrl(String key, int expirationMinutes);
    
    boolean doesFileExist(String key);
}
