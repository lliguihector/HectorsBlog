/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.controllers;

import com.example.demoSecurity.dao.BlogPostDao;
import com.example.demoSecurity.dao.CategoryDao;
import com.example.demoSecurity.dao.HashtagDao;
import com.example.demoSecurity.dto.BlogPost;
import com.example.demoSecurity.dto.Category;
import com.example.demoSecurity.dto.Hashtag;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author hectorlliguichuzca
 */
@Controller
public class BlogController {

    @Autowired
    CategoryDao categoriesDao;

    @Autowired
    HashtagDao hashtagdao;

    @Autowired
    BlogPostDao blogpostdao;

    
    //CREATE A NEW BLOG
    @GetMapping("addBlog")
    public String displayBlog(Model model) {

        List<Category> categories = categoriesDao.readAllCategories();
        List<Hashtag> hashtags = hashtagdao.readAllHashtags();
        model.addAttribute("categories", categories);
        model.addAttribute("hashtags", hashtags);

        return "addBlog";

    }

    //DISPLAY ALL BLOGS
    @GetMapping("allBlogs")
    public String displayAllBlogPosts(Model model) {
        int pending = blogpostdao.readPendingBlogPosts().size();
        List<BlogPost> blogPosts = blogpostdao.readAllBlogPosts();
        model.addAttribute("pending", pending);
        model.addAttribute("blogs", blogPosts);

        return "allBlogs";

    }

    //DISPLAY PENDING POSTS ONLY
    @GetMapping("pendingBlogs")
    public String displayAllPendingPosts(Model model) {

        int pending = blogpostdao.readPendingBlogPosts().size();
        List<BlogPost> blogPosts = blogpostdao.readPendingBlogPosts();

        model.addAttribute("blogs", blogPosts);
        model.addAttribute("pending", pending);
        return "pendingBlogs";

    }

    //GET BLOG DETAILS
    @GetMapping("blogpostDetail")
    public String blogPostDetail(Integer id, Model model) {

        BlogPost blog = blogpostdao.readBlogPostById(id);

//        String d = Integer.toString(id);
        
        model.addAttribute("blog", blog);

        return "blogpostDetail";
    }

    //DELETE A BLOG
    @GetMapping("deleteBlogPost")
    public String deleteCharacter(Integer id) {

        blogpostdao.deleteBlogPostById(id);
        return "redirect:/allBlogs";
    }

//PUBLISH A BLOG
    @PostMapping("/publish")
    public String publishBlog(Integer id) {
        blogpostdao.updatePendingBlogPostById(id);

        return "redirect:/allBlogs";
    }

    //UNPUBLISH A POST
    @PostMapping("/unpublish")
    public String unpublishBlog(Integer id) {
        blogpostdao.unUpdatePendingBlogPostById(id);
        return "redirect:/allBlogs";
    }

    @PostMapping("/publishFromPending")
    public String publishBlogFromPending(Integer id) {
        blogpostdao.updatePendingBlogPostById(id);

        return "redirect:/pendingBlogs";
    }

    //CREATE 
    @PostMapping("addBlogPost")
    public String addBlog(BlogPost blog, HttpServletRequest request) {

        String[] categoryIds = request.getParameterValues("categoryId");

        String[] hashtagIds = request.getParameterValues("hashtagId");

        List<Category> categories = new ArrayList<>();
        List<Hashtag> hashtags = new ArrayList<>();

        for (String categoryId : categoryIds) {

            categories.add(categoriesDao.readCategoryById(Integer.parseInt(categoryId)));

        }

        for (String hashtagId : hashtagIds) {

            hashtags.add(hashtagdao.readHashtagById(Integer.parseInt(hashtagId)));

        }

        blog.setCategories(categories);
        blog.setHashtags(hashtags);
        blog.setPublished(false);

        LocalDateTime a = LocalDateTime.now();

        blog.setPublishedDate(a.toString());
        blog.setExperationDate(a.toString());

        blogpostdao.createBlogPost(blog);

        return "redirect:/addBlog";
    }

    //EDIT
    @GetMapping("editBlog")
    public String editBlogPost(Integer id, Model model) {

        BlogPost blogpost = blogpostdao.readBlogPostById(id);

        List<Category> categories = categoriesDao.readAllCategories();
        List<Hashtag> hashtags = hashtagdao.readAllHashtags();
        model.addAttribute("categories", categories);
        model.addAttribute("hashtags", hashtags);
        model.addAttribute("blog", blogpost);

        return "editBlog";

    }

    @PostMapping("editBlog")
    public String performEditBlog(BlogPost blog, HttpServletRequest request) {
        String[] categoryIds = request.getParameterValues("categoryId");

        String[] hashtagIds = request.getParameterValues("hashtagId");

        List<Category> categories = new ArrayList<>();
        List<Hashtag> hashtags = new ArrayList<>();

        for (String categoryId : categoryIds) {

            categories.add(categoriesDao.readCategoryById(Integer.parseInt(categoryId)));

        }

        for (String hashtagId : hashtagIds) {

            hashtags.add(hashtagdao.readHashtagById(Integer.parseInt(hashtagId)));

        }

        blog.setCategories(categories);
        blog.setHashtags(hashtags);

//        LocalDateTime a = LocalDateTime.now();
//
//        blog.setPublishedDate(a.toString());
//        blog.setExperationDate(a.toString());

        blogpostdao.updateBlogPostById(blog);

        return "redirect:/allBlogs";

    }
    
    
    
     @GetMapping("deleteMyBlog")
    public String deleteMyBlog(Integer id) {

        blogpostdao.deleteBlogPostById(id);
        return "redirect:/content";
    }

    
         //EDIT
    @GetMapping("editBlogFromContent")
    public String editBlogPostFromContent(Integer id, Model model) {

        BlogPost blogpost = blogpostdao.readBlogPostById(id);

        List<Category> categories = categoriesDao.readAllCategories();
        List<Hashtag> hashtags = hashtagdao.readAllHashtags();
        model.addAttribute("categories", categories);
        model.addAttribute("hashtags", hashtags);
        model.addAttribute("blog", blogpost);

        return "editBlogC";

    }

    @PostMapping("editBlogFromContent")
    public String performEditBlogFromContent(BlogPost blog, HttpServletRequest request) {
        String[] categoryIds = request.getParameterValues("categoryId");

        String[] hashtagIds = request.getParameterValues("hashtagId");

        List<Category> categories = new ArrayList<>();
        List<Hashtag> hashtags = new ArrayList<>();

        for (String categoryId : categoryIds) {

            categories.add(categoriesDao.readCategoryById(Integer.parseInt(categoryId)));

        }

        for (String hashtagId : hashtagIds) {

            hashtags.add(hashtagdao.readHashtagById(Integer.parseInt(hashtagId)));

        }

        blog.setCategories(categories);
        blog.setHashtags(hashtags);

//        LocalDateTime a = LocalDateTime.now();
//
//        blog.setPublishedDate(a.toString());
//        blog.setExperationDate(a.toString());

        blogpostdao.updateBlogPostById(blog);

        return "redirect:/content";

    }
}
