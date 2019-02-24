package util;

import java.util.Date;

public interface VerifiedJwt {
    String getHeader();
    String getPayload();
    String getIssuer();
    String getUserId();
    Date getExpiresAt();
}
