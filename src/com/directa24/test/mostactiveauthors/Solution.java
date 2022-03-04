package com.directa24.test.mostactiveauthors;

import com.directa24.test.mostactiveauthors.httpclient.HttpClient;
import com.directa24.test.mostactiveauthors.models.User;
import com.directa24.test.mostactiveauthors.way.too.simple.json.parser.JsonArray;
import com.directa24.test.mostactiveauthors.way.too.simple.json.parser.JsonObject;

import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    private static final String URL = "https://jsonmock.hackerrank.com/api/article_users?";

    public List<String> getUsernames (int threshold) {
        int pageStart = 1;
        int totalPage = 0;

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        Map<String, String> requestParams = new HashMap<>();

        HttpClient client = new HttpClient.Builder()
                .url(URL)
                .headers(headers)
                .build();

        List<JsonArray> userJsonInfoList = new ArrayList<>();

        do {
            requestParams.put("page", String.valueOf(pageStart++));

            Optional<String> apiResponse = client.get(requestParams);

            if (apiResponse.isPresent()) {
                String jsonResponse = apiResponse.get();

                JsonObject responseJsonObject = new JsonObject(jsonResponse);
                totalPage = responseJsonObject.getValue("total_pages").map(Integer::parseInt).orElse(totalPage);

                userJsonInfoList.add(responseJsonObject.getJsonArray("data")
                        .orElseGet(() -> new JsonArray("[]")));
            }
        } while (pageStart <= totalPage);

        return userJsonInfoList.stream()
                .flatMap(JsonArray::stream)
                .map(user -> new User(user.getValue("username").orElse(""),
                                Integer.parseInt(user.getValue("submission_count").orElse("0"))
                        )
                )
                .filter(user -> user.getSubmissionCount() > threshold)
                .map(User::getUsername)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        new Solution().getUsernames(10).forEach(System.out::println);
    }
}
