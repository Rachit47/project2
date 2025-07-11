package com.springbootproject.trackmyskills.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springbootproject.trackmyskills.Utilities.SkillPopularityTracker;
import com.springbootproject.trackmyskills.model.Skill;
import com.springbootproject.trackmyskills.model.UserSkillCompletion;
import com.springbootproject.trackmyskills.repository.CompletionDAO;
import com.springbootproject.trackmyskills.service.SkillService;

public class LeaderboardController {
	private final CompletionDAO completionDAO;
	private final SkillService skillService;

	public LeaderboardController(CompletionDAO completionDAO, SkillService skillService) {
		this.completionDAO = completionDAO;
		this.skillService = skillService;
	}

	@GetMapping("/skills")
	public String topSkills(@RequestParam(defaultValue = "5") Integer k, Model model) {
		List<UserSkillCompletion> completions = completionDAO.getAllCompletions();
		SkillPopularityTracker tracker = new SkillPopularityTracker(completions);
		List<Map.Entry<Integer, Integer>> topKSkillFrequencyMap = tracker.getTopKSkills(k);
		Map<Skill, Integer> topSkillData = new LinkedHashMap<>();
		for (Map.Entry<Integer, Integer> entry : topKSkillFrequencyMap) {
			Skill skill = skillService.getSkillById(entry.getKey());
			topSkillData.put(skill, entry.getValue());
		}
		model.addAttribute("topSkills", topSkillData);
		return "leaderboard-skills";
	}
}
