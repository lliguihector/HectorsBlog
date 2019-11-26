/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.dao;

import com.example.demoSecurity.dto.Hashtag;
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
public class HashtagDaoDB implements HashtagDao {

    @Autowired
    JdbcTemplate jdbc;

    private final String INSERT_HASHTAG = "INSERT INTO hastags(hashtagName)VALUES\n"
            + "(?);";
    private final String SELECT_ALL_HASHTAGS = "SELECT * FROM hastags;";
    private final String SELECT_HASHTAG_BY_ID = "SELECT * FROM hastags WHERE hashtagID = ?";
    private final String DELETE_HASHTAG = "DELETE FROM hastags WHERE hashtagID = ?";
    private final String UPDATE_HASHTAG = "UPDATE hastags SET hashtagName = ? WHERE hashtagID = ?";

    private final String DELETE_BRIDGE = "DELETE FROM bloghashtags WHERE hashtagID = ?";

    @Override
    @Transactional
    public Hashtag createHashTag(Hashtag h) {

        jdbc.update(INSERT_HASHTAG, h.getName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        h.setId(newId);

        return h;

    }

    @Override
    public List<Hashtag> readAllHashtags() {

        return jdbc.query(SELECT_ALL_HASHTAGS, new HashtagMapper());

    }

    @Override
    public Hashtag readHashtagById(int id) {

        try {

            return jdbc.queryForObject(SELECT_HASHTAG_BY_ID, new HashtagMapper(), id);

        } catch (DataAccessException ex) {

            return null;
        }

    }

    @Override
    @Transactional
    public void deleteHashTagById(int id) {

        jdbc.update(DELETE_BRIDGE, id);
        jdbc.update(DELETE_HASHTAG, id);

    }

    @Override
    public void updateHashtagById(Hashtag h) {

        jdbc.update(UPDATE_HASHTAG, h.getName(), h.getId());

    }

    //Mapper()
    public static final class HashtagMapper implements org.springframework.jdbc.core.RowMapper<Hashtag> {

        @Override
        public Hashtag mapRow(ResultSet rs, int i) throws SQLException {

            Hashtag hashtag = new Hashtag();

            hashtag.setId(rs.getInt("hashtagID"));
            hashtag.setName(rs.getString("hashtagName"));

            return hashtag;
        }
    }

}
