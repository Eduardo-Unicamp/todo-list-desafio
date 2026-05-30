package com.saldanha.todo_list.infra.security;

import com.saldanha.todo_list.exception.UserNotFoundException;
import com.saldanha.todo_list.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Data
public class SecurityFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = recoverToken(request);
        if(token!=null){
             var subject = tokenService.validateToken(token);
             UserDetailsImp user = new UserDetailsImp(userRepository.findByUsername(subject).orElseThrow(UserNotFoundException::new));

             var authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    private String recoverToken(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null){
            return null;
        }
        return authHeader.replace("Bearer ","");
    }
}
