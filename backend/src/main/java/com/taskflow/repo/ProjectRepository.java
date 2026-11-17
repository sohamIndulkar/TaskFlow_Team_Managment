package com.taskflow.repo;
import com.taskflow.model.Project; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*;
public interface ProjectRepository extends JpaRepository<Project,Long>{
 @EntityGraph(attributePaths={"owner","members"}) @Query("select distinct p from Project p where p.owner.id=:uid or exists(select m from p.members m where m.id=:uid)") List<Project> findVisibleTo(@Param("uid") Long uid);
 @EntityGraph(attributePaths={"owner","members"}) @Query("select p from Project p where p.id=:id") Optional<Project> findExpandedById(@Param("id") Long id);
}
