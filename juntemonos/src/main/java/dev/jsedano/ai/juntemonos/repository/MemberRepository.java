package dev.jsedano.ai.juntemonos.repository;

import dev.jsedano.ai.juntemonos.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
  public MemberEntity findByHashedPhoneNumber(String hashedPhoneNumber);
}
