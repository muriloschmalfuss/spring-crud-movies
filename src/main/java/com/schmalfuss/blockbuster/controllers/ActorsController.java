package com.schmalfuss.blockbuster.controllers;

import com.schmalfuss.blockbuster.dao.ActorDAO;
import com.schmalfuss.blockbuster.models.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/actors")
public class ActorsController {

    @Autowired
    private ActorDAO actorDAO;

    @RequestMapping
    public String list(Model model) {
        model.addAttribute("actors", actorDAO.getAll());
        return "actors/list";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("actor", new Actor());
        return "actors/add";
    }

    @PostMapping("/create")
    public String create(Actor actor) {
        actorDAO.add(actor);
        return "redirect:/actors";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable int id, Model model) {
        Actor actor = actorDAO.getById(id);
        model.addAttribute("actor", actor);
        return "actors/edit";
    }

    @PostMapping("/edit")
    public String atualizar(Actor actor) {
        actorDAO.update(actor);
        return "redirect:/actors";
    }

    @GetMapping("/remove/{id}")
    public String remover(@PathVariable int id) {
        actorDAO.remove(id);
        return "redirect:/actors";
    }
}
