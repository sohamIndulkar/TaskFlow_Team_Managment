package com.taskflow.api;
import com.taskflow.model.*; import org.springframework.http.HttpStatus; import org.springframework.security.core.context.SecurityContextHolder; import org.springframework.web.server.ResponseStatusException;
abstract class BaseController {
 AppUser me(){Object p=SecurityContextHolder.getContext().getAuthentication().getPrincipal();if(p instanceof AppUser u)return u;throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please sign in.");}
 void requireRole(Role...roles){for(Role r:roles)if(me().role==r)return;throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You do not have permission to perform this action.");}
}
