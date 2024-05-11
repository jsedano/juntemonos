package dev.jsedano.ai.juntemonos.assistant;

import dev.jsedano.ai.juntemonos.model.Community;
import dev.jsedano.ai.juntemonos.repository.CommunityRepository;
import dev.langchain4j.agent.tool.Tool;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssistantTools {

  @Autowired private CommunityRepository communityRepository;

  @Tool("Lists available communities")
  public List<Community> listCommunities() {
    return communityRepository.findAll();
  }
}
