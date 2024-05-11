package dev.jsedano.ai.juntemonos.juntemonosapi.repository;

import dev.jsedano.ai.juntemonos.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {}
