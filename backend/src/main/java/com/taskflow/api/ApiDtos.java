package com.taskflow.api;
import com.taskflow.model.*; import jakarta.validation.constraints.*; import java.time.*; import java.util.*;
public class ApiDtos {
 public record UserView(Long id,String name,String email,Role role,String avatar) {static UserView of(AppUser u){return new UserView(u.id,u.name,u.email,u.role,u.avatar);}}
 public record AuthRequest(@Email @NotBlank String email,@NotBlank @Size(min=8) String password){}
 public record RegisterRequest(@NotBlank @Size(min=2,max=80) String name,@Email @NotBlank String email,@NotBlank @Size(min=8,max=72) String password){}
 public record AuthResponse(String accessToken,String refreshToken,UserView user){}
 public record ProjectRequest(@NotBlank @Size(max=120) String name,@Size(max=1200) String description,@Pattern(regexp="^#[0-9a-fA-F]{6}$") String color,List<Long> memberIds){}
 public record ProjectView(Long id,String name,String description,String color,UserView owner,List<UserView> members,Instant createdAt,long taskCount,long completedCount){}
 public record TaskRequest(@NotBlank @Size(max=180) String title,@Size(max=2000) String description,Priority priority,TaskStatus status,Long assigneeId,LocalDate dueDate){}
 public record TaskView(Long id,String title,String description,TaskStatus status,Priority priority,Long projectId,String projectName,UserView assignee,UserView creator,LocalDate dueDate,Instant createdAt,Instant updatedAt){}
 public record CommentRequest(@NotBlank @Size(max=2000) String body){}
 public record CommentView(Long id,String body,UserView author,Instant createdAt){}
 public record PageView<T>(List<T> content,int page,int size,long totalElements,int totalPages,boolean first,boolean last){}
}
