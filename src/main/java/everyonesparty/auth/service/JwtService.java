package everyonesparty.auth.service;

import everyonesparty.auth.dto.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

/**
 * > 참고 링크: https://daddyprogrammer.org/post/636/springboot2-springsecurity-authentication-authorization/
 * > JWT 토큰을 생성 및 검증 모듈
 *      > 여기선 생성 부분만 사용
 *      > 검증 부분 로직은 api gateway 에서 사용할 것임
 * **/
@RequiredArgsConstructor
@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.token-validity-in-seconds}")
    private long tokenValidMilisecond; // 1시간만 토큰 유효


    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    // Jwt 토큰 생성
    public String createToken(String userPk, Set<UserRole> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", new ArrayList<>(roles));
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 데이터
                .setIssuedAt(now) // 토큰 발행일자
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // set Expire Time
                .signWith(SignatureAlgorithm.HS256, secretKey) // 암호화 알고리즘, secret값 세팅
                .compact();
    }
}