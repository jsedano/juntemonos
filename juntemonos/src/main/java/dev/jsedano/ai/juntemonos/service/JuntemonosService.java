package dev.jsedano.ai.juntemonos.service;

import dev.jsedano.ai.juntemonos.dto.CommunityDTO;
import dev.jsedano.ai.juntemonos.dto.MemberIdDTO;
import dev.jsedano.ai.juntemonos.entity.CommunityEntity;
import dev.jsedano.ai.juntemonos.entity.MemberEntity;
import dev.jsedano.ai.juntemonos.entity.TechnologyEntity;
import dev.jsedano.ai.juntemonos.repository.CommunityRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JuntemonosService {

  private final CommunityRepository communityRepository;

  public List<CommunityDTO> getCommunities() {
    return communityRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
  }

  public Optional<CommunityDTO> showCommunity(String communityName) {
    return Optional.ofNullable(communityRepository.findByName(communityName)).map(this::mapToDTO);
  }

  private CommunityDTO mapToDTO(CommunityEntity community) {
    return CommunityDTO.builder()
        .id(community.getId())
        .name(community.getName())
        .description(community.getDescription())
        .technologies(
            community.getTechnologies().stream()
                .map(TechnologyEntity::getName)
                .collect(Collectors.toList()))
        .members(community.getMembers().stream().map(this::mapToIdDTO).collect(Collectors.toList()))
        .build();
  }

  private MemberIdDTO mapToIdDTO(MemberEntity memberEntity) {
    return MemberIdDTO.builder()
        .id(memberEntity.getId())
        .hashedPhoneNumber(memberEntity.getHashedPhoneNumber())
        .nickname(memberEntity.getNickname())
        .build();
  }
}
