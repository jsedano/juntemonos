package dev.jsedano.ai.juntemonos.repository;

import dev.jsedano.ai.juntemonos.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {}
