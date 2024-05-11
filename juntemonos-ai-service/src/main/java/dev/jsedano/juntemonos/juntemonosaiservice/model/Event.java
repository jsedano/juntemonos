package dev.jsedano.juntemonos.juntemonosaiservice.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {

  private long id;

  private String title;

  private String description;

  private LocalDateTime dateTime;

  private String location;

  private Community community;

  private List<Member> atendies;
}
