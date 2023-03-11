package com.schmalfuss.blockbuster.controllers;

import com.schmalfuss.blockbuster.dao.ActorDAO;
import com.schmalfuss.blockbuster.dao.MovieDAO;
import com.schmalfuss.blockbuster.models.Actor;
import com.schmalfuss.blockbuster.models.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private ActorDAO actorDAO;

    @RequestMapping
    public String list(Model model) {
        model.addAttribute("movies", movieDAO.getAll());
        return "movies/list";
    }

    @RequestMapping("/favorites")
    public String listFavorites(Model model) {
        model.addAttribute("movies", movieDAO.getAllFavorites());
        return "movies/favorites";
    }

    @RequestMapping("/add")
    public String add(Model model) {
        List<Actor> actors = actorDAO.getAll();
        model.addAttribute("actors_list", actors);
        model.addAttribute("movie", new Movie());
        return "movies/add";
    }

    @PostMapping("/create")
    public String create(Movie movie) {
        movieDAO.add(movie);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable int id, Model model) {
        List<Actor> actors = actorDAO.getAll();
        model.addAttribute("actors_list", actors);
        Movie movie = movieDAO.getById(id);
        model.addAttribute("movie", movie);
        return "movies/edit";
    }

    @PostMapping("/edit")
    public String atualizar(Movie movie) {
        movieDAO.update(movie);
        return "redirect:/movies";
    }

    @GetMapping("/remove/{id}")
    public String remover(@PathVariable int id) {
        movieDAO.remove(id);
        return "redirect:/movies";
    }

    @GetMapping("/favorite/{id}")
    public String favorite(@PathVariable int id, HttpServletRequest request) {
        movieDAO.favorite(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/like/{id}")
    public String like(@PathVariable int id, HttpServletRequest request) {
        movieDAO.like(id);
        return "redirect:" + request.getHeader("Referer");
    }

    @GetMapping("/dislike/{id}")
    public String dislike(@PathVariable int id, HttpServletRequest request) {
        movieDAO.dislike(id);
        return "redirect:" + request.getHeader("Referer");
    }
}
