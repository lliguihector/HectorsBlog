/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.dao;

import com.example.demoSecurity.dto.Hashtag;
import java.util.List;

/**
 *
 * @author hectorlliguichuzca
 */
public interface HashtagDao {
    
    //CREATE
    Hashtag createHashTag(Hashtag h);
    
    //READALL
    List<Hashtag> readAllHashtags();
    
    
    //READBYID
    Hashtag readHashtagById(int id);
   
   
    //UPDATE
    void updateHashtagById(Hashtag h);
    
    //DELETE
    void deleteHashTagById(int id);
          
    
    
}
