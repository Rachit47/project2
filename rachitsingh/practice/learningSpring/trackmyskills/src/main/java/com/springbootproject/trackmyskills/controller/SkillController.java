package com.springbootproject.trackmyskills.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootproject.trackmyskills.model.Skill;
import com.springbootproject.trackmyskills.service.RecommendationService;
import com.springbootproject.trackmyskills.service.SkillService;

@Controller
@RequestMapping("/skills")
public class SkillController {
	private final SkillService skillService;
	private final RecommendationService recommendationService;

	@Autowired
	public SkillController(SkillService skillService, RecommendationService recommendationService) {
		this.skillService = skillService;
		this.recommendationService = recommendationService;
	}

	@GetMapping
	public String getAllSkills(Model model) {
		List<Skill> skills = skillService.getAllSkills();
		model.addAttribute("skills", skills);
		return "all-skills";
	}

	@GetMapping("/root")
	public String getRootSkills(Model model) {
		List<Skill> rootSkills = skillService.getRootSkills();
		model.addAttribute(rootSkills);
		return "root-skills";
	}

	@GetMapping("/{skillId}/children")
	public String getChildren(@PathVariable Integer skillId, Model model) {
		List<Skill> children = skillService.getChildrenSkills(skillId);
		Skill parentSkill = skillService.getSkillById(skillId);
		model.addAttribute("parent", parentSkill);
		model.addAttribute("skills", children);
		return "children-skills";
	}

	@GetMapping("/{skillId}/descendants")
	public String getAllDescendants(@PathVariable Integer skillId, Model model) {
		List<Skill> descendants = skillService.getAllDescendents(skillId);
		Skill parentSkill = skillService.getSkillById(skillId);
		model.addAttribute("parent", parentSkill);
		model.addAttribute("descendants", descendants);
		return "descendant-skills";
	}

	@GetMapping("/recommendations")
	public String getRecommendations(@RequestParam Integer userId, @RequestParam(defaultValue = "5") Integer k,
			Model model) {
		List<Skill> recommendations = recommendationService.recommendNextSkills(userId, k);
		model.addAttribute("userId", userId);
		model.addAttribute("recommendations", recommendations);
		return "recommendations";
	}
}
