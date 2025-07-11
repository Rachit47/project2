package com.springbootproject.trackmyskills.repository;

import java.util.List;

import com.springbootproject.trackmyskills.model.UserSkillCompletion;

public interface CompletionDAO {
	void markSkillCompleted(int userId, int skillId);

	List<Integer> getCompletedSkillIds(Integer userId);

	List<UserSkillCompletion> getAllCompletions();

	List<UserSkillCompletion> getCompletionsByUser(Integer userId);

	Integer getSkillCompletionCount(Integer skillId);
}
