package com.taskflow.api;
import com.taskflow.model.*;import com.taskflow.repo.*;import org.springframework.web.bind.annotation.*;import java.time.*;import java.util.*;
@RestController @RequestMapping("/api/dashboard") public class DashboardController extends BaseController{
 private final ProjectRepository projects;private final TaskRepository tasks;public DashboardController(ProjectRepository p,TaskRepository t){projects=p;tasks=t;}
 @GetMapping Map<String,Object> summary(){List<Project> ps=projects.findVisibleTo(me().id);long total=ps.stream().mapToLong(p->tasks.countByProjectId(p.id)).sum();long done=ps.stream().mapToLong(p->tasks.countByProjectIdAndStatus(p.id,TaskStatus.DONE)).sum();long active=ps.stream().mapToLong(p->tasks.countByProjectIdAndStatus(p.id,TaskStatus.IN_PROGRESS)).sum();return Map.of("projectCount",ps.size(),"taskCount",total,"completedCount",done,"inProgressCount",active,"completionRate",total==0?0:Math.round((double)done*100/total),"projects",ps.stream().map(p->Map.of("id",p.id,"name",p.name,"color",p.color,"tasks",tasks.countByProjectId(p.id),"completed",tasks.countByProjectIdAndStatus(p.id,TaskStatus.DONE))).toList());}
}
