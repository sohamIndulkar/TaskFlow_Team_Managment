package com.taskflow.repo;
import com.taskflow.model.*; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import org.springframework.data.domain.*; import java.util.*;
public interface TaskRepository extends JpaRepository<Task,Long>{
 @EntityGraph(attributePaths={"project","assignee","creator"}) @Query("select t from Task t where t.project.id=:projectId and (:status is null or t.status=:status) and (:priority is null or t.priority=:priority) and (:assigneeId is null or t.assignee.id=:assigneeId) and (:q is null or lower(t.title) like lower(concat('%',:q,'%')) or lower(t.description) like lower(concat('%',:q,'%')))") Page<Task> search(@Param("projectId") Long projectId,@Param("status") TaskStatus status,@Param("priority") Priority priority,@Param("assigneeId") Long assigneeId,@Param("q") String q,Pageable pageable);
 @EntityGraph(attributePaths={"project","assignee","creator"}) @Query("select t from Task t where t.id=:id") Optional<Task> findExpandedById(@Param("id") Long id);
 long countByProjectId(Long projectId); long countByProjectIdAndStatus(Long projectId,TaskStatus status);
}
