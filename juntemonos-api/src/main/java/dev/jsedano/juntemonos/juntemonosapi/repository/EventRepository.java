package dev.jsedano.juntemonos.juntemonosapi.repository;

import dev.jsedano.juntemonos.juntemonosapi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "event")
public interface EventRepository extends JpaRepository<Event, Long> {}
