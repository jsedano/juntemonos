package dev.jsedano.ai.juntemonos.controller;

import dev.jsedano.ai.juntemonos.assistant.Assistant;
import dev.langchain4j.service.spring.AiService;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** This is an example of using an {@link AiService}, a high-level LangChain4j API. */
@RestController
class AssistantController {

  @Autowired private Assistant assistant;

  private HashMap<String, Integer> assistants = new HashMap<>();

  private final AtomicInteger assistantCounter = new AtomicInteger(0);

  @GetMapping("/assistant")
  public String assistant(
      @RequestParam(value = "user") String user, @RequestParam(value = "message") String message) {
    if (!assistants.containsKey(user)) {
      assistants.put(user, assistantCounter.getAndIncrement());
    }
    return assistant.chat(assistants.get(user).intValue(), user, message);
  }
}
