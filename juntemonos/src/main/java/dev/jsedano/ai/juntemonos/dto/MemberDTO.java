package dev.jsedano.ai.juntemonos.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberDTO {

  private long id;

  private String hashedPhoneNumber;

  private String nickname;

  private List<String> communities;

  private List<String> events;
}
