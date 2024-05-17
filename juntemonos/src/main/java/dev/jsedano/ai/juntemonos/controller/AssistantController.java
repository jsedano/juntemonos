package dev.jsedano.ai.juntemonos.controller;

import dev.jsedano.ai.juntemonos.assistant.Assistant;
import dev.jsedano.ai.juntemonos.entity.MemberEntity;
import dev.jsedano.ai.juntemonos.repository.MemberRepository;
import dev.langchain4j.service.spring.AiService;
import java.util.HashMap;
import java.util.Objects;
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

  private HashMap<String, Integer> assistants = new HashMap<>();

  private final AtomicInteger assistantCounter = new AtomicInteger(0);

  @Autowired private MemberRepository memberRepository;

  @GetMapping("/assistant")
  public ResponseEntity<String> assistant(
      @RequestParam(value = "user") String hashedPhoneNumber,
      @RequestParam(value = "message") String message) {

    if (Objects.isNull(memberRepository.findByHashedPhoneNumber(hashedPhoneNumber))) {
      memberRepository.save(MemberEntity.builder().hashedPhoneNumber(hashedPhoneNumber).build());
    } // else {
    //   return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
    // .body("Stop spamming the assistant!");
    // }
    if (!assistants.containsKey(hashedPhoneNumber)) {
      assistants.put(hashedPhoneNumber, assistantCounter.getAndIncrement());
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(
            assistant.chat(
                assistants.get(hashedPhoneNumber).intValue(), hashedPhoneNumber, message));
  }
}
