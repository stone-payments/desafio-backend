package glx.com.StarWarsStore.security;

import static java.util.Collections.emptyList;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import glx.com.StarWarsStore.controllers.StarStoreController;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

class TokenAuthenticationService {
	private static Logger log = LoggerFactory.getLogger(StarStoreController.class);

	static final long EXPIRATIONTIME = 864_000_000;
	static final String SECRET = "ThisIsASecret";
	static final String TOKEN_PREFIX = "Jedi";
	static final String HEADER_STRING = "Authorization";

	static void addAuthentication(HttpServletResponse res, String username) {
		String JWT = Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
				.signWith(SignatureAlgorithm.HS512, SECRET).compact();
		res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
	}

	static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		try {
			
			if (token != null) {
				String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
						.getBody().getSubject();

				return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
			}
		} catch (Exception e) {

			log.error("Token invalid: "+ token);
			
		}

		return null;
	}
}