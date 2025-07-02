package ru.practicum.shareit.practicum.shareit.request;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class ItemRequestClientConfig {
    @Value("${shareit-server.url}")
    private String serverUrl;

    @Bean
    public RestTemplate itemRequestRestTemplate(RestTemplateBuilder builder) {
        return builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + "/requests"))
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory())
                .build();
    }

    @Bean
    public ItemRequestClient itemRequestClient(RestTemplate itemRequestRestTemplate) {
        return new ItemRequestClient(itemRequestRestTemplate);
    }
}
