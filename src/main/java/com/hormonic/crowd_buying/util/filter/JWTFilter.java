package com.hormonic.crowd_buying.util.filter;

import com.hormonic.crowd_buying.domain.dto.CustomUserDetails;
import com.hormonic.crowd_buying.domain.entity.User;
import com.hormonic.crowd_buying.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        // request에서 Authorization 헤더를 찾음
        String authorization= req.getHeader("Authorization");

        // Authorization 헤더 검증
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");
            filterChain.doFilter(req, res);

            return;
        }

        System.out.println("authorization now");
        // Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        // 토큰 소멸 시간 검증
        try {
            jwtUtil.isExpired(token);

        } catch (ExpiredJwtException e) {
            System.out.println("token expired");
            filterChain.doFilter(req, res);
            return;
        }

        // 토큰에서 userId와 /* role 획득 */
        String userId = jwtUtil.getUserId(token);
//        String role = jwtUtil.getRole(token);

        // User Entity를 생성하여 값 set
        User user = User.builder()
                        .userId(userId)
                        .userPw("tempPassword")  // 아무 값이나 넣어둬도 됨
                        .build();
//        user.setRole(role);

        // UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // 스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        // 세션에 사용자 등록
        /* 멀티 스레드일 때 문제를 야기할 수 있으므로 SecurityContext 객체를 생성하여 사용
        SecurityContextHolder.getContext().setAuthentication(authToken);
        */
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);

        filterChain.doFilter(req, res);
    }
}
