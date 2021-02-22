package org.javaboy.mailserver.receiver;

import org.javaboy.vhr.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.context.Context;
import sun.rmi.runtime.Log;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailReceiver {

    public static final Logger LOGGER = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    MailProperties mailProperties;

    @Autowired
    TemplateEngine templateEngine;

    @RabbitListener(queues = "javaboy.mail.welcome")
    public void handler(Employee employee) {
        LOGGER.info(employee.toString());
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            // 设置发件人
            helper.setFrom(mailProperties.getUsername());
            // 设置收件人
            helper.setTo(employee.getEmail());
            // 设置主题
            helper.setSubject("入职欢迎");
            // 设置发生时间
            helper.setSentDate(new Date());
            // 设置
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            context.setVariable("positionName", employee.getPosition().getName());
            context.setVariable("joblevelName", employee.getJobLevel().getName());
            String text = templateEngine.process("mail", context);
            LOGGER.info(text);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            LOGGER.error("邮件发送失败：" + e.getMessage());
        }
    }
}
