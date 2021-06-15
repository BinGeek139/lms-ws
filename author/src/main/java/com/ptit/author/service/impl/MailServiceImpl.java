package com.ptit.author.service.impl;

import com.ptit.author.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.sendgrid.*;
import java.io.IOException;
@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;
    @Override
    public void sendEmail(String to, String content) {
        Email from = new Email("quangnn.b17cn511@stu.ptit.edu.vn");
        String subject = "THƯ XÁC NHẬN ĐĂNG KÍ";
        Email emailTo = new Email(to);
        Content contentSen = new Content("text/plain", content);
        Mail mail = new Mail(from, subject, emailTo, contentSen);

        SendGrid sg = new SendGrid("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");
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            log.error(ex.getMessage(),ex);
        }
    }
}
