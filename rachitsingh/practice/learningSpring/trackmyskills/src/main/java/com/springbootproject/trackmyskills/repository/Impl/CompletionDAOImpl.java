package com.springbootproject.trackmyskills.repository.Impl;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.springbootproject.trackmyskills.model.UserSkillCompletion;
import com.springbootproject.trackmyskills.repository.CompletionDAO;

@Repository
public class CompletionDAOImpl implements CompletionDAO {
	private final JdbcTemplate jdbcTemplate;

	public CompletionDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private RowMapper<UserSkillCompletion> userSkillsCompletedMapper = (rs, rowNum) -> new UserSkillCompletion(
			rs.getInt("user_id"), rs.getInt("skill_id"), rs.getTimestamp("timestamp").toLocalDateTime());

	@Override
	public void markSkillCompleted(int userId, int skillId) {
		if (!alreadyMarked(userId, skillId)) {
			String sql = "INSERT INTO user_completed_skills (user_id, skill_id) VALUES (?, ?)";
			jdbcTemplate.update(sql, userId, skillId);
		}
	}

	private boolean alreadyMarked(Integer userId, Integer skillId) {
		String sql = "SELECT COUNT(*) FROM user_completed_skills WHERE user_id=? AND skill_id=?";
		Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId, skillId);
		if (count == 0) {
			return false;
		}
		return true;
	}

	@Override
	public List<Integer> getCompletedSkillIds(Integer userId) {
		String sqlQuery = "SELECT skill_id FROM user_completed_skills WHERE user_id = ?";
		return jdbcTemplate.queryForList(sqlQuery, Integer.class, userId);
	}

	@Override
	public List<UserSkillCompletion> getAllCompletions() {
		String sqlQuery = "SELECT user_id, skill_id, timestamp FROM user_completed_skills";
		return jdbcTemplate.query(sqlQuery, userSkillsCompletedMapper);
	}

	@Override
	public List<UserSkillCompletion> getCompletionsByUser(Integer userId) {
		String sqlQuery = "SELECT user_id, skill_id, timestamp FROM user_completed_skills WHERE user_id = ?";
		return jdbcTemplate.query(sqlQuery, userSkillsCompletedMapper, userId);
	}

	@Override
	public Integer getSkillCompletionCount(Integer skillId) {
		String sqlQuery = "SELECT COUNT(*) FROM user_completed_skills WHERE skill_id = ?";
		return jdbcTemplate.queryForObject(sqlQuery, Integer.class, skillId);
	}
}
