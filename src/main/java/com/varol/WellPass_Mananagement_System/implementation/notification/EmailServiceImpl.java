package com.varol.WellPass_Mananagement_System.implementation.notification;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.varol.WellPass_Mananagement_System.service.notification.EmailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        log.info("Sending email to {}: {}", to, subject);
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlContent) {
        log.info("Sending HTML email to {}: {}", to, subject);
    }

    @Override
    public void sendTemplateEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        log.info("Sending template email to {}: {}", to, subject);
    }

    @Override
    public void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath) {
        log.info("Sending email with attachment to {}: {}", to, subject);
    }
}