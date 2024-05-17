package dev.jsedano.ai.juntemonos.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberIdDTO {
  private long id;

  private String hashedPhoneNumber;

  private String nickname;
}
