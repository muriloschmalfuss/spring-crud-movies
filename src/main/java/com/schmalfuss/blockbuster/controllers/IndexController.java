package com.schmalfuss.blockbuster.controllers;

import com.schmalfuss.blockbuster.dao.MovieDAO;
import com.schmalfuss.blockbuster.dao.NewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class IndexController {

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private NewsDAO newsDAO;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("movies", movieDAO.getAll());
        model.addAttribute("news", newsDAO.getAll());
        return "index";
    }

}
