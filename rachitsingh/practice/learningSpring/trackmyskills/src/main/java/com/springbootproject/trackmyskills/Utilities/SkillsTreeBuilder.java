package com.springbootproject.trackmyskills.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springbootproject.trackmyskills.model.Skill;

public class SkillsTreeBuilder {
	private Map<Integer, List<Skill>> parentToChildrenSkillsMap = new HashMap<>();
	private Map<Integer, Skill> skillMap = new HashMap<>();

	public SkillsTreeBuilder(List<Skill> skills) {
		for (Skill skill : skills) {
			skillMap.put(skill.getId(), skill);
			Integer parentId = skill.getParentId();

			parentToChildrenSkillsMap.computeIfAbsent(parentId, k -> new ArrayList<>()).add(skill);
		}
	}

	public List<Skill> getRootSkills() {
		return parentToChildrenSkillsMap.get(null); // root skills here mean skill node with no parent node.
	}

	public List<Skill> getChildrenSkills(int skillId) {
		return parentToChildrenSkillsMap.getOrDefault(skillId, new ArrayList<>());
	}

	public Skill getSkillById(int id) {
		return skillMap.get(id);
	}

	public Map<Integer, List<Skill>> getParentToChildrenSkillsMap() {
		return parentToChildrenSkillsMap;
	}

	public Map<Integer, Skill> getSkillMap() {
		return skillMap;
	}

	public List<Skill> getAllDescendantsOfSkill(Integer skillId) {
		List<Skill> descendantSkills = new ArrayList<>();
		depthFirstSearch(skillId, descendantSkills);
		return descendantSkills;
	}

	public void depthFirstSearch(Integer skillId, List<Skill> descendantSkills) {
		List<Skill> childrenSkills = parentToChildrenSkillsMap.getOrDefault(skillId, new ArrayList<>());

		for (Skill childSkill : childrenSkills) {
			descendantSkills.add(childSkill);
			depthFirstSearch(childSkill.getId(), descendantSkills);
		}
	}
}
