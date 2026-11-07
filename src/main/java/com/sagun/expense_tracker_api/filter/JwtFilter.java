package com.sagun.expense_tracker_api.filter;

import com.sagun.expense_tracker_api.principal.UserPrincipal;
import com.sagun.expense_tracker_api.service.CustomUserDetailsService;
import com.sagun.expense_tracker_api.utils.JWTUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;
    private final CustomUserDetailsService service;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || ! authHeader.regionMatches(true,0,"Bearer ",0,7)){
            filterChain.doFilter(request,response);
            return;
        }
        try {
            String token = authHeader.substring(7);
            String email = jwtUtils.getEmail(token);
            if(email != null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserPrincipal userDetails = service.loadUserByUsername(email);
                if(jwtUtils.isValidToken(email,token,userDetails)){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("JWT authentication successful");
                }
                else {
                    log.info("JWT validation failed");

                }
            }
        } catch (Exception exception){
            log.error("Cannot set user authentication: {}",exception.getMessage());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write("{\"success\":false,\"message\":\"invalid or expired token.\"}");
            return;
        }
        filterChain.doFilter(request,response);
    }
}
