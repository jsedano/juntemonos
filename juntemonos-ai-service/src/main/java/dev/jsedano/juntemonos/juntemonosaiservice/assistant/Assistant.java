package dev.jsedano.juntemonos.juntemonosaiservice.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface Assistant {

  @SystemMessage(
      """
           Respond in a friendly, helpful, and joyful manner.
           Always assume you could be talking with children so keep conversation appropriate.
           """)
  String chat(String userMessage);
}
