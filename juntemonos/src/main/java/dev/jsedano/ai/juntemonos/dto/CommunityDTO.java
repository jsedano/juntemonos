package dev.jsedano.ai.juntemonos.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommunityDTO {

  private long id;

  private String name;

  private String description;

  private List<String> technologies;
}
