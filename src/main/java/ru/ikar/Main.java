package ru.ikar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Post> upvotesPosts = doRequest().stream()
                .filter(post -> post.getUpvotes() != null)
                .collect(Collectors.toList());
        upvotesPosts.forEach(System.out::println);
    }

    public static List<Post> doRequest() throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        HttpGet request = new HttpGet("https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        CloseableHttpResponse response = httpClient.execute(request);
        List<Post> posts = mapper.readValue(response.getEntity().getContent(), new TypeReference<List<Post>>() {});
        return posts;
    }
}