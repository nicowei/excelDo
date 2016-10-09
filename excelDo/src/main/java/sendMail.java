package main.java;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class sendMail {
	 /**
     * Message���󽫴洢����ʵ�ʷ��͵ĵ����ʼ���Ϣ��
     * Message������Ϊһ��MimeMessage����������������Ҫ֪��Ӧ��ѡ����һ��JavaMail session��
     */
    private MimeMessage message;
    
    /**
     * Session�����JavaMail�е�һ���ʼ��Ự��
     * ÿһ������JavaMail��Ӧ�ó���������һ��Session��������������Session����
     * 
     * JavaMail��ҪProperties������һ��session����
     * Ѱ��"mail.smtp.host"    ����ֵ���Ƿ����ʼ�������
     * Ѱ��"mail.smtp.auth"    �����֤��Ŀǰ����ʼ�����������Ҫ��һ��
     */
    private Session session;
    
    /***
     * �ʼ��Ǽȿ��Ա�����Ҳ���Ա��ܵ���JavaMailʹ����������ͬ������������������ܣ�Transport �� Store�� 
     * Transport ������������Ϣ�ģ���Store�������š�������Ľ̳�����ֻ��Ҫ�õ�Transport����
     */
    private Transport transport;
    
    private String mailHost="10.0.0.2";
    private String sender_username="qyes@eastcom.com";
    private String sender_password="q12yes34!#";
    
    
    private Properties properties = new Properties();
    
    /*
     * ��ʼ������
     */
   
    public void SendMail(boolean debug) {
        InputStream in = sendMail.class.getResourceAsStream("MailServer.properties");
        try {
            properties.load(in);
            this.mailHost = properties.getProperty("mail.smtp.host");
            this.sender_username = properties.getProperty("mail.sender.username");
            this.sender_password = properties.getProperty("mail.sender.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        session = Session.getInstance(properties);
        session.setDebug(debug);//�������е�����Ϣ
        message = new MimeMessage(session);
    }
    
    
  
    /**
     * �����ʼ�
     * 
     * @param subject
     *            �ʼ�����
     * @param sendHtml
     *            �ʼ�����
     * @param receiveUser
     *            �ռ��˵�ַ
     */
   public  void sendMailWithAttach(String subject, String sendHtml,
           String receiveUser,File attachment){
	   
	   try {
		   // ������
		   sender_username="qyes@eastcom.com";
          Address from = new InternetAddress(sender_username);
           message.setFrom(from);
     
           // �ռ���
           receiveUser = "weican@eastcom.com";
           InternetAddress to = new InternetAddress(receiveUser);
           message.setRecipient(Message.RecipientType.TO, to);

           // �ʼ�����
           message.setSubject(subject);

           // ��multipart����������ʼ��ĸ����������ݣ������ı����ݺ͸���
           Multipart multipart = new MimeMultipart();
           
           // ����ʼ�����
           BodyPart contentPart = new MimeBodyPart();
           contentPart.setContent(sendHtml, "text/html;charset=UTF-8");
           multipart.addBodyPart(contentPart);
           
           // ��Ӹ���������
           if (attachment != null) {
               BodyPart attachmentBodyPart = new MimeBodyPart();
               DataSource source = new FileDataSource(attachment);
               attachmentBodyPart.setDataHandler(new DataHandler(source));
               
               // ���������Ľ���ļ�������ķ�������ʵ��MimeUtility.encodeWord�Ϳ��Ժܷ���ĸ㶨
               // �������Ҫ��ͨ�������Base64�����ת�����Ա�֤������ĸ����������ڷ���ʱ����������
               //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
               //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");
               
               //MimeUtility.encodeWord���Ա����ļ�������
               attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));
               multipart.addBodyPart(attachmentBodyPart);
           }
           
           // ��multipart����ŵ�message��
           message.setContent(multipart);
           // �����ʼ�
           message.saveChanges();

           transport = session.getTransport("smtp");
           // smtp��֤���������������ʼ��������û�������
           transport.connect(mailHost, sender_username, sender_password);
           // ����
           transport.sendMessage(message, message.getAllRecipients());

           System.out.println("send success!");
       } catch (Exception e) {
           e.printStackTrace();
       } finally {
           if (transport != null) {
               try {
                   transport.close();
               } catch (MessagingException e) {
                   e.printStackTrace();
               }
           }
       }
   }
		    
		    
		   
	   }
	   
  
	

