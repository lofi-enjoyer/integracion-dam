package me.lofienjoyer.quiet.auth.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service to handle operations involving JWT tokens
 */
@Service
public class JwtService {

    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    /**
     * Extracts the username of a token
     * @param token JWT token
     * @return Username of the token
     * @throws Exception
     */
    public String extractUsername(String token) throws Exception {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts the expiration date of a token
     * @param token JWT token
     * @return Expiration date of the token
     * @throws Exception
     */
    public Date extractExpiration(String token) throws Exception {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a specific claim of a token
     * @param token JWT token
     * @return Claim specified
     * @throws Exception
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) throws Exception {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims of a token
     * @param token JWT token
     * @return All claims of the token
     * @throws Exception
     */
    private Claims extractAllClaims(String token) throws Exception {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * @param token JWT token
     * @return True if token is expired, false if otherwise
     * @throws Exception
     */
    private Boolean isTokenExpired(String token) throws Exception {
        return extractExpiration(token).before(new Date());
    }

    /**
     * @param token JWT token
     * @return True if token is valid, false if otherwise
     * @throws Exception
     */
    public Boolean validateToken(String token, UserDetails userDetails) throws Exception{
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Generates a JWT token
     * @param userName Username for the token
     * @return Generated token
     */
    public String generateToken(String userName){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName);
    }

    /**
     * Generates a JWT token
     * @param claims Claims to store in the token
     * @param userName Username for the token
     * @return Generated token
     */
    private String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    /**
     * Returns the JWT sign key
     * @return JWT sign key
     */
    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
