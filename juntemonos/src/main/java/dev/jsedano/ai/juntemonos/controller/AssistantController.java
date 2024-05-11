package dev.jsedano.ai.juntemonos.controller;

import dev.jsedano.ai.juntemonos.assistant.Assistant;
import dev.jsedano.ai.juntemonos.assistant.AssistantTools;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.spring.AiService;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/** This is an example of using an {@link AiService}, a high-level LangChain4j API. */
@RestController
class AssistantController {

  @Autowired private OpenAiChatModel openAiChatModel;
  @Autowired private AssistantTools assistantTools;

  private HashMap<String, Assistant> assistants = new HashMap<>();

  private AssistantController(OpenAiChatModel openAiChatModel) {
    this.openAiChatModel = openAiChatModel;
  }

  @GetMapping("/assistant")
  public String assistant(
      @RequestParam(value = "user") String user, @RequestParam(value = "message") String message) {
    if (!assistants.containsKey(user)) {
      assistants.put(
          user,
          AiServices.builder(Assistant.class)
              .chatLanguageModel(openAiChatModel)
              .tools(assistantTools)
              .build());
    }
    return assistants.get(user).chat(message);
  }
}
