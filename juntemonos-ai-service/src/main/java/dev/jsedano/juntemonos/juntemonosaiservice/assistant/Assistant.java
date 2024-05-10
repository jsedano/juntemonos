package dev.jsedano.juntemonos.juntemonosaiservice.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface Assistant {

  @SystemMessage(
      """
           Respond in a friendly, helpful, and joyful manner.
           Keep conversation appropriate and be succinct, concise, brief, short.
           """)
  String chat(String userMessage);
}
