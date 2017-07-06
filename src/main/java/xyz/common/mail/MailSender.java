package xyz.common.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * MailSender
 *
 * @author leihz
 * @date 2017/7/6 20:47
 */
public class MailSender {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${sendTo}")
    private String sendTo;

    @Value("${sendFrom}")
    private String sendFrom;


    public void sendEmail(String subject,String msg) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(sendFrom);
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(msg);
        mailSender.send(message);
    }

}
