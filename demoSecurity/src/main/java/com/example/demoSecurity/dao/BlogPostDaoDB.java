/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.dao;

import com.example.demoSecurity.dto.BlogPost;
import com.example.demoSecurity.dto.Category;
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
public class BlogPostDaoDB implements BlogPostDao {

    @Autowired
    JdbcTemplate jdbc;

    //CREATE 
    private final String INSERT_BLOG = "INSERT INTO  blog(title, blogDescription, content, published, userid, publishedDate, experationDate)VALUES(?,?,?,?,?,?,?)";
    private final String INSERT_BLOG_CATEGORIES = "INSERT INTO blogcategories(blogID,categoryID)VALUES(?,?)";
    private final String INSERT_BLOG_HASHTAGS = "INSERT INTO bloghashtags(blogID, hashtagID)VALUES(?,?)";

    //READ
    private final String SELECT_ALL_BLOGPOSTS = "SELECT * FROM blog";
    private final String SELECT_CATEGORIES_FOR_BLOGPOST = "SELECT c.* FROM categories c\n"
            + "JOIN blogcategories bc ON c.categoryID = bc.categoryID\n"
            + "WHERE bc.blogID = ?";
    private final String SELECT_HASHTAGS_FOR_BLOGPOST = "SELECT h.* FROM hastags h\n"
            + "JOIN bloghashtags bh ON h.hashtagID = bh.hashtagID \n"
            + "WHERE bh.blogID = ?";
    private final String SELECT_BLOGPOST_BY_ID = "SELECT * FROM blog WHERE blogID = ?";
    private final String SELECT_ALL_BLOGS_BY_USERNAME = "SELECT * FROM blog WHERE userID = ?";
    private final String SELECT_PENDING = "SELECT * FROM blog WHERE published = FALSE";
    private final String SELECT_PUBLISHED = "SELECT * FROM blog WHERE published = TRUE";
    private final String UPDATE_PENDING = "UPDATE blog SET published = TRUE WHERE blogID = ?";
    private final String UPDATE_PENDING_UNDO = "UPDATE blog SET published = FALSE WHERE blogID = ?";
    //DELETE
    private final String DELETE_BLOG = "DELETE FROM blog WHERE blogID = ?";
    private final String DELETE_CATEGORY_BLOG = "DELETE FROM blogcategories WHERE blogID = ?";
    private String DELETE_HASHTAG_BLOG = "DELETE FROM bloghashtags WHERE blogID = ?";

    private final String UPDATE_BLOGPOST = "UPDATE blog SET title = ?, blogDescription = ?, content = ?, published = ? , userid = ?, publishedDate= ?, experationDate = ? WHERE blogID = ?";

    @Override
    @Transactional
    public BlogPost createBlogPost(BlogPost blog) {

        jdbc.update(INSERT_BLOG, blog.getTitle(), blog.getDescription(), blog.getContent(), blog.isPublished(), blog.getUserId(), blog.getPublishedDate(), blog.getExperationDate());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        blog.setId(newId);

        insertBlogCategories(blog);
        insertBlogHashTags(blog);
        
        return blog;
    }

    public void insertBlogCategories(BlogPost blog) {

        for (Category category : blog.getCategories()) {

            jdbc.update(INSERT_BLOG_CATEGORIES, blog.getId(), category.getId());

        }

    }

    public void insertBlogHashTags(BlogPost blog) {

        for (Hashtag hashtag : blog.getHashtags()) {

            jdbc.update(INSERT_BLOG_HASHTAGS, blog.getId(), hashtag.getId());

        }
    }

    @Override
    public List<BlogPost> readAllBlogPosts() {
        List<BlogPost> posts = jdbc.query(SELECT_ALL_BLOGPOSTS, new BlogPostMapper());

        associateCategoriesAndHashtags(posts);

        return posts;
    }

    public void associateCategoriesAndHashtags(List<BlogPost> blogposts) {

        for (BlogPost posts : blogposts) {

            posts.setCategories(getCategoriesForBlogPost(posts.getId()));
            posts.setHashtags(getHashtagsForBlogPost(posts.getId()));

        }

    }

    private List<Category> getCategoriesForBlogPost(int id) {

        return jdbc.query(SELECT_CATEGORIES_FOR_BLOGPOST, new CategoryMapper(), id);
    }

    private List<Hashtag> getHashtagsForBlogPost(int id) {

        return jdbc.query(SELECT_HASHTAGS_FOR_BLOGPOST, new HashtagMapper(), id);
    }

    @Override
    public BlogPost readBlogPostById(int id) {

        try {

            BlogPost blog = jdbc.queryForObject(SELECT_BLOGPOST_BY_ID, new BlogPostMapper(), id);

            blog.setCategories(getCategoriesForBlogPost(id));
            blog.setHashtags(getHashtagsForBlogPost(id));

            return blog;

        } catch (DataAccessException ex) {
            return null;
        }

    }

    @Override
    public void updateBlogPostById(BlogPost blog) {
//    private final String UPDATE_BLOGPOST = "UPDATE blog SET title = ?, blogDescription = ?, content = ?, published = ? , userid = ?, publishedDate= ?, experationDate = '? WHERE blogID = ?";

        jdbc.update(UPDATE_BLOGPOST, blog.getTitle(), blog.getDescription(), blog.getContent(), blog.isPublished(), blog.getUserId(), blog.getPublishedDate(), blog.getExperationDate(), blog.getId());

        jdbc.update(DELETE_CATEGORY_BLOG, blog.getId());
        jdbc.update(DELETE_HASHTAG_BLOG, blog.getId());

        insertBlogCategories(blog);
        insertBlogHashTags(blog);

    }

    @Override
    @Transactional
    public void deleteBlogPostById(int id) {

        jdbc.update(DELETE_CATEGORY_BLOG, id);
        jdbc.update(DELETE_HASHTAG_BLOG, id);
        jdbc.update(DELETE_BLOG, id);

    }

    @Override
    public List<BlogPost> readAllBlogPostsByUserName(String username) {

        List<BlogPost> posts = jdbc.query(SELECT_ALL_BLOGS_BY_USERNAME, new BlogPostMapper(), username);

        associateCategoriesAndHashtags(posts);

        return posts;

    }

    @Override
    public List<BlogPost> readPendingBlogPosts() {

        List<BlogPost> posts = jdbc.query(SELECT_PENDING, new BlogPostMapper());

        associateCategoriesAndHashtags(posts);

        return posts;
    }

    @Override
    public List<BlogPost> readApprovedBlogPosts() {

        List<BlogPost> posts = jdbc.query(SELECT_PUBLISHED, new BlogPostMapper());

        associateCategoriesAndHashtags(posts);

        return posts;

    }

    @Override
    public void updatePendingBlogPostById(int id) {
        jdbc.update(UPDATE_PENDING, id);

    }

    @Override
    public void unUpdatePendingBlogPostById(int id) {
        jdbc.update(UPDATE_PENDING_UNDO, id);

    }

    public static final class CategoryMapper implements org.springframework.jdbc.core.RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {

            Category category = new Category();
            category.setId(rs.getInt("categoryID"));
            category.setName(rs.getString("categoryName"));
            return category;
        }
    }

    public static final class HashtagMapper implements org.springframework.jdbc.core.RowMapper<Hashtag> {

        @Override
        public Hashtag mapRow(ResultSet rs, int i) throws SQLException {

            Hashtag hastag = new Hashtag();
            hastag.setId(rs.getInt("hashtagID"));
            hastag.setName(rs.getString("hashtagName"));

            return hastag;
        }
    }

    //Mapper() 
    public static final class BlogPostMapper implements org.springframework.jdbc.core.RowMapper<BlogPost> {

        @Override
        public BlogPost mapRow(ResultSet rs, int i) throws SQLException {

            BlogPost blog = new BlogPost();

            blog.setId(rs.getInt("blogId"));
            blog.setTitle(rs.getString("title"));
            blog.setDescription(rs.getString("blogDescription"));
            blog.setContent(rs.getString("content"));
            blog.setPublished(rs.getBoolean("published"));
            blog.setUserId(rs.getString("userid"));
            blog.setPublishedDate(rs.getString("publishedDate"));
            blog.setExperationDate(rs.getString("experationDate"));
            return blog;
        }
    }

}
