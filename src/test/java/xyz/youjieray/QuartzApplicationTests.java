package xyz.youjieray;

import javafx.application.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
//@SpringApplicationConfiguration(classes = Application.class)
public class QuartzApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void sendEmail() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("295482300@qq.com");
		message.setTo("172526456@qq.com");
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");
		mailSender.send(message);
	}

}
