package dev.jsedano.ai.juntemonos.assistant;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface Assistant {

  @SystemMessage(
      """
           Respond in a friendly, helpful, and joyful manner.
           Keep conversation appropriate and be succinct, concise, brief, short.
           My username is {{name}}
           """)
  String chat(@MemoryId int memoryId, @V("name") String name, @UserMessage String userMessage);
}
