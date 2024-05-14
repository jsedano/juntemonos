package dev.jsedano.ai.juntemonos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "community")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Community {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToMany private List<Technology> technologies;

  @ManyToMany private List<Member> members;

  @OneToMany(mappedBy = "community")
  private List<Event> events;
}