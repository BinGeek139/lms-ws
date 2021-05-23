package com.ptit.author.service.impl;

import com.ptit.author.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender mailSender;
    @Override
    public void sendEmail(String to, String content) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
              MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("MAIL XÁC NHẬN ĐĂNG KÍ TỪ LMS PTIT");
            mimeMessageHelper.setFrom("LmsPtit");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setText(content);

            mailSender.send(mimeMessageHelper.getMimeMessage());

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
