package dev.jsedano.juntemonos.juntemonosapi.repository;

import dev.jsedano.juntemonos.juntemonosapi.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "member")
public interface MemberRepository extends JpaRepository<Member, Long> {}
