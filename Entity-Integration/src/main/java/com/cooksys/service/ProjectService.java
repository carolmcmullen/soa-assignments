package com.cooksys.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.criteria.CriteriaBuilder;

import org.springframework.stereotype.Service;

import com.cooksys.dto.ProjectDto;
import com.cooksys.entity.Project;
import com.cooksys.exception.ReferencedEntityNotFoundException;
import com.cooksys.mapper.ProjectMapper;
import com.cooksys.repository.ProjectRepository;

@Service
public class ProjectService {

	private ProjectRepository repo;
	private ProjectMapper mapper;

	public ProjectService(ProjectRepository repo, ProjectMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}
	
	public List<ProjectDto> getAll() {
		return repo.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
	}
	
	public List<ProjectDto> getAllOverdueProjects() {
		Date date = new Date();
		return repo.isPastDue(date).stream().map(mapper::toDto).collect(Collectors.toList()); 
	}
	
	public List<ProjectDto> getProjectByProjectManagerId(long projectManagerId) {
		return repo.getProjectByProjectManagerId(projectManagerId).stream().map(mapper::toDto).collect(Collectors.toList()); 
	}

	public boolean has(Long id) {
		return repo.exists(id);
	}

	public ProjectDto get(Long id) {
		mustExist(id);
		return mapper.toDto(repo.findOne(id));
	}

	public Long post(ProjectDto projectDto) {
		projectDto.setId(null);
		return repo.save(mapper.toEntity(projectDto)).getId();
	}

	public void put(Long id, ProjectDto projectDto) {
		mustExist(id);
		projectDto.setId(id);
		repo.save(mapper.toEntity(projectDto));
	}
	
	private void mustExist(Long id) {
		if(!has(id))
			throw new ReferencedEntityNotFoundException(Project.class, id);
	}

	public void delete(Long id) {
		mustExist(id);
		repo.delete(id);
	}
}
