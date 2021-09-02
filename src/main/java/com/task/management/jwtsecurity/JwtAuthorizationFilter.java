package com.task.management.jwtsecurity;

import com.task.management.error.ErrorCode;
import com.task.management.error.ErrorResponse;
import com.task.management.exception.JwtExpiredTokenException;
import com.task.management.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Divineit-Iftekher on 4/29/2018.
 */
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {


  UserDetailsService userDetailsService;

  public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
    super(authenticationManager);
    this.userDetailsService = userDetailsService;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws JwtExpiredTokenException,IOException, ServletException {
    UsernamePasswordAuthenticationToken authentication = null;
    String header = request.getHeader(SecurityConstants.HEADER_STRING);

    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }

    try {
      authentication = getAuthentication(header);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      chain.doFilter(request, response);
    } catch (ExpiredJwtException exp) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(response.getWriter(), new ErrorResponse(HttpStatus.GATEWAY_TIMEOUT, "Token has expired", ErrorCode.TOKEN_EXPIRED, new Date()));
    }
    //  SecurityContextHolder.clearContext();

  }

  public UsernamePasswordAuthenticationToken getAuthentication(String token) {

    //  String token = req.getHeader(SecurityConstants.HEADER_STRING);
    System.out.println(token);
    if (token != null) {

      String user = Jwts.parser()
        .setSigningKey(SecurityConstants.SECRET)
        .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
        .getBody()
        .getSubject();

      if (user != null) {
        User userDetails = (User) userDetailsService.loadUserByUsername(user);
        return new UsernamePasswordAuthenticationToken(user, null, userDetails.getAuthorities());
      }
    }

    return null;
  }

}
