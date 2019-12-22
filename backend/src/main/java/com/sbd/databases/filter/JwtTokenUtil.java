package com.sbd.databases.filter;

import com.sbd.databases.model.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable
{
    @Value("this secret is so secret")
    private String secret;

    public String getCustomerNameFromRequest(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return getCustomerNameFromToken(token);
    }

    public String getCustomerNameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Customer customer)
    {
        return doGenerateToken(customer.getName());
    }

    private String doGenerateToken(String subject)
    {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", "customer")
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 1000 * 60 * 5))
                .signWith(SignatureAlgorithm.HS384, secret)
                .compact();
    }

    public Boolean validateToken(String token, Customer customer)
    {
        final String name = getCustomerNameFromToken(token);
        return (name.equals(customer.getName()) && !isTokenExpired(token));
    }

}
