package util;


import javax.inject.Inject;
import javax.inject.Singleton;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.typesafe.config.Config;

import play.libs.F;
import util.JwtValidator.Error;
import play.Logger;

@Singleton
public class JwtValidatorImpl implements JwtValidator {

	private String secret;
    private JWTVerifier verifier;
    
    @Inject
    public JwtValidatorImpl(Config config) {
    	this.secret = config.getString("play.http.secret.key");
    	
    	Algorithm algorithm = Algorithm.HMAC256(secret);
    	verifier= JWT.require(algorithm).withIssuer("ThePlayApp").build();
    }
    
    @Override
    public F.Either<Error, VerifiedJwt> verify(String token) {
    	try
    	{
    		DecodedJWT jwt = verifier.verify(token);
    		VerifiedJwtImpl verifiedJwt = new VerifiedJwtImpl(jwt);
    		return F.Either.Right(verifiedJwt);
    	}catch (JWTVerificationException exception) {
    		
    		//Invalid signature/claims
    		Logger.error("f=JwtValidatorImpl, event=verify, exception=JWTVerificationException, msg={}", exception.getMessage());
    		return F.Either.Left(Error.ERR_INVALID_SIGNATURE_OR_CLAIM);
    	}
    }
    
}
