package com.taskflow.api;
import com.taskflow.model.*;import com.taskflow.repo.UserRepository;import org.springframework.web.bind.annotation.*;import org.springframework.web.server.ResponseStatusException;import org.springframework.http.HttpStatus;import java.util.*;import static com.taskflow.api.ApiDtos.*;
@RestController @RequestMapping("/api/admin") public class AdminController extends BaseController{
 private final UserRepository users;public AdminController(UserRepository u){users=u;}
 @PatchMapping("/users/{id}/role") UserView role(@PathVariable Long id,@RequestBody Map<String,String> body){requireRole(Role.ADMIN);AppUser u=users.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"User not found."));try{u.role=Role.valueOf(body.get("role"));}catch(Exception e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Choose a valid role.");}return UserView.of(users.save(u));}
}
