package controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import dao.DomainDao;
import dao.UserDao;
import models.Domain;
import models.RequestBody;
import models.TestEbean;
import models.User;
import play.Logger;
import play.libs.Json;
import play.mvc.*;
import services.DomainService;
import services.UserService;
import services.WatchListService;
import util.JwtControllerHelper;
import util.JwtControllerHelperImpl;
import util.VerifiedJwt;
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
	
	@Inject
    private JwtControllerHelper jwtControllerHelper;
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
   
   public Result fetchDomain(int offset, int limit) throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   return jwtControllerHelper.verify(request(), res -> {
           if (res.left.isPresent()) {
               return forbidden(res.left.get().toString());
           }
           VerifiedJwt verifiedJwt = res.right.get();
           Logger.debug("{}", verifiedJwt);

           ObjectNode result = Json.newObject();
           try {
			return ok(Json.toJson(domainService.fetchDomainByemail(verifiedJwt.getUserId(), offset, limit)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           return forbidden();
       });
   }
   
   public Result addDomain() throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   return jwtControllerHelper.verify(request(), res -> {
           if (res.left.isPresent()) {
               return forbidden(res.left.get().toString());
           }
           VerifiedJwt verifiedJwt = res.right.get();
           Logger.debug("{}", verifiedJwt);
           
           JsonNode json_node= request().body().asJson();
    	   if(json_node == null)
    	   {
    		   return badRequest("Expected proper json data");
    	   }
    	   RequestBody new_domain= Json.fromJson(json_node,RequestBody.class);
    	   try {
			watchListService.add(new_domain.getDomain_name(), new_domain.getPrimary_email());
    	   } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   return ok(Json.toJson(new_domain));
       });
   }
   
   public Result fetchWatchList(int id, int offset, int limit) throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   System.out.println(id);
	   return ok(Json.toJson(watchListService.fetchWatchListById(id, offset, limit)));
   }
   
   public Result forgotPasswordLink() throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   JsonNode json_node= request().body().asJson();
	   if(json_node == null)
	   {
		   return badRequest("Expected proper json data");
	   }else
	   {
		   String email = json_node.findPath("email").textValue();
		   userservice.forgotPasswordLink(email);
		   return ok(Json.toJson(true));
	   }
   }
   
   public Result validateforgotPasswordLink(String forgotPasswordToken) throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
		  userservice.forgotPasswordLink(forgotPasswordToken);
		   return ok(Json.toJson(true));
   }
   
   public Result resetPassword() throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   JsonNode json_node= request().body().asJson();
	   if(json_node == null)
	   {
		   return badRequest("Expected proper json data");
	   }else
	   {
		   int userId = json_node.findPath("userId").intValue();
		   String new_password = json_node.findPath("new_password").textValue();
		   userservice.resetPassword(userId, new_password);
		   return ok(Json.toJson(true));
	   }
   }
   
   public Result changePassword() throws SQLException, InterruptedException, ExecutionException, IOException, ParseException{
	   JsonNode json_node= request().body().asJson();
	   if(json_node == null)
	   {
		   return badRequest("Expected proper json data");
	   }else
	   {
		   int userId = json_node.findPath("userId").intValue();
		   String new_password= json_node.findPath("new_password").textValue();
		   String old_password= json_node.findPath("old_password").textValue();
		   userservice.changePassword(userId, old_password, new_password);
		   return ok(Json.toJson(true));
	   }
   }

  }
