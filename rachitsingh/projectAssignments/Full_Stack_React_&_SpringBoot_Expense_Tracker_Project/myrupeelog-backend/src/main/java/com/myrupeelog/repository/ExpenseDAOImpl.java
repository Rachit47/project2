package com.myrupeelog.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.myrupeelog.model.Expense;

@Repository
public class ExpenseDAOImpl implements ExpenseDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void insertRecord(Expense expense) {
		String sql = "INSERT INTO expenses (title, amount, category, date) VALUES (?, ?, ?, ?)";
		jdbcTemplate.update(sql, expense.getTitle(), expense.getAmount(), expense.getCategory(), expense.getDate());
	}

	@Override
	public List<Expense> fetchAllExpenses() {
		String sql = "SELECT id, title, amount, category, date FROM expenses";
		return jdbcTemplate.query(sql, new ExpenseRowMapper());
	}

	@Override
	public Expense findById(Integer id) {
		String sql = "SELECT id, title, amount, category, date FROM expenses WHERE id = ?";
		return jdbcTemplate.queryForObject(sql, new ExpenseRowMapper(), id);
	}

	@Override
	public void deleteRecord(Integer id) {
		String sql = "DELETE FROM expenses WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	private static class ExpenseRowMapper implements RowMapper<Expense> {
		@Override
		public Expense mapRow(ResultSet rs, int rowNum) throws SQLException {
			Expense e = new Expense();
			e.setId(rs.getInt("id"));
			e.setTitle(rs.getString("title"));
			e.setAmount(rs.getDouble("amount"));
			e.setCategory(rs.getString("category"));
			e.setDate(rs.getDate("date").toLocalDate());
			return e;
		}
	}
}
