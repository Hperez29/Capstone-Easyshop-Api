package org.yearup.data.mysql;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao
{
    private final JdbcTemplate jdbcTemplate;

    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Category> getAllCategories()
    {
        String sql = "SELECT category_id, name, description FROM categories";
        return jdbcTemplate.query(sql, this::mapRow);
    }

    @Override
    public Category getById(int categoryId)
    {
        String sql = "SELECT category_id, name, description FROM categories WHERE category_id = ?";
        List<Category> results = jdbcTemplate.query(sql, this::mapRow, categoryId);

        if (results.isEmpty())
            return null;

        return results.get(0);
    }

    @Override
    public Category create(Category category)
    {
        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";

        jdbcTemplate.update(sql,
                category.getName(),
                category.getDescription());

        // Get the newly created category (assuming auto-increment id)
        // This is a simple approach; if your DB supports RETURNING, use that instead.
        String sqlLastId = "SELECT LAST_INSERT_ID()";
        Integer id = jdbcTemplate.queryForObject(sqlLastId, Integer.class);
        category.setCategoryId(id);
        return category;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        String sql = "UPDATE categories SET name = ?, description = ? WHERE category_id = ?";
        jdbcTemplate.update(sql,
                category.getName(),
                category.getDescription(),
                categoryId);
    }

    @Override
    public void delete(int categoryId)
    {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        jdbcTemplate.update(sql, categoryId);
    }

    private Category mapRow(ResultSet row, int rowNum) throws SQLException
    {
        Category category = new Category();
        category.setCategoryId(row.getInt("category_id"));
        category.setName(row.getString("name"));
        category.setDescription(row.getString("description"));
        return category;
    }
}