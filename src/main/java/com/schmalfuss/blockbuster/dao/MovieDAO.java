package com.schmalfuss.blockbuster.dao;

import com.google.gson.Gson;
import com.schmalfuss.blockbuster.models.Movie;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MovieDAO {

    private static List<Movie> movies = new ArrayList<>();
    private static int nextId = 1;

    public List<Movie> getAll() {
        return movies;
    }

    public List<Movie> getAllFavorites() {
        return movies.stream().filter(f -> f.getFavorite()).collect(Collectors.toList());
    }

    MovieDAO() {
        decode();
    }

    public void add(Movie movie) {
        movie.setId(nextId++);
        movie.setFavorite(false);
        movie.setLikes(0);
        movie.setDislikes(0);
        movies.add(movie);
        encode();
    }

    public Movie getById(int id) {
        return movies.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    public void update(Movie movie) {
        for (int i = 0; i < movies.size(); i++) {
            Movie n = movies.get(i);
            if (n.getId() == movie.getId()) {
                movies.set(i, movie);
                break;
            }
        }
        encode();
    }

    public void remove(int id) {
        movies.removeIf(p -> p.getId() == id);
        encode();
    }

    public void favorite(int id) {
        Movie movie = getById(id);
        movie.setFavorite(!movie.getFavorite());
        encode();
    }

    public void like(int id) {
        Movie movie = getById(id);
        movie.setLikes(movie.getLikes() + 1);
        encode();
    }

    public void dislike(int id) {
        Movie movie = getById(id);
        movie.setDislikes(movie.getDislikes() + 1);
        encode();
    }

    public static void decode() {
        try {
            movies = new ArrayList<>();
            Path dbPath = new ClassPathResource("db/movies.json").getFile().toPath();
            Gson gson = new Gson();
            String json = String.join(" ", Files.readAllLines(dbPath, StandardCharsets.UTF_8));
            Arrays.asList(gson.fromJson(json, Movie[].class)).stream().forEach(movie -> {
                movie.setId(nextId++);
                movies.add(movie);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void encode() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(movies);

            Path dbPath = new ClassPathResource("db/movies.json").getFile().toPath();
            Files.newBufferedWriter(dbPath);
            Files.writeString(dbPath, json, StandardOpenOption.WRITE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
