package com.springbootproject.trackmyskills.service;

import java.util.List;
import com.springbootproject.trackmyskills.model.Skill;
import com.springbootproject.trackmyskills.Utilities.SkillsTreeBuilder;

public interface SkillService {

	void loadSkillsTree();

	List<Skill> getAllSkills();

	List<Skill> getRootSkills();

	List<Skill> getChildrenSkills(Integer id);

	Skill getSkillById(Integer id);

	List<Skill> getAllDescendents(Integer skillId);

	SkillsTreeBuilder getSkillTreeBuilder();
}
