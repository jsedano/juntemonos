package dev.jsedano.juntemonos.juntemonosaiservice.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Technology {

  private long id;

  private String name;

  private String description;

  private List<Community> communities;
}
