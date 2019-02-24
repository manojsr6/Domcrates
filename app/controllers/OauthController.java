package controllers;

import java.io.UnsupportedEncodingException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import javax.inject.Inject;

import com.auth0.jwt.*;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.typesafe.config.Config;

import models.User;
import play.mvc.*;
import services.UserService;
import util.JwtControllerHelper;
import util.JwtControllerHelperImpl;
import util.VerifiedJwt;
import play.libs.*;
import play.Logger;
import play.libs.typedmap.TypedKey;

public class OauthController extends Controller {
	UserService userService= new UserService();
	
	@Inject
    private JwtControllerHelper jwtControllerHelper;
	
	 @Inject
	    private Config config;
	 
	 public Result generateSignedToken() throws UnsupportedEncodingException {
	        return ok("signed token: " + getSignedToken("1"));
	    }
	 
	 public Result login() throws UnsupportedEncodingException {
	        JsonNode body = request().body().asJson();

	        if (body == null) {
	            Logger.error("json body is null");
	            return forbidden();
	        }

	        if (body.hasNonNull("email") && body.hasNonNull("password")) {
	        	
	        	User user= userService.validateCredentials(body.get("email").asText(), body.get("password").asText());
	        	if(user != null)
	        	{
	        		ObjectNode result = Json.newObject();
		            result.put("access_token", getSignedToken(user.getPrimary_email()));
		            return ok(result);
	        	}
	        	else
	        	{
	        		return unauthorized(Json.toJson("invalid credentials"));
	        	}
	            
	        } else {
	            return badRequest(Json.toJson("invalid request body"));
	        }
	    }
	 
	 private String getSignedToken(String i) throws UnsupportedEncodingException {
	        String secret = config.getString("play.http.secret.key");

	        Algorithm algorithm = Algorithm.HMAC256(secret);
	        return JWT.create()
	                .withIssuer("ThePlayApp")
	                .withClaim("user_id", i)
	                .sign(algorithm);
	    }
	 
	 public Result requiresJwt() {
	        return jwtControllerHelper.verify(request(), res -> {
	            if (res.left.isPresent()) {
	                return forbidden(res.left.get().toString());
	            }
	            VerifiedJwt verifiedJwt = res.right.get();
	            Logger.debug("{}", verifiedJwt);

	            ObjectNode result = Json.newObject();
	            result.put("access", "granted");
	            result.put("secret_data", "birds fly");
	            return ok(result);
	        });
	    }
	 
	 public Result requiresJwtViaFilter() {
	        Optional<VerifiedJwt> oVerifiedJwt = request().attrs().getOptional(TypedKey.<VerifiedJwt>create("verifiedJwt"));
	        return oVerifiedJwt.map(jwt -> {
	            Logger.debug(jwt.toString());
	            return ok("access granted via filter");
	        }).orElse(forbidden("eh, no verified jwt found"));
	    }


}
