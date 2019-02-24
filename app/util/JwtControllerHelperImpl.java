package util;

import java.util.Optional;
import java.util.function.Function;

import javax.inject.Inject;

import play.Logger;
import play.libs.F.Either;
import play.mvc.Http.Request;
import play.mvc.Result;
import static play.mvc.Results.forbidden;


public class JwtControllerHelperImpl implements JwtControllerHelper{

	private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";
    private static final String ERR_AUTHORIZATION_HEADER = "ERR_AUTHORIZATION_HEADER";
    private JwtValidator jwtValidator;
    
    @Inject
    public JwtControllerHelperImpl(JwtValidator jwtValidator) {
        this.jwtValidator = jwtValidator;
    }

	@Override
	public Result verify(Request request, Function<Either<util.JwtValidator.Error, VerifiedJwt>, Result> f) {
				Optional<String> authHeader = request.getHeaders().get(HEADER_AUTHORIZATION);
				
				if(!authHeader.filter(ah -> ah.contains(BEARER)).isPresent()) {
					Logger.error("f=JwtControllerHelperImpl, event=verify, error=authHeaderNotPresent");
					return forbidden(ERR_AUTHORIZATION_HEADER);
				}
				String token = authHeader.map(ah -> ah.replace(BEARER, "")).orElse("");
				return f.apply(jwtValidator.verify(token));
	}
}
