package dev.jsedano.ai.juntemonos.repository;

import dev.jsedano.ai.juntemonos.entity.CommunityEntity;
import dev.jsedano.ai.juntemonos.entity.TechnologyEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Long> {

  public CommunityEntity findByName(String name);

  public List<CommunityEntity> findAllByTechnologies(TechnologyEntity technology);

  public List<CommunityEntity> findAllByMembersHashedPhoneNumber(String hashedPhoneNumber);
}
