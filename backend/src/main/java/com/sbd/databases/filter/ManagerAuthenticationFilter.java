package com.sbd.databases.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ManagerAuthenticationFilter implements javax.servlet.Filter
{
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public ManagerAuthenticationFilter(JwtTokenUtil jwtTokenUtil)
    {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        try
        {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            Claims claims = jwtTokenUtil.getAllClaimsFromToken(jwtTokenUtil.getTokenFromRequest(request));
            if (claims.get("roles", String.class).equals(RolesEnum.MANAGER.name()))
            {
                servletRequest.setAttribute("claims", claims);
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else
            {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Log in to view this page.");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Log in to view this page.");
        }

    }
}
