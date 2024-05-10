package org.travelassistant.ai.controller;

import com.travelassistant.pojo.User;
import org.springframework.web.bind.annotation.*;
import org.travelassistant.ai.serive.FastGptService;
import org.travelassistant.ai.vo.UserAndContext;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/ai")
public class ChatController {

    private final FastGptService fastGptService;

    public ChatController(FastGptService fastGptService) {
        this.fastGptService = fastGptService;
    }

    @PostMapping("/chat")
    public Mono<String> getChatResponse(@RequestBody UserAndContext userAndContext) {
        String id = String.valueOf(userAndContext.getUser().getUserId());
        String userName = userAndContext.getUser().getUserName();
        String context = userAndContext.getContext();
        if (id == null & userName == null & context == null) {
            return Mono.error(new IllegalArgumentException("Invalid user or context"));
        }else {
            return fastGptService.sendChatRequest(id, id, userName, context);
        }
    }
}
