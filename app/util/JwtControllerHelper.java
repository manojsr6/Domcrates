package util;

import java.util.function.Function;

import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

public interface JwtControllerHelper {
	public Result verify(Http.Request request, Function<F.Either<JwtValidator.Error, VerifiedJwt>, Result> f);
}
