package dev.jsedano.ai.juntemonos.assistant;

import dev.jsedano.ai.juntemonos.model.Community;
import dev.jsedano.ai.juntemonos.model.Technology;
import dev.jsedano.ai.juntemonos.repository.CommunityRepository;
import dev.jsedano.ai.juntemonos.repository.MemberRepository;
import dev.jsedano.ai.juntemonos.repository.TechnologyRepository;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.V;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssistantTools {

  @Autowired private CommunityRepository communityRepository;
  @Autowired private MemberRepository memberRepository;
  @Autowired private TechnologyRepository technologyRepository;

  @Tool("List available communities, do not mention communities outside this list")
  public List<Community> listCommunities() {
    return communityRepository.findAll();
  }

  @Tool("Join a community")
  public boolean joinCommunity(
      @V("hashedPhoneNumber") String member, @P("name of the community") String communityName) {

    Community community = communityRepository.findByName(communityName);
    if (Objects.nonNull(community)) {
      if (Objects.isNull(community.getMembers())) {
        community.setMembers(new HashSet<>());
      }
      community.getMembers().add(memberRepository.findByHashedPhoneNumber(member));
      communityRepository.save(community);
      return true;
    }
    return false;
  }

  @Tool(
      "List available technologies, programming languages, frameworks, do not mention technologies, programming languages, frameworks, outside this list")
  public List<Technology> listTechnologies() {
    return technologyRepository.findAll();
  }

  @Tool(
      "Look up communities by technology, programming language or framework used by the community")
  public List<Community> listCommunitiesByTechnology(
      @P("technology, programming language or framework") String technology) {
    Technology tech = technologyRepository.findByName(technology.toLowerCase());
    if (Objects.nonNull(tech)) {
      return communityRepository.findAllByTechnologies(tech);
    }
    return Collections.emptyList();
  }

  @Tool("Returns which communities the {{hashedPhoneNumber}} is part of")
  public List<Community> listCommunitiesByMember(@V("hashedPhoneNumber") String member) {
    return communityRepository.findAllByMembersHashedPhoneNumber(member);
  }
}
