package dev.jsedano.ai.juntemonos.assistant;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class AssistantConfiguration {

  @Bean
  ChatMemory chatMemory() {
    return MessageWindowChatMemory.withMaxMessages(10);
  }

  @Bean
  ChatMemoryProvider chatMemoryProvider() {
    return memoryId -> MessageWindowChatMemory.withMaxMessages(10);
  }
}
