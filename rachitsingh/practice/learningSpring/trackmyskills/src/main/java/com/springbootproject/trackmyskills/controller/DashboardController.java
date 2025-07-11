package com.springbootproject.trackmyskills.controller;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.springbootproject.trackmyskills.Utilities.SkillPopularityTracker;
import com.springbootproject.trackmyskills.Utilities.SkillsTreeBuilder;
import com.springbootproject.trackmyskills.model.Skill;
import com.springbootproject.trackmyskills.model.UserSkillCompletion;
import com.springbootproject.trackmyskills.repository.CompletionDAO;
import com.springbootproject.trackmyskills.service.RecommendationService;
import com.springbootproject.trackmyskills.service.SkillService;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final CompletionDAO completionDAO;
    private final SkillService skillService;
    private final RecommendationService recommendationService;

    public DashboardController(CompletionDAO completionDAO,
                               SkillService skillService,
                               RecommendationService recommendationService) {
        this.completionDAO = completionDAO;
        this.skillService = skillService;
        this.recommendationService = recommendationService;
    }

    @GetMapping("/{userId}")
    public String userDashboard(@PathVariable Integer userId,
                                @RequestParam(defaultValue = "5") Integer k,
                                Model model) {

        // Fetch data
        List<Skill> allSkills = skillService.getAllSkills();
        List<Integer> completedSkillIds = completionDAO.getCompletedSkillIds(userId);
        List<UserSkillCompletion> allCompletions = completionDAO.getAllCompletions();

        // Build maps
        Map<Integer, List<Skill>> childrenMap = buildChildrenMap(allSkills);
        Map<Integer, String> skillIdToNameMap = allSkills.stream()
                .collect(Collectors.toMap(Skill::getId, Skill::getSkillName));

        // Tree builder
        SkillsTreeBuilder treeBuilder = skillService.getSkillTreeBuilder();
        List<Skill> rootSkills = treeBuilder.getRootSkills();

        // Recommendation logic
        SkillPopularityTracker tracker = new SkillPopularityTracker(allCompletions);
        List<Skill> recommendations = computeRecommendations(userId, completedSkillIds, childrenMap, rootSkills, tracker, k);

        // Top learned skills
        Map<Skill, Integer> topSkills = tracker.getTopKSkills(k).stream()
                .collect(Collectors.toMap(
                        entry -> skillService.getSkillById(entry.getKey()),
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        // Model attributes
        model.addAttribute("userId", userId);
        model.addAttribute("userName", "User " + userId);
        model.addAttribute("skills", allSkills);
        model.addAttribute("completedSkillIds", completedSkillIds);
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("rootSkills", rootSkills);
        model.addAttribute("childrenMap", childrenMap);
        model.addAttribute("skillIdToNameMap", skillIdToNameMap);
        model.addAttribute("topSkills", topSkills);

        return "dashboard";
    }

    @PostMapping("/completeSkill")
    public String markSkillCompleted(@RequestParam Integer userId,
                                     @RequestParam Integer skillId) {
        completionDAO.markSkillCompleted(userId, skillId);
        return "redirect:/dashboard/" + userId;
    }

    private List<Skill> computeRecommendations(Integer userId,
                                               List<Integer> completedSkillIds,
                                               Map<Integer, List<Skill>> childrenMap,
                                               List<Skill> rootSkills,
                                               SkillPopularityTracker tracker,
                                               int k) {
        Set<Integer> completed = new HashSet<>(completedSkillIds);
        List<Skill> recommendations = new ArrayList<>();

        if (completedSkillIds.isEmpty()) {
            // First-time user → recommend root skills
            recommendations.addAll(rootSkills);
        } else {
            Integer lastCompletedSkillId = completedSkillIds.get(completedSkillIds.size() - 1);

            // Try children first
            List<Skill> children = childrenMap.getOrDefault(lastCompletedSkillId, Collections.emptyList());
            List<Skill> uncompletedChildren = children.stream()
                    .filter(s -> !completed.contains(s.getId()))
                    .sorted(Comparator.comparingInt((Skill s) -> tracker.getFrequency(s.getId())).reversed())
                    .limit(k)
                    .collect(Collectors.toList());

            if (!uncompletedChildren.isEmpty()) {
                recommendations.addAll(uncompletedChildren);
            } else {
                // Try other unvisited root skills
                List<Skill> alternativeRoots = rootSkills.stream()
                        .filter(root -> !completed.contains(root.getId()))
                        .sorted(Comparator.comparingInt((Skill s) -> tracker.getFrequency(s.getId())).reversed())
                        .collect(Collectors.toList());
                recommendations.addAll(alternativeRoots);
            }
        }

        return recommendations.stream().limit(k).collect(Collectors.toList());
    }

    private Map<Integer, List<Skill>> buildChildrenMap(List<Skill> allSkills) {
        Map<Integer, List<Skill>> map = new HashMap<>();
        for (Skill s : allSkills) {
            if (s.getParentId() != null) {
                map.computeIfAbsent(s.getParentId(), x -> new ArrayList<>()).add(s);
            }
        }
        return map;
    }
}
