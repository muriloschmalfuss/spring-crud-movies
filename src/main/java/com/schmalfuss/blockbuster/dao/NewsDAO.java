package com.schmalfuss.blockbuster.dao;

import com.google.gson.Gson;
import com.schmalfuss.blockbuster.models.News;
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
public class NewsDAO {
    private static List<News> news = new ArrayList<>();
    private static int nextId = 1;

    public List<News> getAll() {
        return news;
    }

    NewsDAO() {
        decode();
    }

    public void add(News newsToAdd) {
        newsToAdd.setId(nextId++);
        news.add(newsToAdd);
        encode();
    }

    public News getById(int id) {
        return news.stream().filter(n -> n.getId() == id).findFirst().orElse(null);
    }

    public void update(News newsToUpdate) {
        for (int i = 0; i < news.size(); i++) {
            News n = news.get(i);
            if (n.getId() == newsToUpdate.getId()) {
                news.set(i, newsToUpdate);
                break;
            }
        }
        encode();
    }

    public void remove(int id) {
        news.removeIf(p -> p.getId() == id);
        encode();
    }

    public static void decode() {
        try {
            Path dbPath = new ClassPathResource("db/news.json").getFile().toPath();
            Gson gson = new Gson();
            String json = String.join(" ", Files.readAllLines(dbPath, StandardCharsets.UTF_8));
            Arrays.asList(gson.fromJson(json, News[].class)).stream().forEach(report -> {
                report.setId(nextId++);
                news.add(report);
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void encode() {
        try {
            Gson gson = new Gson();
            String json = gson.toJson(news);

            Path dbPath = new ClassPathResource("db/news.json").getFile().toPath();
            Files.newBufferedWriter(dbPath);
            Files.writeString(dbPath, json, StandardOpenOption.WRITE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
