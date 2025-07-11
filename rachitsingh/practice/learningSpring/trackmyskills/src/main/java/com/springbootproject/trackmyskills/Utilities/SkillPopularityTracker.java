package com.springbootproject.trackmyskills.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.springbootproject.trackmyskills.model.UserSkillCompletion;

public class SkillPopularityTracker {
	private final Map<Integer, Integer> skillFrequencyMap = new HashMap<>();

	public SkillPopularityTracker(List<UserSkillCompletion> completions) {
		for (UserSkillCompletion completion : completions) {
			Integer skillId = completion.getSkillId();
			Integer currentFrequencyOfSkill = skillFrequencyMap.getOrDefault(skillId, 0);
			skillFrequencyMap.put(skillId, currentFrequencyOfSkill + 1);
		}
	}

	public Integer getFrequency(int skillId) {
		return skillFrequencyMap.getOrDefault(skillId, 0);
	}

	public Map<Integer, Integer> getAllSkillFrequencies() {
		return skillFrequencyMap;
	}

	public List<Map.Entry<Integer, Integer>> getTopKSkills(Integer k) {
		return skillFrequencyMap.entrySet().stream().sorted((x, y) -> y.getValue() - x.getValue()).limit(k).toList();
	}

}
