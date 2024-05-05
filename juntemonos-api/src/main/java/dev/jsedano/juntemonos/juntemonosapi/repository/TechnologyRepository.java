package dev.jsedano.juntemonos.juntemonosapi.repository;

import dev.jsedano.juntemonos.juntemonosapi.model.Technology;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "technology")
public interface TechnologyRepository extends JpaRepository<Technology, Long> {}
