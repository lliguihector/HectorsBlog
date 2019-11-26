/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.dao;

import com.example.demoSecurity.dto.BlogPost;
import java.util.List;

/**
 *
 * @author hectorlliguichuzca
 */
public interface BlogPostDao {

    //CERATE
    BlogPost createBlogPost(BlogPost blog);

    //READALL
    List<BlogPost> readAllBlogPosts();

    //READBYID
    BlogPost readBlogPostById(int id);

    //UPDATE 
    void updateBlogPostById(BlogPost blog);

    //DELETE
    void deleteBlogPostById(int id);

    List<BlogPost> readAllBlogPostsByUserName(String username);

  

    List<BlogPost> readPendingBlogPosts();
    List<BlogPost> readApprovedBlogPosts();

    void updatePendingBlogPostById(int id);

    void unUpdatePendingBlogPostById(int id);
    //List<Character> getCharacterForSuperPowers(SuperPower superpower);
    //List<Organization> getCharacterForOrganization(Organization organization);
}
