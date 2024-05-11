package dev.jsedano.ai.juntemonos.juntemonosapi.repository;

import dev.jsedano.ai.juntemonos.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {}
