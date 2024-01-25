package com.example.todolist.security;

import com.example.todolist.domain.RefreshToken;
import com.example.todolist.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtTokenProvider {
    private final MemberDetailsService memberDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    // 되도록 시크릿키는 유추하기 어렵게 복잡하게 설정하는 것이 좋다.
    private String secretKey = "Adfaoidalksdhcpxzjhpdhfpdkoxaodfid";
    private static long ACCESS_TOKEN_EXPIRE_TIME = 1 * 60 * 1000L; // 테스트를 수월하게 하기위해 1분으로 설정
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1 * 24 * 60 * 60 * 1000L;    // 1일

    /*
        시크릿키를 인코딩한 뒤 결과값을 String형태로 반환하여 시크릿키에 저장.
        인코딩을 할 때 바이트로 변환하지 않으면 아스키코드형태로 해석되어 인코딩이 이루어지기 때문에
        바이트로 변환한 값을 인코딩해주어야한다.
    */
    @PostConstruct // 객체 의존설정이 끝난 뒤 자동으로 실행
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    /*
        토큰을 생성하는 메소드로 로그인이 정상적으로 완료되면 수행된다.
        Claims객체는 claim를 key-value쌍으로 해시맵에 저장하는 객체이다.
        로그인이 정상적으로 수행되면 username만 토큰에 저장한 뒤 클라이언트로 보내준다.password는 보안상 jwt에 저장하지 않는다.
        클라이언트는 인증이 필요한 request를 보낼때 토큰을 같이 보내면 된다. 그러면 별도의 인증과정을 거치지않고 토큰의 유효여부,조작여부만 검사한다.
     */
    public String createAccessToken(String loginId, String role) {
        //subject는 주제로 제목이라고 보면 된다.제목에 loginId를 넣는다.
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("role", role); // 정보는 key/value 쌍으로 저장됩니다.
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장(클레임 저장)
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME)) // 토큰 유효 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘
                .compact();
    }
    public String createRefreshToken(String loginId) {
        Claims claims = Jwts.claims().setSubject(loginId);
        Date now = new Date();
        String refreshToken= Jwts.builder()
                .setClaims(claims) // 정보 저장(클레임 저장)
                .setIssuedAt(now) // 토큰 발행 시간
                .setExpiration(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME)) // 토큰 유효 시간
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 사용할 암호화 알고리즘
                .compact();
        RefreshToken redis=new RefreshToken(loginId,refreshToken);
        refreshTokenRepository.save(redis);

        return refreshToken;
    }
     /*
        토큰에서 인증정보를 가져오는 메소드.
        토큰을 바탕으로 UsernamePasswordAuthenticationToken을 생성하고 SprintContextHolder에 저장해야한다.
        따라서 토큰에서 username을 추출한 뒤 이 값으로 UserDetails객체를 생성한다.
        그 뒤 UserDetails객체와 Authority를 통해 UsernamePasswordAuthenticationToken를 생성해 반환한다.
     */
     // JWT 토큰에서 인증 정보 조회
     public Authentication getAuthentication(String token) {
         UserDetails userDetails = memberDetailsService.loadUserByUsername(this.getUserLoginId(token));
         return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
     }

     /*
        토큰을 받아 sub(username)값을 추출하는 메소드.
     */
     public String getUserLoginId(String token) {
         return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
     }
    // Request 의 Header 에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveAccessToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
     /*
        토큰의 조작여부와 유효여부를 판단하는 메소드.
        claims.getBody().getExpiration().before(new Date())를 통해 유효여부를 판단한다.
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token)를 통해 조작여부를 판단한다.
        만약 조작이 의심되면 예외를 던진다. (+ parser()는 deprecated 됨)
     */
     public boolean validateToken(String jwtToken) {
         try {
             Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
             return !claims.getBody().getExpiration().before(new Date());
         } catch (Exception e) {
             return false;
         }
     }

}
