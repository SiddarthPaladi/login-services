package io.movecloud.movecloud_login_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.movecloud.movecloud_login_service.exception.MoveCloudException;
import io.movecloud.movecloud_login_service.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

/**
 * Service for handling JWT operations such as token generation, validation, and extraction.
 *
 * <p>\@author Dushyanth \@since 2024.1.6
 */
@Service
@Slf4j
public class JwtService {
    private String secretKey;

    public JwtService() {
        secretKey = generateSecretKey();
    }

    /**
     * Generates a secret key for HMAC-SHA256 algorithm.
     *
     * @return the generated secret key as a Base64 encoded string
     */
    private String generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(Constants.HMAC_ALGORITHM);
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            log.error(Constants.ERROR_GENERATING_KEY_LOG, e);
            throw new MoveCloudException(Constants.ERROR_GENERATING_KEY_LOG);
        }
    }

    /**
     * Retrieves the secret key for signing JWT tokens.
     *
     * @return the secret key
     */
    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generates a JWT token for the given user details.
     *
     * @param businessEmail the business email of the user
     * @param organizationName the organization name of the user
     * @param sessionId the session ID
     * @return the generated JWT token
     */
    public String generateToken(String businessEmail, String organizationName, String sessionId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(Constants.ORGANIZATION_NAME_KEY, organizationName);
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(businessEmail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        log.info(Constants.TOKEN_GENERATED_LOG, token);
        return token;
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token
     * @return the username
     */
    public String extractUsername(String token) {
        log.info(Constants.EXTRACTING_USERNAME_LOG, token);
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the given JWT token.
     *
     * @param token the JWT token
     * @param claimsResolver the function to resolve the claim
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token
     * @return the claims
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Validates the given JWT token against the user details.
     *
     * @param token the JWT token
     * @param userDetails the user details
     * @return true if the token is valid, false otherwise
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        log.info(Constants.VALIDATING_TOKEN_LOG, userDetails.getUsername());
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Checks if the given JWT token is expired.
     *
     * @param token the JWT token
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        boolean expired = extractExpiration(token).before(new Date());
        if (expired) {
            log.warn(Constants.TOKEN_EXPIRED_LOG, token);
        }
        return expired;
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token the JWT token
     * @return the expiration date
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generates a new session ID.
     *
     * @return the generated session ID
     */
    public String generateSessionId() {
        String sessionId = UUID.randomUUID().toString();
        log.info(Constants.SESSION_ID_GENERATED_LOG, sessionId);
        return sessionId;
    }
}