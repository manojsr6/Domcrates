package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.databind.JsonNode;

import dao.DomainDao;
import dao.UserDao;
import models.Domain;
import models.RequestBody;
import models.TestEbean;
import models.User;
import play.libs.Json;
import play.mvc.*;
import services.DomainService;
import services.UserService;
import services.WatchListService;
import views.html.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {

	UserDao user= new UserDao();
	UserService userservice=  new UserService();
	//DomainDao domainDao= new DomainDao();
	DomainService domainService= new DomainService();
	WatchListService watchListService= new WatchListService();
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     * @throws SQLException 
     */
    public Result index() throws SQLException {
    	try
    	{
    		List<User> userList= user.findAll();
    	    return ok(Json.toJson(userList)).as("application/json");
    	}
        catch (SQLException e)
        {
        	throw e;
        }
    }
    
   public Result findByEmail() throws SQLException, InterruptedException, ExecutionException, IOException, ParseException {
	   JsonNode json_node= request().body().asJson();
	   if(json_node == null)
	   {
		   return badRequest("Expected proper json data");
	   }
	   User user1= Json.fromJson(json_node,User.class);
	   System.out.println("after return statement");
	   ArrayList<String> userList= new ArrayList<>();
	   userList.add("Vijilin");
	   userList.add("Manoj");
	   userList.add("Vijoyes");
	   user.automaticInsert(userList);
	   return ok(Json.toJson("sairam"));
	   
   }
   
   public Result createUser() throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   JsonNode json_node= request().body().asJson();
	   if(json_node == null)
	   {
		   return badRequest("Expected proper json data");
	   }
	   User new_user= Json.fromJson(json_node,User.class);
	   userservice.create_user(new_user);
	   return ok(Json.toJson(new_user));
	   
   }
   
   public Result fetchDomain(String email, int offset, int limit) throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   return ok(Json.toJson(domainService.fetchDomainByemail(email, offset, limit)));
   }
   
   public Result addDomain() throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   JsonNode json_node= request().body().asJson();
	   if(json_node == null)
	   {
		   return badRequest("Expected proper json data");
	   }
	   RequestBody new_domain= Json.fromJson(json_node,RequestBody.class);
	   watchListService.add(new_domain.getDomain_name(), new_domain.getPrimary_email());
	   return ok(Json.toJson(new_domain));
	   
   }
   
   public Result fetchWatchList(int id, int offset, int limit) throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   System.out.println(id);
	   return ok(Json.toJson(watchListService.fetchWatchListById(id, offset, limit)));
   }

}
