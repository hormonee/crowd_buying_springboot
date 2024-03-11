package com.hormonic.crowd_buying.util.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hormonic.crowd_buying.domain.dto.CustomUserDetails;
import com.hormonic.crowd_buying.domain.dto.request.user.UserLoginRequest;
import com.hormonic.crowd_buying.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Iterator;

//@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final long VALID_TIME = 60 * 30 * 1000L;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

        setFilterProcessesUrl("/api/users/login");
    }

    public LoginFilter(AuthenticationManager authenticationManager, AuthenticationManager authenticationManager1, JWTUtil jwtUtil) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager1;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        // 클라이언트 요청에서 userId, userPw 추출
        // 1. 로그인 정보를 폼데이터로 반는 경우
        // 이 때, parameter name을 개별적으로 설정한 userId/userPw 가 아니라 username/password로 설정해서 보내야 한다.
        String userId = obtainUsername(req);
        String userPw = obtainPassword(req);

        /*
        // 2. 로그인 정보를 raw(json) 형태로 반는 경우
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = req.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            userLoginRequest = objectMapper.readValue(messageBody, UserLoginRequest.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String userId = userLoginRequest.getUserId();
        String userPw = userLoginRequest.getUserPw();
        */

        // 스프링 시큐리티에서 userId와 userPw를 검증하기 위해서 token에 담음
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userId, userPw, null);

        // token에 담은 로그인 정보 검증을 위해 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공 - JWT 발급
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authentication) {
        // UserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String userId = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(userId, /*role, */VALID_TIME);  // 30분

        // HTTP 인증 방식은 RFC 7235 정의에 따라 'Authorization: Bearer tokenString'와 같은 인증헤더 형태를 가져야 함
        res.addHeader("Authorization", "Bearer " + token);
    }

    // 로그인 실패
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        // 로그인 실패 - 401 Unauthorized 상태 코드 반환
        response.setStatus(401);
    }
}
