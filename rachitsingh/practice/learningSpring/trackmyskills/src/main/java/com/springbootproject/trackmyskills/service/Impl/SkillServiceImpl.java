package com.springbootproject.trackmyskills.service.Impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.springbootproject.trackmyskills.Utilities.SkillsTreeBuilder;
import com.springbootproject.trackmyskills.model.Skill;
import com.springbootproject.trackmyskills.repository.SkillDAO;
import com.springbootproject.trackmyskills.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService {
	private final SkillDAO skillDAO;
	private SkillsTreeBuilder skillsTreeBuilder;

	public void loadSkillsTree() {
		List<Skill> allSkills = skillDAO.getAllSkills();
		this.skillsTreeBuilder = new SkillsTreeBuilder(allSkills);
	}

	public SkillServiceImpl(SkillDAO skillDAO) {
		this.skillDAO = skillDAO;
		loadSkillsTree();
	}

	@Override
	public List<Skill> getAllSkills() {
		return skillDAO.getAllSkills();
	}

	@Override
	public List<Skill> getRootSkills() {
		return skillsTreeBuilder.getRootSkills();
	}

	@Override
	public List<Skill> getChildrenSkills(Integer id) {
		return skillsTreeBuilder.getChildrenSkills(id);
	}

	@Override
	public Skill getSkillById(Integer id) {
		return skillDAO.getSkillById(id);
	}

	@Override
	public List<Skill> getAllDescendents(Integer skillId) {
		return skillsTreeBuilder.getAllDescendantsOfSkill(skillId);
	}

	@Override
	public SkillsTreeBuilder getSkillTreeBuilder() {
		return skillsTreeBuilder;
	}
}
