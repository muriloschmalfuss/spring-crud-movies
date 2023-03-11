package com.schmalfuss.blockbuster.dao;

import com.google.gson.Gson;
import com.schmalfuss.blockbuster.models.Actor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class ActorDAO {
    private static List<Actor> actors = new ArrayList<>();
    private static int nextId = 1;

    public List<Actor> getAll() {
        return actors;
    }

    ActorDAO() {
        decode();
    }

    public void add(Actor actor) {
        actor.setId(nextId++);
        actors.add(actor);
        encode();
    }

    public Actor getById(int id) {
        return actors.stream().filter(a -> a.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void update(Actor actor) {
        for (int i = 0; i < actors.size(); i++) {
            Actor n = actors.get(i);
            if (n.getId() == actor.getId()) {
                actors.set(i, actor);
                break;
            }
        }
        encode();
    }

    public void remove(int id) {
        actors.removeIf(p -> p.getId() == id);
        encode();
    }

    public static void decode() {
        try {
            Path dbPath = new ClassPathResource("db/actors.json").getFile().toPath();
            Gson gson = new Gson();
            String json = String.join(" ", Files.readAllLines(dbPath, StandardCharsets.UTF_8));
            Arrays.asList(gson.fromJson(json, Actor[].class)).stream().forEach(actor -> {
                actor.setId(nextId++);
                actors.add(actor);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void encode() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(actors);

            Path dbPath = new ClassPathResource("db/actors.json").getFile().toPath();
            Files.newBufferedWriter(dbPath);
            Files.writeString(dbPath, json, StandardOpenOption.WRITE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
