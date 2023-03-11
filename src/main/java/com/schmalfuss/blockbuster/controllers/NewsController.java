package com.schmalfuss.blockbuster.controllers;

import com.schmalfuss.blockbuster.dao.NewsDAO;
import com.schmalfuss.blockbuster.models.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsDAO newsDAO;

    @RequestMapping
    public String list(Model model) {
        List<News> news = newsDAO.getAll();
        model.addAttribute("news", news);
        return "news/list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("news", new News());
        return "news/add";
    }

    @PostMapping("/create")
    public String create(News news) {
        newsDAO.add(news);
        return "redirect:/news";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable int id, Model model) {
        News news = newsDAO.getById(id);
        model.addAttribute("news", news);
        return "news/edit";
    }

    @PostMapping("/edit")
    public String atualizar(News news) {
        newsDAO.update(news);
        return "redirect:/news";
    }

    @GetMapping("/remove/{id}")
    public String remover(@PathVariable int id) {
        newsDAO.remove(id);
        return "redirect:/news";
    }

}
