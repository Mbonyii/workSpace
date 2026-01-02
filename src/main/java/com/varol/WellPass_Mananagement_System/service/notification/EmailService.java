package com.varol.WellPass_Mananagement_System.service.notification;

import java.util.Map;

public interface EmailService {
    
    void sendSimpleEmail(String to, String subject, String text);
    
    void sendHtmlEmail(String to, String subject, String htmlContent);
    
    void sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> variables);
    
    void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath);
}