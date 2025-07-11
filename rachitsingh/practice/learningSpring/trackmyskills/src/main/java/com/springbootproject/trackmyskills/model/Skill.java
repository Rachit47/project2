package com.springbootproject.trackmyskills.model;

public class Skill {
	private Integer id;
	private String skillName;
	private Integer parentId;
	private String difficulty;
	private Integer estimatedTime;
	
	public Skill() {}
	
	 public Skill(Integer id, String skillName, Integer parentId, String difficulty, Integer estimatedTime) {
	        this.id = id;
	        this.skillName = skillName;
	        this.parentId = parentId;
	        this.difficulty = difficulty;
	        this.estimatedTime = estimatedTime;
	    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public Integer getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(Integer estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	
}
