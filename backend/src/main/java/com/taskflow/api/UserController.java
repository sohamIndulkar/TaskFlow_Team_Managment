package com.taskflow.api;
import com.taskflow.model.*;import com.taskflow.repo.UserRepository;import org.springframework.web.bind.annotation.*;import java.util.*;import static com.taskflow.api.ApiDtos.*;
@RestController @RequestMapping("/api/users") public class UserController extends BaseController{
 private final UserRepository users;public UserController(UserRepository users){this.users=users;}
 @GetMapping List<UserView> list(@RequestParam(required=false) String q){return users.findAll().stream().filter(u->q==null||u.name.toLowerCase().contains(q.toLowerCase())||u.email.toLowerCase().contains(q.toLowerCase())).sorted(Comparator.comparing(u->u.name.toLowerCase())).map(UserView::of).toList();}
}
