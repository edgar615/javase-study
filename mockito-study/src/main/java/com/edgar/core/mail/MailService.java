package com.edgar.core.mail;

import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@Validated
public interface MailService {

    /**
     * 发送邮件
     *
     * @param from    发件人
     * @param to      收件人
     * @param subject 主题
     * @param body    邮件内容
     */
    void sendSimpleMail(String from, String to, String subject, String body);

    /**
     * 给多个收件人发送邮件
     *
     * @param from    发件人
     * @param to      收件人列表
     * @param subject 主题
     * @param body    邮件内容
     */
    void sendSimpleMail(String from, List<String> to, String subject, String body);

    /**
     * 发送邮件
     *
     * @param from    发件人
     * @param to      收件人列表
     * @param cc      抄送列表
     * @param subject 主题
     * @param body    邮件内容
     */
    void sendSimpleMail(String from, List<String> to, List<String> cc, String subject,
                        String body);

    /**
     * 根据模板发送邮件
     *
     * @param from    发件人
     * @param to      收件人列表
     * @param cc      抄送列表
     * @param subject 主题
     * @param tpl     模板
     * @param model   map对象
     */
    void sendTplEmail(String from, List<String> to, List<String> cc, String subject,
                      String tpl, Map<String, Object> model);

    /**
     * 根据模板路径发送工具
     *
     * @param from        发件人
     * @param to          收件人列表
     * @param cc          抄送列表
     * @param subject     主题
     * @param tplLocation 模板路径
     * @param model       map对象
     */
    void sendTplLocationEmail(String from, List<String> to, List<String> cc, String subject,
                              String tplLocation, Map<String, Object> model);

}
