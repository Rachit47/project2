package com.springbootproject.trackmyskills.service.Impl;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springbootproject.trackmyskills.Utilities.SkillPopularityTracker;
import com.springbootproject.trackmyskills.model.Skill;
import com.springbootproject.trackmyskills.repository.CompletionDAO;
import com.springbootproject.trackmyskills.service.RecommendationService;
import com.springbootproject.trackmyskills.service.SkillService;

@Service
public class RecommendationServiceImpl implements RecommendationService {
	private final CompletionDAO completionDAO;
	private final SkillService skillService;

	public RecommendationServiceImpl(CompletionDAO completionDAO, SkillService skillService) {
		this.completionDAO = completionDAO;
		this.skillService = skillService;
	}

	@Override
	public List<Skill> recommendNextSkills(Integer userId, Integer k) {
		List<Skill> allSkills = skillService.getAllSkills();
		Map<Integer, Skill> idToSkill = allSkills.stream().collect(Collectors.toMap(Skill::getId, s -> s));
		Map<Integer, List<Skill>> childrenMap = buildChildrenMap(allSkills);

		List<Integer> completedSkillIds = completionDAO.getCompletedSkillIds(userId);
		Set<Integer> completedSet = new HashSet<>(completedSkillIds);
		SkillPopularityTracker tracker = new SkillPopularityTracker(completionDAO.getAllCompletions());

		List<Skill> recommendations = new ArrayList<>();

		if (completedSkillIds.isEmpty()) {
			// Recommend root skills
			return allSkills.stream().filter(s -> s.getParentId() == null)
					.sorted((a, b) -> tracker.getFrequency(b.getId()) - tracker.getFrequency(a.getId())).limit(k)
					.collect(Collectors.toList());
		}

		// Start from the last completed skill and walk DFS to find next unvisited
		// children
		Integer lastSkillId = completedSkillIds.get(completedSkillIds.size() - 1);
		Deque<Integer> stack = new ArrayDeque<>();
		stack.push(lastSkillId);

		while (!stack.isEmpty()) {
			int currentId = stack.pop();
			List<Skill> children = childrenMap.getOrDefault(currentId, Collections.emptyList());

			// Recommend unvisited children of this node
			List<Skill> unvisited = children.stream().filter(child -> !completedSet.contains(child.getId()))
					.sorted((a, b) -> tracker.getFrequency(b.getId()) - tracker.getFrequency(a.getId()))
					.collect(Collectors.toList());

			if (!unvisited.isEmpty()) {
				return unvisited.stream().limit(k).collect(Collectors.toList());
			}

			// Push children to stack for deeper traversal
			for (Skill child : children) {
				stack.push(child.getId());
			}
		}

		// Fallback: try siblings of last completed
		Skill lastSkill = idToSkill.get(lastSkillId);
		if (lastSkill != null && lastSkill.getParentId() != null) {
			List<Skill> siblings = childrenMap.getOrDefault(lastSkill.getParentId(), Collections.emptyList()).stream()
					.filter(s -> !completedSet.contains(s.getId()))
					.sorted((a, b) -> tracker.getFrequency(b.getId()) - tracker.getFrequency(a.getId())).limit(k)
					.collect(Collectors.toList());

			if (!siblings.isEmpty())
				return siblings;
		}

		// Fallback: suggest unvisited root-level skills
		return allSkills.stream().filter(s -> s.getParentId() == null && !completedSet.contains(s.getId()))
				.sorted((a, b) -> tracker.getFrequency(b.getId()) - tracker.getFrequency(a.getId())).limit(k)
				.collect(Collectors.toList());
	}

	private Map<Integer, List<Skill>> buildChildrenMap(List<Skill> allSkills) {
		Map<Integer, List<Skill>> map = new HashMap<>();
		for (Skill skill : allSkills) {
			Integer parentId = skill.getParentId();
			if (parentId != null) {
				map.computeIfAbsent(parentId, k -> new ArrayList<>()).add(skill);
			}
		}
		return map;
	}
}
