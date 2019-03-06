package services;
import com.typesafe.config.ConfigFactory;



import java.io.IOException;

import com.sendgrid.*;
import com.typesafe.config.Config;

public class EmailService {
	 SendGrid sg = new SendGrid(ConfigFactory.load().getString("sendgridKey"));
	
	
	public void verificationLink(String primary_email, String activationCode) throws IOException
	{
		Email from = new Email(ConfigFactory.load().getString("email.from"));
	    String subject = "Reset password link";
	    Email to = new Email(primary_email);
	    Content content = new Content("text/plain", "Hi Boss");
	    Mail mail = new Mail(from, subject, to, content);
	    Request request = new Request();
	    
	    try {
	        request.setMethod(Method.POST);
	        request.setEndpoint("mail/send");
	        request.setBody(mail.build());
	        Response response = sg.api(request);
	      } catch (IOException ex) {
	        throw ex;
	      }
	}
	

}
