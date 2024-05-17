package dev.jsedano.ai.juntemonos.repository;

import dev.jsedano.ai.juntemonos.entity.TechnologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<TechnologyEntity, Long> {

  public TechnologyEntity findByName(String name);
}
