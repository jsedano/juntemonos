package dev.jsedano.ai.juntemonos.repository;

import dev.jsedano.ai.juntemonos.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<Community, Long> {

    public Community findByName(String name);
}
