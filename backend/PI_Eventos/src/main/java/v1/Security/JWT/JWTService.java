package v1.Security.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import v1._1_Model.Usuario;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    static final Logger logger = LoggerFactory.getLogger(JWTService.class);
    public String getToken(Usuario user) {
        return getToken(new HashMap<>(), user);
    }

    private String getToken(Map<String, Object> extraClaims, Usuario user){

        logger.info("Generando token para usuario: {}", user.getUserName());

        String token = Jwts
                .builder()
                .claims(extraClaims)
                .claim("id", user.getId())
                .claim("roles", user.getRole())
                .subject(user.getUserName())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .signWith(getKey())
                .compact();

        logger.info("Token generado: {}", token);

        return token;
    }

    private SecretKey getKey() {
        logger.info("Decodificando la clave secreta.");

        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims (String token){
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim (String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration (String token) {
     return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired (String token) {
        return getExpiration(token).before(new Date()); // ve si el token ha expirado pasandole la fecha de este momento
    }


}
