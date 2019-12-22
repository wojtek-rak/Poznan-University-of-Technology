package com.sbd.databases.filter;

import com.sbd.databases.model.Customer;
import com.sbd.databases.model.DTO.ManagerLoginDTO;
import com.sbd.databases.model.Manager;
import com.sbd.databases.service.ManagerService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable
{
    @Value("this secret is so secret")
    private String secret;
    private ManagerService managerService;

    @Autowired
    public JwtTokenUtil(ManagerService managerService)
    {
        this.managerService = managerService;
    }

    public String getCustomerNameFromRequest(HttpServletRequest request)
    {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        return getCustomerNameFromToken(token);
    }

    public Manager getManagerFromRequest(HttpServletRequest request, Integer managerId)
    {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        Manager manager = managerService.findById(managerId);

        if (manager != null)
        {
            return getManagerFromToken(token, manager.getPassword());
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    public String getCustomerNameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
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

    public Manager getManagerFromToken(String token, String secret)
    {
        return managerService.findByUsername(getClaimFromToken(token, secret, Claims::getSubject));
    }

    public Date getExpirationDateFromToken(String token, String secret)
    {
        return getClaimFromToken(token, secret, Claims::getExpiration);
    }

    public Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, String secret, Function<Claims, T> claimsResolver)
    {
        final Claims claims = getAllClaimsFromToken(token, secret);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token, String secret)
    {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS384.getJcaName());

        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token, String secret)
    {
        final Date expiration = getExpirationDateFromToken(token, secret);
        return expiration.before(new Date());
    }

    private Boolean isTokenExpired(String token)
    {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(ManagerLoginDTO managerLoginDTO)
    {
        return doGenerateToken(managerLoginDTO.getUsername(), managerLoginDTO.getPassword());
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
                .setExpiration(new Date(currentTimeMillis + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS384, secret)
                .compact();
    }

    private String doGenerateToken(String subject, String secret)
    {
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS384.getJcaName());

        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)
                .claim("roles", "manager")
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 1000 * 60 * 60 * 24))
                .signWith(SignatureAlgorithm.HS384, signingKey)
                .compact();
    }

    public Boolean validateToken(String token, Customer customer)
    {
        final String name = getCustomerNameFromToken(token);
        return (name.equals(customer.getName()) && !isTokenExpired(token));
    }

}
