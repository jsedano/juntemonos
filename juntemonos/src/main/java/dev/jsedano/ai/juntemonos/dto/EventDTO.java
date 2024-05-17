package dev.jsedano.ai.juntemonos.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventDTO {

  private long id;

  private String title;

  private String description;

  private LocalDateTime dateTime;

  private String location;

  private String community;

  private List<String> attendees;
}
