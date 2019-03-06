package services;
import com.typesafe.config.ConfigFactory;



import java.io.IOException;

import com.sendgrid.*;
import com.typesafe.config.Config;

public class EmailService {
	 SendGrid sg = new SendGrid(ConfigFactory.load().getString("sendgridKey"));
	
	
	public void activationLink(String primary_email, String activationCode) throws IOException
	{
		Email from = new Email(ConfigFactory.load().getString("email.from"));
	    String subject = "Account Activation Link";
	    Email to = new Email(primary_email);
	    Content content = new Content("text/plain", "https:localhost:9000/api/1.0/portal/mobile/validateActivationCode/"+activationCode);
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
	
	public void forgotPasswordLink(String primary_email, String forgotPasswordToken) throws IOException
	{
		Email from = new Email(ConfigFactory.load().getString("email.from"));
	    String subject = "Reset password link";
	    Email to = new Email(primary_email);
	    Content content = new Content("text/plain", "https:localhost:9000/api/1.0/portal/mobile/forgotPasswordLinkValidation/"+forgotPasswordToken);
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
	
	public void resetPasswordConfirmationMail(String primary_email) throws IOException
	{
		Email from = new Email(ConfigFactory.load().getString("email.from"));
	    String subject = "Reset password link";
	    Email to = new Email(primary_email);
	    Content content = new Content("text/plain", "Changed password successfully");
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
