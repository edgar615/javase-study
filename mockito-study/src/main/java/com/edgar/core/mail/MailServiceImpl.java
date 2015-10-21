package com.edgar.core.mail;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.MimeMessage;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Override
    public void sendSimpleMail(String from, String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

    }

    @Override
    public void sendSimpleMail(String from, List<String> to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to.toArray(new String[to.size()]));
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

    }

    @Override
    public void sendSimpleMail(String from, List<String> to, List<String> cc, String subject,
                               String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);
        message.setTo(to.toArray(new String[to.size()]));
        message.setCc(cc.toArray(new String[cc.size()]));
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);

    }

    @Override
    public void sendTplEmail(final String from, final List<String> to, final List<String> cc,
                             final String subject, final String tpl, final Map<String, Object> model) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(to.toArray(new String[to.size()]));
                message.setCc(cc.toArray(new String[cc.size()]));
                message.setFrom(from);
                message.setSubject(subject);
                VelocityContext context = new VelocityContext(model);
                StringWriter w = new StringWriter();
                Velocity.evaluate(context, w, "emailString", tpl);
                message.setText(w.toString(), true);
            }
        };
        JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) mailSender;
        javaMailSenderImpl.send(preparator);
    }

    @Override
    public void sendTplLocationEmail(final String from, final List<String> to,
                                     final List<String> cc, final String subject, final String tplLocation,
                                     final Map<String, Object> model) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setTo(to.toArray(new String[to.size()]));
                message.setCc(cc.toArray(new String[cc.size()]));
                message.setFrom(from);
                message.setSubject(subject);
                String tpl;
                tpl = VelocityEngineUtils.mergeTemplateIntoString(
                        velocityEngine, tplLocation, "utf-8", model);
                message.setText(tpl, true);
            }
        };
        JavaMailSenderImpl javaMailSenderImpl = (JavaMailSenderImpl) mailSender;
        javaMailSenderImpl.send(preparator);
    }

}