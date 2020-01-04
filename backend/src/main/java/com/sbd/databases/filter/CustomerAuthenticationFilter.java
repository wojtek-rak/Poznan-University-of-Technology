package com.sbd.databases.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

@Component
public class CustomerAuthenticationFilter implements Filter
{
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public CustomerAuthenticationFilter(JwtTokenUtil jwtTokenUtil)
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
            if (claims.get("roles", String.class).equals(RolesEnum.CUSTOMER.name()))
            {
                servletRequest.setAttribute("claims", claims);
                filterChain.doFilter(servletRequest, servletResponse);
            }
            else
            {
                final HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
                wrapper.sendError(HttpServletResponse.SC_FORBIDDEN, "You have no access to this resource.");
            }
        }
        catch (Exception e)
        {
            final HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            wrapper.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You can not view this page.");
        }

    }
}