package dev.jsedano.ai.juntemonos.controller;

import dev.jsedano.ai.juntemonos.assistant.Assistant;
import dev.langchain4j.service.spring.AiService;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** This is an example of using an {@link AiService}, a high-level LangChain4j API. */
@RestController
class AssistantController {

  @Autowired private Assistant assistant;

  private HashMap<String, Integer> chatMemoryMap = new HashMap<>();

  private final AtomicInteger assistantCounter = new AtomicInteger(0);

  @GetMapping("/assistant")
  public ResponseEntity<String> assistant(
      @RequestParam(value = "user") String hashedPhoneNumber,
      @RequestParam(value = "message") String message) {

    // else {
    //   return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
    // .body("Stop spamming the assistant!");
    // }
    if (!chatMemoryMap.containsKey(hashedPhoneNumber)) {
      chatMemoryMap.put(hashedPhoneNumber, assistantCounter.getAndIncrement());
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            assistant.chat(
                chatMemoryMap.get(hashedPhoneNumber).intValue(), hashedPhoneNumber, message));
  }
}
