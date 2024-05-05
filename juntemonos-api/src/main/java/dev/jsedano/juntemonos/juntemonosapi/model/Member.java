package dev.jsedano.juntemonos.juntemonosapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "hashed_phone_number")
  private String hashedPhoneNumber;

  @Column(name = "name")
  private String name;

  @ManyToMany(mappedBy = "members")
  private List<Community> communities;

  @ManyToMany(mappedBy = "atendies")
  private List<Event> events;
}
