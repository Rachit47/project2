package com.springbootproject.trackmyskills.repository.Impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.springbootproject.trackmyskills.model.Skill;
import com.springbootproject.trackmyskills.repository.SkillDAO;

@Repository
public class SkillDAOImpl implements SkillDAO {
	private final JdbcTemplate jdbcTemplate;

	public SkillDAOImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private RowMapper<Skill> skillRowMapper = (resultSet, rowNum) -> new Skill(
			resultSet.getObject("skill_id") != null ? resultSet.getInt("skill_id") : null, resultSet.getString("skill_name"),
			resultSet.getObject("parent_id") != null ? resultSet.getInt("parent_id") : null,
			resultSet.getString("difficulty"), resultSet.getInt("estimated_time"));

	@Override
	public List<Skill> getAllSkills() {
		String sqlQuery = "SELECT skill_id, skill_name, parent_id, difficulty, estimated_time FROM skills";
		return jdbcTemplate.query(sqlQuery, skillRowMapper);
	}

	@Override
	public Skill getSkillById(Integer id) {
		String sqlQuery = "SELECT skill_id, skill_name, parent_id, difficulty, estimated_time FROM skills WHERE skill_id = ?";
		return jdbcTemplate.queryForObject(sqlQuery, skillRowMapper, id);
	}
}
