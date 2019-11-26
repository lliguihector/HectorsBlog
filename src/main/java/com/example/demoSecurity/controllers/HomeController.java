/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.controllers;

import com.example.demoSecurity.dao.BlogPostDao;
import com.example.demoSecurity.dto.BlogPost;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author hectorlliguichuzca
 */
@Controller
public class HomeController {

    @Autowired
    BlogPostDao blogpostdao;

    @GetMapping({"/", "/home"})
    public String displayHomePage(Model model) {

        int pending = blogpostdao.readPendingBlogPosts().size();
        List<BlogPost> blogPosts = blogpostdao.readApprovedBlogPosts();

        model.addAttribute("pending", pending);
        model.addAttribute("blogs", blogPosts);

        return "home";

    }

//  @GetMapping("allBlogs")
//    public String displayAllBlogPosts(Model model) {
//         int pending = blogpostdao.readPendingBlogPosts().size();
//        List<BlogPost> blogPosts = blogpostdao.readAllBlogPosts();
//           model.addAttribute("pending", pending);
//        model.addAttribute("blogs", blogPosts);
//        
//        return "allBlogs";
//        
//    }
}
