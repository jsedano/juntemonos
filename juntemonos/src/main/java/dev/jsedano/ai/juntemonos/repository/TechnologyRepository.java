package dev.jsedano.ai.juntemonos.repository;

import dev.jsedano.ai.juntemonos.model.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {

  public Technology findByName(String name);
}
