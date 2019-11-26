/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.dao;

import com.example.demoSecurity.dto.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author hectorlliguichuzca
 */
@Repository
public class CategoryDaoDB implements CategoryDao {

    @Autowired
    JdbcTemplate jdbc;

    private final String INSERT_CATEGORY = "INSERT INTO categories(categoryName)VALUES(?)";
    private final String SELECT_ALL_CATEGORIES = "SELECT * FROM categories ORDER BY categoryName";
    private final String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE categoryID = ?";
    private final String DELETE_CATEGORY_BY_ID = "DELETE FROM categories WHERE categoryID = ?";
    private final String UPDATE_CATEGORY = "UPDATE categories SET categoryName = ? WHERE categoryID = ?";

    private final String DELETE_CATEGORY_BRIDGE = "DELETE FROM blogcategories WHERE categoryID  = ?";

    @Override
    @Transactional
    public Category createCategory(Category category) {

        jdbc.update(INSERT_CATEGORY, category.getName());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        category.setId(newId);

        return category;
    }

    @Override
    public List<Category> readAllCategories() {

        return jdbc.query(SELECT_ALL_CATEGORIES, new CategoryMapper());

    }

    @Override
    public Category readCategoryById(int id) {

        try {

            return jdbc.queryForObject(SELECT_CATEGORY_BY_ID, new CategoryMapper(), id);

        } catch (DataAccessException ex) {

            return null;

        }

    }

    @Override
    @Transactional
    public void deleteCategoryById(int id) {

        jdbc.update(DELETE_CATEGORY_BRIDGE, id);
        jdbc.update(DELETE_CATEGORY_BY_ID, id);

    }

    @Override
    public void updateCategory(Category c) {

        jdbc.update(UPDATE_CATEGORY, c.getName(), c.getId());
    }

    //Mapper()
    public static final class CategoryMapper implements org.springframework.jdbc.core.RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {

            Category category = new Category();
            category.setId(rs.getInt("categoryID"));
            category.setName(rs.getString("categoryName"));
            return category;
        }
    }

}
