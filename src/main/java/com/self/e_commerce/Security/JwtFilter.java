package com.self.e_commerce.Security;

import com.self.e_commerce.Service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    ApplicationContext context;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/user/register") || path.equals("/user/login");
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = "";
        String username = "";
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(username);

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtService.extractUserName(token);
            System.out.println("Extracted token: " + token);
            System.out.println("Extracted username: " + username);
        }
        if(StringUtils.hasText(token) && jwtService.validateToken(token,userDetails)){
            String userId = jwtService.extractUserId(token);
            String requestedUserId = request.getParameter("userId");

            if (requestedUserId != null && !userId.equals(requestedUserId)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Unauthorized access");
                return;
            }
        }
        if(username!= null && SecurityContextHolder.getContext().getAuthentication() == null){
//            UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);

            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
