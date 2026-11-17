package com.taskflow.api;
import com.taskflow.model.*; import com.taskflow.repo.UserRepository; import com.taskflow.security.JwtService; import io.jsonwebtoken.Claims; import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import java.util.*;
import static com.taskflow.api.ApiDtos.*;
@RestController @RequestMapping("/api/auth") public class AuthController extends BaseController{
 private final UserRepository users;private final PasswordEncoder encoder;private final JwtService jwt;
 public AuthController(UserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 @PostMapping("/register") AuthResponse register(@Valid @RequestBody RegisterRequest r){if(users.existsByEmailIgnoreCase(r.email()))throw new ResponseStatusException(HttpStatus.CONFLICT,"An account with this email already exists.");AppUser u=users.save(new AppUser(r.name().trim(),r.email().toLowerCase(),encoder.encode(r.password()),Role.MEMBER));return tokens(u);}
 @PostMapping("/login") AuthResponse login(@Valid @RequestBody AuthRequest r){AppUser u=users.findByEmailIgnoreCase(r.email()).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email or password is incorrect."));if(!encoder.matches(r.password(),u.passwordHash))throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Email or password is incorrect.");return tokens(u);}
 @PostMapping("/refresh") AuthResponse refresh(@RequestBody Map<String,String> body){try{Claims c=jwt.parse(Objects.requireNonNull(body.get("refreshToken")));if(!"refresh".equals(c.get("type")))throw new Exception();return tokens(users.findByEmailIgnoreCase(c.getSubject()).orElseThrow());}catch(Exception e){throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Your session expired. Please sign in again.");}}
 @GetMapping("/me") UserView meView(){return UserView.of(me());}
 private AuthResponse tokens(AppUser u){return new AuthResponse(jwt.access(u),jwt.refresh(u),UserView.of(u));}
}
