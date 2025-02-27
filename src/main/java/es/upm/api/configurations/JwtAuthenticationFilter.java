package es.upm.api.configurations;

import es.upm.api.domain.model.Role;
import es.upm.api.domain.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain chain)
            throws IOException, ServletException {
        String token = jwtService.extractToken(request.getHeader(AUTHORIZATION));
        if (!token.isEmpty()) {
            GrantedAuthority authority = new SimpleGrantedAuthority(Role.PREFIX + jwtService.role(token));
            org.springframework.security.core.userdetails.User userDetails =
                    new org.springframework.security.core.userdetails.User(jwtService.user(token), "", List.of(authority));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            request.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
        }
        chain.doFilter(request, response);
    }

}
