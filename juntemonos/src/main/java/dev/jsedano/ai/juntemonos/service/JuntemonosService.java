package dev.jsedano.ai.juntemonos.service;

import dev.jsedano.ai.juntemonos.dto.CommunityDTO;
import dev.jsedano.ai.juntemonos.dto.MemberIdDTO;
import dev.jsedano.ai.juntemonos.entity.CommunityEntity;
import dev.jsedano.ai.juntemonos.entity.MemberEntity;
import dev.jsedano.ai.juntemonos.entity.TechnologyEntity;
import dev.jsedano.ai.juntemonos.repository.CommunityRepository;
import dev.jsedano.ai.juntemonos.repository.MemberRepository;
import dev.jsedano.ai.juntemonos.repository.TechnologyRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JuntemonosService {

  private final CommunityRepository communityRepository;
  private final MemberRepository memberRepository;
  private final TechnologyRepository technologyRepository;

  public Optional<MemberIdDTO> getMember(String hashedPhoneNumber) {
    MemberEntity member = memberRepository.findByHashedPhoneNumber(hashedPhoneNumber);
    return Optional.ofNullable(
        Objects.nonNull(member)
            ? mapToIdDTO(memberRepository.findByHashedPhoneNumber(hashedPhoneNumber))
            : null);
  }

  public List<CommunityDTO> getCommunitiesByTechnologies(String technology) {
    TechnologyEntity technologyEntity = technologyRepository.findByName(technology);
    if (Objects.isNull(technologyEntity)) {
      return Collections.emptyList();
    }
    return communityRepository.findAllByTechnologies(technologyEntity).stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
  }

  public boolean saveMember(MemberIdDTO memberId) {
    if (getMember(memberId.getHashedPhoneNumber()).isPresent()) {
      return false;
    }
    MemberEntity member = mapToEntity(memberId);
    memberRepository.save(member);
    return true;
  }

  public boolean updateMember(MemberIdDTO memberId) {
    MemberEntity member = memberRepository.findByHashedPhoneNumber(memberId.getHashedPhoneNumber());
    if (Objects.isNull(member)) {
      return false;
    }
    member.setNickname(memberId.getNickname());
    memberRepository.save(member);
    return true;
  }

  public boolean joinCommunity(String hashedPhoneNumber, String communityName) {
    MemberEntity member = memberRepository.findByHashedPhoneNumber(hashedPhoneNumber);
    CommunityEntity community = communityRepository.findByName(communityName);
    if (Objects.isNull(member) || Objects.isNull(community)) {
      return false;
    }
    if (Objects.nonNull(member.getCommunities())
        && member.getCommunities().stream().anyMatch(c -> c.getName().equals(communityName))) {
      return false;
    }
    community.getMembers().add(member);
    communityRepository.save(community);
    return true;
  }

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

  private MemberEntity mapToEntity(MemberIdDTO memberId) {
    return MemberEntity.builder()
        .id(memberId.getId())
        .hashedPhoneNumber(memberId.getHashedPhoneNumber())
        .nickname(memberId.getNickname())
        .build();
  }
}
