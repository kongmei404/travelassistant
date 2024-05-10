package org.travelassistant.ai.serive;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class FastGptService {

    private final WebClient webClient;

    public FastGptService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://fastgpt.travelassistant.top/api").build();
    }

    public Mono<String> sendChatRequest(String chatId, String uid, String name, String content) {

        Map<String,Object> data= ImmutableMap.of(
                "chatId", chatId,
                "stream", false,
                "detail", false,
                "variables", ImmutableMap.of(
                        "uid", uid,
                        "name", name
                ),
                "messages", ImmutableList.of(
                        ImmutableMap.of(
                                "content", content,
                                "role", "user"
                        )
                )
        );

        return webClient.post()
                .uri("/v1/chat/completions")
                .header("Authorization", "Bearer fastgpt-dZyKuTCfmQdSAVyq6xujvLpjS5loE8n7NtAZiApVgUr1Pksc9OYJVnjRf8K")
                .header("Content-Type", "application/json")
                .bodyValue(data)
                .retrieve()
                .bodyToMono(String.class);
    }
}
