/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.dao;

import com.example.demoSecurity.dto.Category;
import java.util.List;

/**
 *
 * @author hectorlliguichuzca
 */
public interface CategoryDao {

    //CREATE 
    Category createCategory(Category category);

    //READALL
    List<Category> readAllCategories();

    //RADBYID
    Category readCategoryById(int id);

    //UPDATE
    void updateCategory(Category c);
    
    //DELETE 
    void deleteCategoryById(int id);

}
