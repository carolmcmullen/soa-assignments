package com.cooksys.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	 @Query("select p from Project p where p.dueDate < ?")
	 List<Project> isPastDue(Date currentDate);
	
	 @Query("select p from Project p where p.manager.id = ?")
	 List<Project> getProjectByProjectManagerId(long projectManagerId);
}
