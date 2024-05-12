package dev.jsedano.ai.juntemonos.repository;

import dev.jsedano.ai.juntemonos.model.Community;
import dev.jsedano.ai.juntemonos.model.Technology;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

  public Community findByName(String name);

  public List<Community> findAllByTechnologies(Technology technology);

  public List<Community> findAllByMembersHashedPhoneNumber(String hashedPhoneNumber);
}
