package com.ls.bs.core.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

/**
 * <p>
 * Title: 使用javamail发送邮件
 * </p>
 */
public class SslMailClient {
    private String to;// 收件人
    private String host;// smtp主机
    private String auth;
    private String username;
    private String password;
    private boolean debug;
    private String subject;// 邮件主题
    private String content;// 邮件正文
    private Vector file = new Vector();// 附件文件集合

    /**
     * <br>
     * 方法说明：默认构造器 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public SslMailClient() {
    }

    /**
     * <br>
     * 方法说明：构造器，提供直接的参数传入 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public SslMailClient(String to, String smtpServer, String username, String password, String subject, String content) {
        this.to = to;
        this.host = smtpServer;
        this.username = username;
        this.password = password;
        this.subject = subject;
        this.content = content;
    }

    /**
     * <br>
     * 方法说明：设置邮件服务器地址 <br>
     * 输入参数：String host 邮件服务器地址名称 <br>
     * 返回类型：
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * <br>
     * 方法说明：设置登录服务器校验密码 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setPassWord(String pwd) {
        this.password = pwd;
    }

    /**
     * <br>
     * 方法说明：设置登录服务器校验用户 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setUserName(String usn) {
        this.username = usn;
    }

    /**
     * <br>
     * 方法说明：设置邮件发送目的邮箱 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setTo(String to) {
        this.to = to;
    }

    public void addTo(String to) {
        this.to += ";" + to;
    }

    /**
     * <br>
     * 方法说明：设置邮件主题 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * <br>
     * 方法说明：设置邮件内容 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public String getHost() {
        return host;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <br>
     * 方法说明：往附件组合中添加附件 <br>
     * 输入参数： <br>
     * 返回类型：
     */
    public void attachfile(String fname) {
        file.addElement(fname);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean getDebug() {
        return debug;
    }

    /**
     * <br>
     * 方法说明：发送邮件 <br>
     * 输入参数： <br>
     * 返回类型：boolean 成功为true，反之为false
     */
    public void sendMail() throws MessagingException {
        // 构造mail session
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", auth == null ? "true" : auth);
        props.put("mail.sender.username", this.username);
        props.put("mail.sender.password", this.password);
        Session session = Session.getInstance(props);
        session.setDebug(debug);// 开启后有调试信息

        // 构造MimeMessage 并设定基本的值
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(username));

        String[] topepole = to.split(";");
        for (String top : topepole) {
            mimeMessage.addRecipients(Message.RecipientType.TO, InternetAddress.parse(top));
        }
        mimeMessage.setSubject(subject);

        // 构造Multipart
        Multipart mimeMultipartMail = new MimeMultipart();

        // 向Multipart添加正文
        MimeBodyPart mimeBodyPartMailBody = new MimeBodyPart();
        mimeBodyPartMailBody.setContent(content, "text/html;charset=utf-8");

        // 向MimeMessage添加（Multipart代表正文）
        mimeMultipartMail.addBodyPart(mimeBodyPartMailBody);

        // 向Multipart添加附件
        Enumeration elements = file.elements();
        String elementFilePathName = null;
        while (elements.hasMoreElements()) {
            MimeBodyPart mimeBodyPartFile = new MimeBodyPart();
            elementFilePathName = elements.nextElement().toString();
            FileDataSource fileDataSource = new FileDataSource(elementFilePathName);
            mimeBodyPartFile.setDataHandler(new DataHandler(fileDataSource));
            mimeBodyPartFile.setFileName(fileDataSource.getName());
            mimeMultipartMail.addBodyPart(mimeBodyPartFile);
        }

        file.removeAllElements();
        // 向Multipart添加MimeMessage
        mimeMessage.setContent(mimeMultipartMail);
        mimeMessage.setSentDate(new Date());
        mimeMessage.saveChanges();
        // 发送邮件
        Transport transport = session.getTransport("smtps");
        transport.connect(host, username, password);
        transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
        transport.close();
    }

}


