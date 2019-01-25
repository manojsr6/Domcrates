package services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.UserDao;
import dao.WatchListDao;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Domain;
import models.User;
import models.WatchList;

public class WatchListService{
	
	WatchListDao watchListDao= new WatchListDao();
	UserDao userDao= new UserDao();
	
	public WatchList getDomainDetails(String domain) throws IOException, ParseException {
		
		WatchList watchList= new WatchList();
		String uri= "http://api.whoxy.com/?key=fa3a3c320f59b2b41z05b705bfc20cacd&whois="+domain;
		URL url= new URL(uri);
		URLConnection request= url.openConnection();
		request.connect();
		
		JsonParser jp= new JsonParser();
		JsonElement root= jp.parse(new InputStreamReader((InputStream) request.getContent()));
		JsonObject search_result= root.getAsJsonObject();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		JsonElement domain_name= search_result.get("domain_name");
		System.out.println("\n "+search_result);
		JsonElement domain_registered= search_result.get("domain_registered");
		System.out.println("\n "+domain_registered);
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
			
//			JsonObject registrant_contact= search_result.get("registrant_contact").getAsJsonObject();
//			JsonElement country_name= registrant_contact.get("country_name");
			
			watchList.setDomain_name(domain_name.toString().replaceAll("\\\"", ""));
			//watchList.setRegistered_email(primary_email);
			watchList.setRegistrar_name(registrar_name.toString().replaceAll("\\\"", ""));
//			domainObject.setCountry(country_name.toString().replaceAll("\\\"", ""));
			watchList.setCreated_date(createdDate);
			watchList.setUpdated_date(updatedDate);
			watchList.setExpiry_date(expiryDate);
			watchList.setWebsite_url(website_url.toString().replaceAll("\\\"", ""));
			
			return watchList;
		}
		else
			return null;
		
	}
	
	public WatchList add(String domainName, String primary_email) throws SQLException, IOException, ParseException
	{
		WatchList old_watchList= watchListDao.fetch(domainName);
		if(old_watchList != null)
		{
			User user= userDao.fetchUserByEmail(primary_email);
			WatchList new_watchList= new WatchList();
			new_watchList.setDomain_name(old_watchList.getDomain_name());
			//watchList.setRegistered_email(primary_email);
			//watchList.setRegistrar_name(registrar_name);
			new_watchList.setCreated_date(old_watchList.getCreated_date());
			new_watchList.setUpdated_date(old_watchList.getUpdated_date());
			new_watchList.setExpiry_date(old_watchList.getExpiry_date());
			new_watchList.setWebsite_url(old_watchList.getWebsite_url());
			new_watchList.setUser_id(user.getUser_id());
			return watchListDao.add(new_watchList);
		}
		else 
		{
			WatchList new_watchList= getDomainDetails(domainName);
			System.out.println("new watchList: "+new_watchList);
			if(new_watchList != null)
			{
				User user= userDao.fetchUserByEmail(primary_email);
				System.out.println("User: "+user);
				new_watchList.setUser_id(user.getUser_id());
				return watchListDao.add(new_watchList);
			}
			else
			{
				return null;
			}
		}
	}
	
	public List<WatchList> fetchWatchListById(int id,int offset,int limit)
	{
		User user= userDao.fetchUserById(id);
		if(user != null)
		{
			List<WatchList> WatchList= watchListDao.fetchByuser_id(id, offset, limit);
			
			for(int index =0 ;index<WatchList.size();index++)
			{
				
				LocalDate expiryLocalDate= WatchList.get(index).getExpiry_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				
				Period difference= Period.between(LocalDate.now(), expiryLocalDate);
				WatchList.get(index).setYear(difference.getYears());
				WatchList.get(index).setMonth(difference.getMonths());
				WatchList.get(index).setDay(difference.getDays());
			}
			return WatchList;
		}
		else
		{
			return null;
		}
		
	}
}