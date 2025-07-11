package com.springbootproject.trackmyskills.service;

import com.springbootproject.trackmyskills.model.Skill;
import java.util.List;

public interface RecommendationService {

	List<Skill> recommendNextSkills(Integer userId, Integer k);
}