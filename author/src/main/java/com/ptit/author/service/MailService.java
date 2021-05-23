package com.ptit.author.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
     void sendEmail(String to,String content);
}
