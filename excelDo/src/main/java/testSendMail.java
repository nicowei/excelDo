package main.java;


import main.java.sendMail;
import java.io.File;

public class testSendMail {

	   public static void main(String[] args) {
	        JavaMailWithAttachment se = new JavaMailWithAttachment(true,"MailServer.properties");
	        String affix ="d:\\test.xls";
	        se.doSendHtmlEmail("ÌìÌì(theme)", "ÌìÀ²àà(content)", "weican@eastcom.com","qyes@eastcom.com", affix);
	    }

}
