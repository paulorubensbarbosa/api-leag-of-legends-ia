package com.pr.lolia.adapters.out;

import com.pr.lolia.domain.ports.GenerativeAiService;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "OPENAI", matchIfMissing = true)
@FeignClient(name = "OpenAiChatApi", url = "${openai.base-url}") // , configuration = OpenAiChatService.Config.class
public interface OpenAiChatService extends GenerativeAiService {

    @PostMapping("/v1/chat/completions")
    OpenAiChatCompletionResp chatCompletion(OpenAiChatCompletionReq req);

    @Override
    default String generateContent(String objective, String context){
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(
                new Message("system", objective),
                new Message("user", context)
        );
        OpenAiChatCompletionReq req = new OpenAiChatCompletionReq(model, messages);

        OpenAiChatCompletionResp resp = chatCompletion(req);
        return resp.choices().getFirst().message().content();

    }

    record OpenAiChatCompletionReq(String model, List<Message> messages){}
    record Message(String role, String content){}

    record OpenAiChatCompletionResp(List<Choice> choices){}
    record Choice(Message message) {}

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${openai.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.header(
                    HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }

}
