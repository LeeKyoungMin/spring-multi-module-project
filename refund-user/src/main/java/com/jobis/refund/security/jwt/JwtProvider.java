package com.jobis.refund.security.jwt;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.jobis.refund.security.UserDetailsServiceImpl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private PrivateKey privateKey;

    private PublicKey publicKey;

    @Value("${jwt.accessExpireTime}")
    private String accessExpireTime;

    @Value("${jwt.refreshExpireTime}")
    private String refreshExpireTime;
    
    @Value("${jwt.private-key}")
    private String jwtPrivateKey;

    @Value("${jwt.public-key}")
    private String jwtPublicKey;

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @PostConstruct
    protected void init() throws IOException {
    	
        byte[] priKeyBytes = parsePEMFile(jwtPrivateKey);
        privateKey = getPrivateKey(priKeyBytes, "RSA");

        byte[] pubKeyBytes = parsePEMFile(jwtPublicKey);
        publicKey = getPublicKey(pubKeyBytes, "RSA");
    }

    public byte[] parsePEMFile(String file) throws IOException{
        byte[] priKeyBytes = Base64.getDecoder().decode(file);
        return priKeyBytes;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String createToken(String userId) {

        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userId", userId);

        Date issuedAt = new Date();
        issuedAt.setTime(issuedAt.getTime());
        Date expiration = new Date();
        expiration.setTime(expiration.getTime() + Long.parseLong(accessExpireTime));

        return Jwts.builder()
                    .setHeader(headers)
                    .setClaims(payloads)
                    .setSubject("user")
                    .setIssuedAt(issuedAt)
                    .setExpiration(expiration)
                    .signWith(privateKey,SignatureAlgorithm.RS256)
                    .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }


    public String createRefreshToken(String userId) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("userId", userId);

        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(Duration.ofMillis(Long.parseLong(refreshExpireTime)));

        return Jwts.builder()
                   .setHeader(headers)
                   .setClaims(payloads)
                   .setSubject("user")
                   .setExpiration(Date.from(expiration))
                   .setIssuedAt(Date.from(issuedAt))
                   .signWith(privateKey,SignatureAlgorithm.RS256)
                   .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(this.getUserInfo(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserInfo(String token) {
        String userInfo = "";
        try{
            userInfo = (String) Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token).getBody().get("userId");
        }catch(ExpiredJwtException e){
            throw new ExpiredJwtException(null, null, userInfo);
        }catch(IllegalArgumentException e){
            throw new IllegalArgumentException();
        }
        return userInfo;
    }

    public boolean validateJwtToken(ServletRequest request, String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            request.setAttribute("exception", "MalformedJwtException");
        } catch (ExpiredJwtException e) {
            request.setAttribute("exception", "ExpiredJwtException");
        } catch (UnsupportedJwtException e) {
            request.setAttribute("exception", "UnsupportedJwtException");
        } catch (IllegalArgumentException e) {
            request.setAttribute("exception", "IllegalArgumentException");
        } catch (Exception e){
            request.setAttribute("exception", "Exception");
        }
        return false;
    }

    public PublicKey getPublicKey(byte[] keyBytes, String algorithm) {
        PublicKey publicKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            publicKey = kf.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not reconstruct the public key, the given algorithm could not be found.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Could not reconstruct the public key");
        }

        return publicKey;
    }
    
    public PrivateKey getPrivateKey(byte[] keyBytes, String algorithm) {
        RSAPrivateKey privateKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance(algorithm);
            EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            privateKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Could not reconstruct the private key, the given algorithm could not be found.");
        } catch (InvalidKeySpecException e) {
            System.out.println("Could not reconstruct the private key");
        }
        return privateKey;
    }
}

