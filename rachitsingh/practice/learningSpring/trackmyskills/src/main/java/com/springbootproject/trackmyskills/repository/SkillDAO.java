package com.springbootproject.trackmyskills.repository;

import java.util.List;

import com.springbootproject.trackmyskills.model.Skill;

public interface SkillDAO {
	List<Skill> getAllSkills() ;
	Skill getSkillById(Integer id);
}
