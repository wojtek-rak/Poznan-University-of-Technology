package com.sbd.databases.filter;

import com.sbd.databases.model.Customer;
import com.sbd.databases.model.Manager;
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
    @Value("${secret}")
    private String SECRET;

    public String getTokenFromRequest(HttpServletRequest request)
    {
        return request.getHeader("Authorization").replace("Bearer ", "");
    }

    public Integer getIdFromRequest(HttpServletRequest request)
    {
        return getIdFromToken(getTokenFromRequest(request));
    }

    public Integer getIdFromToken(String token)
    {
        return Integer.valueOf(getClaimFromToken(token, Claims::getSubject));
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Manager manager)
    {
        return doGenerateToken(manager.getId().toString(), RolesEnum.MANAGER);
    }

    public String generateToken(Customer customer)
    {
        return doGenerateToken(customer.getId().toString(), RolesEnum.CUSTOMER);
    }

    private String doGenerateToken(String subject, RolesEnum role)
    {
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", role)
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS384, SECRET)
                .compact();
    }

    public Boolean validateToken(String token, Customer customer)
    {
        final Integer id = getIdFromToken(token);
        return (id.equals(customer.getId()) && !isTokenExpired(token));
    }

    public Boolean validateToken(String token, Manager manager)
    {
        final Integer id = getIdFromToken(token);
        return (id.equals(manager.getId()) && !isTokenExpired(token));
    }
}
