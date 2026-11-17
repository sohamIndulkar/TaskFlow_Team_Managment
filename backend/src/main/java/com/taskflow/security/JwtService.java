package com.taskflow.security;
import com.taskflow.model.AppUser; import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import javax.crypto.SecretKey; import java.nio.charset.StandardCharsets; import java.time.Instant; import java.util.Date;
@Service public class JwtService {
 private final SecretKey key; private final long accessMinutes,refreshDays;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.access-minutes}") long accessMinutes,@Value("${app.jwt.refresh-days}") long refreshDays){this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.accessMinutes=accessMinutes;this.refreshDays=refreshDays;}
 public String access(AppUser u){return issue(u,"access",accessMinutes*60);} public String refresh(AppUser u){return issue(u,"refresh",refreshDays*86400);}
 private String issue(AppUser u,String type,long seconds){Instant now=Instant.now();return Jwts.builder().subject(u.email).claim("role",u.role.name()).claim("type",type).issuedAt(Date.from(now)).expiration(Date.from(now.plusSeconds(seconds))).signWith(key).compact();}
 public Claims parse(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
}
