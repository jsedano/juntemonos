package dev.jsedano.ai.juntemonos.assistant;

import dev.jsedano.ai.juntemonos.dto.CommunityDTO;
import dev.jsedano.ai.juntemonos.dto.MemberIdDTO;
import dev.jsedano.ai.juntemonos.entity.CommunityEntity;
import dev.jsedano.ai.juntemonos.repository.CommunityRepository;
import dev.jsedano.ai.juntemonos.repository.MemberRepository;
import dev.jsedano.ai.juntemonos.repository.TechnologyRepository;
import dev.jsedano.ai.juntemonos.service.JuntemonosService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.V;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssistantTools {

  private final CommunityRepository communityRepository;
  private final MemberRepository memberRepository;
  private final TechnologyRepository technologyRepository;
  private final JuntemonosService juntemonosService;

  @Tool("Enroll a new member or update nickname if already enrolled")
  public boolean saveOrUpdateMember(
      @V("hashedPhoneNumber") String hashedPhoneNumber, @P("nickname") String nickname) {
    MemberIdDTO memberId =
        MemberIdDTO.builder().hashedPhoneNumber(hashedPhoneNumber).nickname(nickname).build();
    if (juntemonosService.saveMember(memberId)) {
      return true;
    }
    return juntemonosService.updateMember(memberId);
  }

  @Tool("Get member if enrolled")
  public Optional<MemberIdDTO> getNickname(@V("hashedPhoneNumber") String hashedPhoneNumber) {
    return juntemonosService.getMember(hashedPhoneNumber);
  }

  @Tool("List available communities, do not mention communities outside this list")
  public List<CommunityDTO> listCommunities() {

    return juntemonosService.getCommunities();
  }

  @Tool("Get the description of a community")
  public String getCommunityDescription(@P("name of the community") String communityName) {
    return communityRepository.findByName(communityName).getDescription();
  }

  @Tool("Join a community")
  public String joinCommunity(
      @V("hashedPhoneNumber") String hashedPhoneNumber,
      @P("name of the community") String communityName) {

    if (juntemonosService.getMember(hashedPhoneNumber).isEmpty()) {
      return "Member not found, join system first by choosing your nickname";
    }

    CommunityEntity community = communityRepository.findByName(communityName);
    if (Objects.nonNull(community)) {
      if (Objects.isNull(community.getMembers())) {
        community.setMembers(new ArrayList<>());
      }
      community.getMembers().add(memberRepository.findByHashedPhoneNumber(hashedPhoneNumber));
      communityRepository.save(community);
      return "You have joined the community";
    }
    return "Community not found";
  }

  @Tool("List communities by technology")
  public List<CommunityDTO> listCommunitiesByTechnology(@V("technology") String technology) {
    return juntemonosService.getCommunitiesByTechnologies(technology.toLowerCase());
  }
}
