package com.alocode.usuario_service.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
