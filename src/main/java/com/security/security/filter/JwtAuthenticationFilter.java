package com.security.security.filter;

import com.security.security.jwt.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

//        String requestURI = request.getRequestURI();
//        System.out.println(requestURI);
        String token = getJwtFromRequest(request);
//        System.out.println("Received token" + token);
//
//        System.out.println("Authorization Header: " + request.getHeader("Authorization"));
//        System.out.println("Extracted JWT: " + token);

        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            // 토큰이 유효한 경우, 사용자 정보 로드
            String userId = tokenProvider.getUserIdFromJWT(token);

            System.out.println("UserId from JWT: " + userId);

            // DB에서 해당 사용자 ID에 대한 UserDetails 객체를 로드
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
            // 스프링 시큐리티의 인증된 사용자 정보 객체 생성
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // SecurityContext에 사용자 인증 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            log.info("Extracted JWT token");
            return token;
        } else {
            log.info("Authorization header is either empty or does not start with 'Bearer '");
        }

        return null;
    }

}

