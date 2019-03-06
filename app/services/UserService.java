package services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.DomainDao;
import dao.UserDao;
import models.Domain;
import models.User;
import play.libs.ws.WS;
import play.libs.ws.WSClient;
import play.libs.ws.WSResponse;


public class UserService{
	
	public UserDao userdao= new UserDao();
	public DomainDao domaindao= new DomainDao();
	public EmailService emailService= new EmailService();
	
	public JsonArray fetchDomainNames(String primary_email) throws InterruptedException, ExecutionException, IOException
	{
		String uri= "http://api.whoxy.com/?key=fa3a3c320f59b2b41z05b705bfc20cacd&reverse=whois&email="+primary_email;
		URL url= new URL(uri);
		URLConnection request= url.openConnection();
		request.connect();
		JsonParser jp= new JsonParser();
		JsonElement root= jp.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject jsobject= root.getAsJsonObject();
		JsonArray json_array= jsobject.get("search_result").getAsJsonArray();
		return json_array;
	}
	
//	public JsonElement fetchDomainDetails(JsonElement domain_name) throws IOException, ParseException
//	{
//		JsonElement json_element=null;
//		
//		String uri= "https://www.whoisxmlapi.com/whoisserver/WhoisService?domainName="+domain_name.toString().replaceAll("\"", "")+"&apiKey=at_VTsR68K3bmLfxyWWfbrcu5YB04XAS&outputFormat=JSON";
//		URL url= new URL(uri);
//		URLConnection request= url.openConnection();
//		request.connect();
//		JsonParser jp= new JsonParser();
//		JsonElement root= jp.parse(new InputStreamReader((InputStream) request.getContent()));
//		JsonObject jsobject= root.getAsJsonObject();
//		
//		//Declaration of date format for parsing
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddTHH:mm:ss");
//		
//		//Extracting Who is record data from array list 
//		JsonObject json_element1= jsobject.get("WhoisRecord").getAsJsonObject();
//		
//		//Fetching date as json element and parsing to date format inorder to get java date
//		JsonElement created_date= json_element1.get("createdDate");
//		Date createdDate= sdf.parse(created_date.toString().replaceAll("\"", ""));
//		
//		JsonElement updated_date= json_element1.get("updatedDate");
//		Date updatedDate= sdf.parse(updated_date.toString().replaceAll("\"", ""));
//		
//		JsonElement expiresDate= json_element1.get("expiresDate");
//		Date expiryDate= sdf.parse(expiresDate.toString().replaceAll("\"", ""));
//		
//		JsonObject json_element2= json_element1.get("registrant").getAsJsonObject();
//		
//		JsonElement country= json_element2.get("country");
//		JsonElement countyCode= json_element2.get("countryCode");
//		JsonElement registrant_email= json_element2.get("email");
//		JsonElement registrant_name= json_element2.get("name");
//		
//		Domain domain= new Domain();
//		
//		domain.setDomain_name(domain_name.toString().replaceAll("\"", ""));
//		domain.setRegistered_email(registrant_email.toString());
//		domain.setRegistrar_name(registrant_name.toString());
//		domain.setCountry(country.toString());
//		domain.setCreated_date(createdDate);
//		domain.setUpdated_date(updatedDate);
//		domain.setExpiry_date(expiryDate);
//		
//		System.out.printf("\n Domain: "+domain);
//		return country;
//		
//	}
//	
	/**
	 * This function is used to fetch the domain details including name, expiry, created, updated date etc from whoxy api
	 * @param primary_email
	 * @return
	 * @throws InterruptedException
	 * @throws ExecutionException
	 * @throws IOException
	 * @throws ParseException
	 * @throws SQLException
	 */
	
	public ArrayList<String> fetchData(String primary_email) throws InterruptedException, ExecutionException, IOException, ParseException, SQLException
	{
		JsonArray json_array= fetchDomainNames(primary_email);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		ArrayList<String>domainList= new ArrayList<String>();
		
		for(int i= 0 ; i<json_array.size();i++)
		{
			JsonObject search_result= json_array.get(i).getAsJsonObject();
			JsonElement domain_name= search_result.get("domain_name");
			domainList.add(domain_name.toString().replaceAll("\\\"", ""));
			
		}
		return domainList;
	}
	
	public Domain getDomainDetails(String domain, String primary_email) throws IOException, ParseException {
		
		Domain domainObject= new Domain();
		String uri= "http://api.whoxy.com/?key=fa3a3c320f59b2b41z05b705bfc20cacd&whois="+domain;
		URL url= new URL(uri);
		URLConnection request= url.openConnection();
		request.connect();
		
		JsonParser jp= new JsonParser();
		JsonElement root= jp.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject search_result= root.getAsJsonObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		JsonElement domain_name= search_result.get("domain_name");
		int status= search_result.get("status").getAsInt();
		JsonElement domain_registered= search_result.get("domain_registered");
		if(status == 1)
		{
			if(search_result.get("domain_registered").toString().replaceAll("\\\"", "").equalsIgnoreCase("Yes"))
			{
				JsonElement created_date= search_result.get("create_date"); 
				Date createdDate= sdf.parse(created_date.toString().replaceAll("\"", ""));
				
				JsonElement expiry_date= search_result.get("expiry_date");
				Date expiryDate= sdf.parse(expiry_date.toString().replaceAll("\"", ""));
				
				JsonElement update_date= search_result.get("update_date");
				Date updatedDate= sdf.parse(update_date.toString().replaceAll("\"", ""));
				
				JsonObject domain_registrar= search_result.get("domain_registrar").getAsJsonObject();
				JsonElement registrar_name= domain_registrar.get("registrar_name");
				JsonElement website_url= domain_registrar.get("website_url");
				
//				JsonObject registrant_contact= search_result.get("registrant_contact").getAsJsonObject();
//				JsonElement country_name= registrant_contact.get("country_name");
				
				domainObject.setDomain_name(domain_name.toString().replaceAll("\\\"", ""));
				domainObject.setRegistered_email(primary_email);
				domainObject.setRegistrar_name(registrar_name.toString().replaceAll("\\\"", ""));
//				domainObject.setCountry(country_name.toString().replaceAll("\\\"", ""));
				domainObject.setCreated_date(createdDate);
				domainObject.setUpdated_date(updatedDate);
				domainObject.setExpiry_date(expiryDate);
				domainObject.setWebsite_url(website_url.toString().replaceAll("\\\"", ""));
				
				return domainObject;
			}
			return null;
		}
		
		else
			return null;
		
	}
	
	public ArrayList<Domain> fetchDomainDetails(ArrayList<String>domainNameList, String primary_email) throws IOException, ParseException{
		ArrayList<Domain> domainList = new ArrayList<Domain>();
		Domain domain= new Domain();
		for(int index = 0 ;index < domainNameList.size();index++)
		{
			if(!domainNameList.get(index).equalsIgnoreCase("smaug.studio"))
			 domain= getDomainDetails(domainNameList.get(index), primary_email);
			if(domain != null)
			{
				domainList.add(domain);
			}
			
		}
		return domainList;
	}
	
	public User create_user(User new_user) throws SQLException, InterruptedException, ExecutionException, IOException, ParseException
	{
		ArrayList<String> domainNameList= new ArrayList<String>();
		ArrayList<Domain> domainList= new ArrayList<Domain>();
		domainNameList=fetchData(new_user.getPrimary_email());
		
		domainList= fetchDomainDetails(domainNameList,new_user.getPrimary_email());
		
		
		if(!domainList.isEmpty())
		{
			new_user.setEmailVerificationToken(UUID.randomUUID().toString());
			userdao.create_user(new_user);
			emailService.activationLink(new_user.getPrimary_email(), new_user.getEmailVerificationToken());
			domaindao.add_domain(domainList);
		}
		
		return new_user;
		
	}
	
	public User validateCredentials(String email, String password)
	{
		User user= userdao.validateCredentials(email, password);
		return user;
	}
	
	public Boolean forgotPasswordLink(String email) throws IOException
	{
		User user= userdao.fetchUserByEmail(email);
		if(user != null)
		{
			String forgotPasswordToken= UUID.randomUUID().toString();
			user.setForgotPasswordToken(forgotPasswordToken);
			userdao.updateUser(user);
			emailService.forgotPasswordLink(user.getPrimary_email(), forgotPasswordToken);
			return true;
			
		}
		else
			return false;
	}
	
	public Boolean validateForgotPasswordLink(String forgotPasswordToken)
	{
		User user= userdao.validateForgotPasswordLink(forgotPasswordToken);
		if(user != null && user.getForgotPasswordToken().equals(forgotPasswordToken))
		{
			user.setForgotPasswordToken(null);
			userdao.updateUser(user);
			return true;
		}
		else
			return false;
	}
	
	public void resetPassword(int userId,String new_password)
	{
		User user= userdao.fetchUserById(userId);
		if(user != null)
		{
			user.setPassword(new_password);
			userdao.resetPassword(user);
		}
	}
	
	public String changePassword(int userId, String old_password, String new_password) throws IOException
	{
		User user= userdao.fetchUserById(userId);
		if(user != null)
		{
			if(userdao.checkPass(old_password, user.getPassword()))
			{
				user.setPassword(new_password);
				userdao.changePassword(user);
				emailService.resetPasswordConfirmationMail(user.getPrimary_email());
				return "Changed the password";
			}
		}
		return "Could not changed the password";
	}
	
	public Boolean validateActivationCode(String activationCode)
	{
		User user= userdao.validateActivationLink(activationCode);
		if(user != null && user.getEmailVerificationToken().equals(activationCode))
		{
			user.setVerified(true);
			user.setEmailVerificationToken(null);
			userdao.updateUser(user);
			return true;
		}
		else 
			return false;
	}
	
}
