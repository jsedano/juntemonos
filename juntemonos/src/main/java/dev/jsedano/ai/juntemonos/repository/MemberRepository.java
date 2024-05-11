package dev.jsedano.ai.juntemonos.repository;

import dev.jsedano.ai.juntemonos.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Member findByHashedPhoneNumber(String hashedPhoneNumber);
}
