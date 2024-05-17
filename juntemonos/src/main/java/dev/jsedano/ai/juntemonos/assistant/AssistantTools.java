package dev.jsedano.ai.juntemonos.assistant;

import dev.jsedano.ai.juntemonos.entity.CommunityEntity;
import dev.jsedano.ai.juntemonos.entity.MemberEntity;
import dev.jsedano.ai.juntemonos.entity.TechnologyEntity;
import dev.jsedano.ai.juntemonos.repository.CommunityRepository;
import dev.jsedano.ai.juntemonos.repository.MemberRepository;
import dev.jsedano.ai.juntemonos.repository.TechnologyRepository;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.V;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssistantTools {

  @Autowired private CommunityRepository communityRepository;
  @Autowired private MemberRepository memberRepository;
  @Autowired private TechnologyRepository technologyRepository;

  @Tool("Set user nickname")
  public boolean setNickname(
      @V("hashedPhoneNumber") String hashedPhoneNumber, @P("nickname") String nickname) {
    MemberEntity member = memberRepository.findByHashedPhoneNumber(hashedPhoneNumber);
    member.setNickname(nickname);
    memberRepository.save(member);
    return true;
  }

  @Tool("Check if nickname is set")
  public boolean checkIfNicknameIsSet(@V("hashedPhoneNumber") String hashedPhoneNumber) {
    String nickname = memberRepository.findByHashedPhoneNumber(hashedPhoneNumber).getNickname();
    return nickname != null ? nickname.isBlank() : true;
  }

  @Tool("Get user nickname")
  public String getNickname(@V("hashedPhoneNumber") String hashedPhoneNumber) {
    String nickname = memberRepository.findByHashedPhoneNumber(hashedPhoneNumber).getNickname();
    return nickname == null ? "User does not have nickname" : nickname;
  }

  @Tool("List available communities, do not mention communities outside this list")
  public List<String> listCommunities() {
    return communityRepository.findAll().stream().map(CommunityEntity::getName).toList();
  }

  @Tool("Get the description of a community")
  public String getCommunityDescription(@P("name of the community") String communityName) {
    return communityRepository.findByName(communityName).getDescription();
  }

  @Tool("Join a community")
  public boolean joinCommunity(
      @V("hashedPhoneNumber") String hashedPhoneNumber,
      @P("name of the community") String communityName) {

    CommunityEntity community = communityRepository.findByName(communityName);
    if (Objects.nonNull(community)) {
      if (Objects.isNull(community.getMembers())) {
        community.setMembers(new ArrayList<>());
      }
      community.getMembers().add(memberRepository.findByHashedPhoneNumber(hashedPhoneNumber));
      communityRepository.save(community);
      return true;
    }
    return false;
  }

  @Tool(
      "List available technologies, programming languages, frameworks, do not mention technologies, programming languages, frameworks, outside this list")
  public List<String> listTechnologies() {
    return technologyRepository.findAll().stream().map(TechnologyEntity::getName).toList();
  }

  @Tool(
      "Look up communities by technology, programming language or framework used by the community")
  public List<String> listCommunitiesByTechnology(
      @P("technology, programming language or framework") String technology) {
    TechnologyEntity tech = technologyRepository.findByName(technology.toLowerCase());
    if (Objects.nonNull(tech)) {
      return communityRepository.findAllByTechnologies(tech).stream()
          .map(CommunityEntity::getName)
          .toList();
    }
    return Collections.emptyList();
  }

  @Tool("Returns which communities the {{hashedPhoneNumber}} is part of")
  public List<String> listCommunitiesByMember(@V("hashedPhoneNumber") String hashedPhoneNumber) {
    return communityRepository.findAllByMembersHashedPhoneNumber(hashedPhoneNumber).stream()
        .map(CommunityEntity::getName)
        .toList();
  }

  @Tool("List events of a community")
  public List<String> listEvents(@P("name of the community") String communityName) {
    CommunityEntity community = communityRepository.findByName(communityName);
    if (Objects.nonNull(community)) {
      return community.getEvents().stream()
          .map(event -> event.getTitle() + " - " + event.getDateTime())
          .toList();
    }
    return Collections.emptyList();
  }
}
