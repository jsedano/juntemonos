package dev.jsedano.ai.juntemonos.assistant;

import dev.jsedano.ai.juntemonos.dto.CommunityDTO;
import dev.jsedano.ai.juntemonos.dto.MemberIdDTO;
import dev.jsedano.ai.juntemonos.service.JuntemonosService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.V;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssistantTools {

  private final JuntemonosService juntemonosService;

  @Tool("Enroll a new member or update nickname if already enrolled")
  public boolean saveOrUpdateMember(
      @V("hashedPhoneNumber") String hashedPhoneNumber, @P("nickname") String nickname) {

    try {
      MemberIdDTO memberId =
          MemberIdDTO.builder().hashedPhoneNumber(hashedPhoneNumber).nickname(nickname).build();
      if (juntemonosService.saveMember(memberId)) {
        return true;
      }
      return juntemonosService.updateMember(memberId);
    } catch (Exception e) {
      log.error("Error saving or updating memeber", e);
      return false;
    }
  }

  @Tool("Check is member enrolled")
  public boolean isEnrolled(@V("hashedPhoneNumber") String hashedPhoneNumber) {
    try {
      return juntemonosService.getMember(hashedPhoneNumber).isPresent();
    } catch (Exception e) {
      log.error("Error in is member enrolled", e);
      return false;
    }
  }

  @Tool("List available communities, do not mention communities outside this list")
  public List<CommunityDTO> listCommunities() {
    try {
      return juntemonosService.getCommunities();

    } catch (Exception e) {
      log.error("Error in list communities", e);
      return Collections.emptyList();
    }
  }

  @Tool("Get the description of a community")
  public String getCommunityDescription(@P("name of the community") String communityName) {
    try {
      return juntemonosService
          .showCommunity(communityName)
          .map(CommunityDTO::getDescription)
          .orElse("no description found");
    } catch (Exception e) {
      log.error("Error getting community description", e);
      return "Error getting community description";
    }
  }

  @Tool("Join a community")
  public String joinCommunity(
      @V("hashedPhoneNumber") String hashedPhoneNumber,
      @P("name of the community") String communityName) {
    try {

      if (juntemonosService.getMember(hashedPhoneNumber).isEmpty()) {
        return "Member not found, you must join system first by choosing your nickname";
      }

      if (juntemonosService.showCommunity(communityName).isEmpty()) {
        return "Community not found";
      }

      if (juntemonosService.joinCommunity(hashedPhoneNumber, communityName)) {
        return "You have joined the community";
      } else {
        return "You are already a member of this community";
      }
    } catch (Exception e) {
      log.error("Error in join community", e);
      return "Error joining community";
    }
  }

  @Tool("List communities by technology")
  public List<CommunityDTO> listCommunitiesByTechnology(@V("technology") String technology) {
    try {
      return juntemonosService.getCommunitiesByTechnologies(technology.toLowerCase());
    } catch (Exception e) {
      log.error("Error in list communities by technology", e);
      return Collections.emptyList();
    }
  }
}
