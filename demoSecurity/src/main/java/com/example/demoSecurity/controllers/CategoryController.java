/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demoSecurity.controllers;

import com.example.demoSecurity.dao.CategoryDao;
import com.example.demoSecurity.dto.Category;
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
public class CategoryController {

    @Autowired
    CategoryDao categorydao;

    //READALL
    @GetMapping("categories")
    public String displayCategories(Model model) {
        List<Category> categories = categorydao.readAllCategories();

        model.addAttribute("categories", categories);

        return "categories";
    }

    //CREATE
    @PostMapping("addCategory")
    public String addCategory(HttpServletRequest request) {

        String name = request.getParameter("name");

        Category c = new Category();

        c.setName(name);

        categorydao.createCategory(c);

        return "redirect:/categories";
    }

    //DELETE
    @GetMapping("deleteCategory")
    public String deleteCategory(Integer id) {

        categorydao.deleteCategoryById(id);
        return "redirect:/categories";
    }

    @GetMapping("editCategory")
    public String editCategory(Integer id, Model model) {

        Category c = categorydao.readCategoryById(id);
        model.addAttribute("category", c);

        return "editCategory";
    }

    //PERFORM UPDATE
    @PostMapping("editCategory")
    public String performEditCategory(Category c) {

        categorydao.updateCategory(c);

        return "redirect:/categories";

    }

}
