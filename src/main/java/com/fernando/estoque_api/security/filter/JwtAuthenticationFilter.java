package com.fernando.estoque_api.security.filter;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fernando.estoque_api.entity.User;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.repository.UserRepository;
import com.fernando.estoque_api.security.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    public JwtAuthenticationFilter(JwtService jwtService,UserRepository userRepository){
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }
    public void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain filterChain)throws ServletException, IOException{
        String bearer= request.getHeader("Authorization");
        if(bearer != null) {
            String token = bearer.startsWith("Bearer ") ? bearer.substring(7) : bearer;
            String email = jwtService.extractSubject(token);
            if(jwtService.validateToken(token, email)){
            
            User user = userRepository
            .findByEmailAndDeletedAtIsNull(email)
            .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado."));
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
            SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);
            };
        }
        filterChain.doFilter(request, response);
        }
}
