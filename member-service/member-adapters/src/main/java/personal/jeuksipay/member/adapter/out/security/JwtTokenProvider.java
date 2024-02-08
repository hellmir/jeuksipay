package personal.jeuksipay.member.adapter.out.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import personal.jeuksipay.member.application.port.out.AuthenticationPort;
import personal.jeuksipay.member.domain.Member;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements AuthenticationPort {
    private final UserDetailsService userDetailsService;

    private static final String KEY_ROLES = "roles";
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1000 * 60 * 10;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

    private static final String ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE = "토큰이 비어 있습니다.";
    private static final String MALFORMED_JWT_EXCEPTION_MESSAGE = "유효하지 않은 토큰입니다.";
    private static final String UNSUPPORTED_JWT_EXCEPTION_MESSAGE = "지원하지 않는 토큰 형식입니다.";

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @PostConstruct
    public void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    @Override
    public String generateAccessToken(Member member) {
        Claims claims = generateToken(member.getId().toString(), member.getRoles().toStrings());

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public String generateRefreshToken(Member member) {
        Claims claims = generateToken(member.getId().toString(), member.getRoles().toStrings());

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(parseMemberId(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    @Override
    public String parseMemberId(String token) {
        return parseClaims(token).getSubject();
    }

    @Override
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) {
            return false;
        }

        Claims claims = parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }

    private Claims generateToken(String memberId, List<String> roles) {
        Claims claims = Jwts.claims().setSubject(memberId);
        claims.put(KEY_ROLES, roles);

        return claims;
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT_EXCEPTION_MESSAGE, e);
        } catch (MalformedJwtException e) {
            throw new MalformedJwtException(MALFORMED_JWT_EXCEPTION_MESSAGE, e);
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException(UNSUPPORTED_JWT_EXCEPTION_MESSAGE, e);
        }
    }
}
