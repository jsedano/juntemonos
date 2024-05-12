package dev.jsedano.ai.juntemonos.assistant;

import dev.jsedano.ai.juntemonos.model.Community;
import dev.jsedano.ai.juntemonos.repository.CommunityRepository;
import dev.jsedano.ai.juntemonos.repository.MemberRepository;
import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.service.V;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssistantTools {

  @Autowired private CommunityRepository communityRepository;
  @Autowired private MemberRepository memberRepository;


  @Tool("List available communities, do not mention communities outside this list")
  public List<Community> listCommunities() {
    return communityRepository.findAll();
  }

  @Tool("Make member join a community")
  public boolean joinCommunity(@V("hashedPhoneNumber") String member, @V("community") String communityName) {
    
    Community community = communityRepository.findByName(communityName);
    if(Objects.nonNull(community)){
      if(Objects.isNull(community.getMembers())){
        community.setMembers(new ArrayList<>());
      }
      community.getMembers().add(memberRepository.findByHashedPhoneNumber(member));
      communityRepository.save(community);
      return true;
    }
    return false;
  }


}
