package com.taskflow.repo;
import com.taskflow.model.TaskComment; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface CommentRepository extends JpaRepository<TaskComment,Long>{ @EntityGraph(attributePaths={"author"}) @Query("select c from TaskComment c where c.task.id=:taskId order by c.createdAt asc") List<TaskComment> findForTask(@Param("taskId") Long taskId); }
