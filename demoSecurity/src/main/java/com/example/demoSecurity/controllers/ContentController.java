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
public class ContentController {
    
    @Autowired
    BlogPostDao blogpostdao;
    
    
    @Autowired
    CategoryDao categoriesDao;
    
    @Autowired
    HashtagDao  hashtagdao;
    
    @GetMapping("/content")
    public String displayContentPage() {
        
        
        return "content";
    }
    
    @GetMapping("/viewBlogsByUsername")
    public String blogPostDetail(String username, Model model) {
        
         List<BlogPost> blogs = blogpostdao.readAllBlogPostsByUserName(username);

        model.addAttribute("blogs", blogs);
        
        return "content";
    }
    
    

    
}
