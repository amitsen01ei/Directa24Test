package com.directa24.test.mostactiveauthors.httpclient;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public final class HttpClient {

    private final String url;

    private final Map<String, String> headers;

    private final Map<String, String> requestParams;

    private HttpClient(Builder builder, Map<String, String> headers, Map<String, String> requestParams) {
        this.url = builder.url;
        this.headers = headers;
        this.requestParams = requestParams;
    }

    public Optional<String> get() {
        return this.executeRequest(buildUrlWithRequestParams(this.requestParams));
    }

    public Optional<String> get(Map<String, String> requestParams) {
        return this.executeRequest(buildUrlWithRequestParams(requestParams));
    }

    private Optional<String> executeRequest(String url) {
        try {
            URL articleUserApiUrl = new URL(url);
            HttpsURLConnection connection = (HttpsURLConnection) articleUserApiUrl.openConnection();
            connection.setRequestMethod("GET");

            headers.forEach(connection::setRequestProperty);

            try (InputStream responseStream = connection.getResponseCode() > 299 ? connection.getErrorStream() :
                    connection.getInputStream()) {

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(responseStream))) {

                    String inputLine;
                    StringBuilder content = new StringBuilder();

                    while ((inputLine = reader.readLine()) != null) {
                        content.append(inputLine);
                    }
                    return Optional.of(content.toString());
                }
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String buildUrlWithRequestParams(Map<String, String> requestParams) {

        if (requestParams.isEmpty()) {
            return this.url;
        }

        StringBuilder urlWithRequestParams = new StringBuilder(this.url);

        if (!this.url.endsWith("?")) {
            urlWithRequestParams.append("?");
        }

        return urlWithRequestParams.append(requestParams.entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + "=" + entry.getValue())
                        .collect(Collectors.joining("&")))
                .toString();
    }

    public static class Builder {

        private String url;

        private Map<String, String> headers;

        private Map<String, String> requestParams;

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder requestParams(Map<String, String> requestParams) {
            this.requestParams = requestParams;
            return this;
        }

        public HttpClient build() {
            HttpClient client = new HttpClient(this, headers, requestParams);
            try {
                this.validateClient(client);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
            return client;
        }

        private void validateClient(HttpClient client) throws MalformedURLException {
            if (client.url == null || client.url.isEmpty()) {
                throw new MalformedURLException("Invalid URL");
            }
        }
    }
}
