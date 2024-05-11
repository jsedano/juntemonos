package dev.jsedano.juntemonos.juntemonosaiservice.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {

  private long id;

  private String hashedPhoneNumber;

  private String name;

  private List<Community> communities;

  private List<Event> events;
}
