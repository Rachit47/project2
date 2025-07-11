package com.springbootproject.trackmyskills.model;

import java.time.LocalDateTime;

public class UserSkillCompletion {
	private int userId;
	private int skillId;
	private LocalDateTime timestamp;

	public UserSkillCompletion() {
	}

	public UserSkillCompletion(int userId, int skillId, LocalDateTime timestamp) {
		this.userId = userId;
		this.skillId = skillId;
		this.timestamp = timestamp;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSkillId() {
		return skillId;
	}

	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
}
