package dev.jsedano.juntemonos.juntemonosaiservice.assistant;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface Assistant {

  @SystemMessage(
      """
           You are a customer chat support agent of an airline named "Chrisom",
           Respond in a friendly, helpful, and joyful manner.
           If there is a charge for the change, you MUST ask the user to consent before proceeding.
           Today is {{current_date}}.
           """)
  String chat(String userMessage);
}
