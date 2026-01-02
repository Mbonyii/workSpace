package com.varol.WellPass_Mananagement_System.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    
    String storeFile(MultipartFile file, String directory);
    
    byte[] loadFile(String filePath);
    
    void deleteFile(String filePath);
    
    String storeProfilePicture(MultipartFile file, String employeeId);
    
    boolean fileExists(String filePath);
}