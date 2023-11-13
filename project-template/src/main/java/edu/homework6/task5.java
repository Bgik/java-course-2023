package edu.homework6;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class task5 {

    public static long[] hackerNewsTopStories() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://hacker-news.firebaseio.com/v0/topstories.json"))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String[] ids = response.body().replaceAll("[\\[\\]]", "").split(",");
            long[] newsIds = new long[ids.length];
            for (int i = 0; i < ids.length; i++) {
                newsIds[i] = Long.parseLong(ids[i].trim());
            }
            return newsIds;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return new long[0];
        }
    }

    public static String news(long id) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://hacker-news.firebaseio.com/v0/item/" + id + ".json"))
            .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Pattern pattern = Pattern.compile("\"title\"\\s*:\\s*\"([^\"]+)\"");
            Matcher matcher = pattern.matcher(response.body());

            if (matcher.find()) {
                return matcher.group(1);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void main(String[] args) {
        long[] topStories = hackerNewsTopStories();
        System.out.println("Top Hacker News Stories: " + java.util.Arrays.toString(topStories));
        String newsTitle = news(37570037);
        System.out.println("News Title: " + newsTitle);
    }
}
