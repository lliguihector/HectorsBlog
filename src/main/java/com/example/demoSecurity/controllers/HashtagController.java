/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.controllers;

import com.example.demoSecurity.dao.HashtagDao;
import com.example.demoSecurity.dto.Hashtag;
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
public class HashtagController {

    @Autowired
    HashtagDao hashtagdao;

    //READALL
    @GetMapping("hashtags")
    public String displayHashtags(Model model) {

        List<Hashtag> hashtags = hashtagdao.readAllHashtags();

        model.addAttribute("hashtags", hashtags);
        return "hashtags";

    }

    //CREATE
    @PostMapping("addHashtag")
    public String addHashtag(HttpServletRequest request) {

        String name = request.getParameter("name");

        Hashtag h = new Hashtag();

        h.setName(name);

        hashtagdao.createHashTag(h);

        return "redirect:/hashtags";
    }

    //DELETE
    @GetMapping("deleteHashtag")
    public String deleteHashtag(Integer id) {

        hashtagdao.deleteHashTagById(id);

        return "redirect:/hashtags";

    }

    //UPDATE
    @GetMapping("editHashtag")
    public String editHashTag(Integer id, Model model) {

        Hashtag h = hashtagdao.readHashtagById(id);

        model.addAttribute("hashtag", h);

        return "editHashtag";

    }
    
    //PERFORM UPDATE
    @PostMapping("editHashtag")
    public String performEditHashtag(Hashtag h){
        
        hashtagdao.updateHashtagById(h);
        
       
        return "redirect:/hashtags";
        
    }
    
    
    
    

}
