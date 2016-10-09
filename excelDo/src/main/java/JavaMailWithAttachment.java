package main.java;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
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

public class JavaMailWithAttachment {
	private MimeMessage message;
    private Session session;
    private Transport transport;

    private String mailHost = "";
    private String sender_username = "";
    private String sender_password = "";


    private Properties properties = new Properties();

    /*
     * ��ʼ������
     */
    public JavaMailWithAttachment(boolean debug,String input) {
    	//�����ں�class��ͬһ��Ŀ¼��
    	
        //InputStream in = JavaMailWithAttachment.class.getResourceAsStream(input);
    	InputStream in =null;
    	
    	try {
    		if(input.indexOf("/")!=-1){
    			File f =new File(input);
    			in = new FileInputStream(f);  	
    		}else {
    			in =JavaMailWithAttachment.class.getResourceAsStream(input);
    		}
    	     
            properties.load(in);
            this.mailHost = properties.getProperty("mail.smtp.host");
            this.sender_username = properties.getProperty("mail.sender.username");
            this.sender_password = properties.getProperty("mail.sender.password");
       
        } catch (IOException e) {
            e.printStackTrace();
        }

        session = Session.getInstance(properties);
        session.setDebug(debug);// �������е�����Ϣ
        message = new MimeMessage(session);
    }
    public JavaMailWithAttachment(boolean debug,HashMap<String,String> input){
    	this.mailHost =String.valueOf(input.get("host"));
        this.sender_username = String.valueOf(input.get("username"));
        this.sender_password = String.valueOf(input.get("password"));
        properties.put("mail.smtp.host", this.mailHost);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.sender.username", this.sender_username);
        properties.put("mail.sender.password", this.sender_password);
        
        session = Session.getInstance(properties);
        session.setDebug(debug);// �������е�����Ϣ
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
     * @param attachment
     *            ����
     */
    public Map<String,Object> doSendHtmlEmail(String subject, String sendHtml, String receiveUser,String fromUser, String attpath) {
    	Map<String,Object> result = new HashMap<String,Object>();
        try {        	
        	MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();		       
	        mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
	        CommandMap.setDefaultCommandMap(mc);
            // ������
            InternetAddress from = new InternetAddress(fromUser);
            message.setFrom(from);

            // �ռ���
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
            if(attpath !=null){
            	File attachment = new File(attpath);
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
            result.put("Status","Done");           
            result.put("result","send success!");
            return result;
        } catch (Exception e) {
           // e.printStackTrace();
            result.put("Status","Wrong"); 
            result.put("result",e.toString());
            return result;
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    //e.printStackTrace();
                    result.put("Status","Wrong"); 
                    result.put("result",e.toString());
                    return result;
                }
            }
        }
    }

 
}
