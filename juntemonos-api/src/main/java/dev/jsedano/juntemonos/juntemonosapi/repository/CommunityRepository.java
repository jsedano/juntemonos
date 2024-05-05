package dev.jsedano.juntemonos.juntemonosapi.repository;

import dev.jsedano.juntemonos.juntemonosapi.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "community")
public interface CommunityRepository extends JpaRepository<Community, Long> {}
