package de.devwhyqueue.batmanregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "batman_user")
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class User {

  @Id
  @NotNull
  private Long id;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<Registration> registrations;
  @JsonIgnore
  @OneToMany(mappedBy = "createdByUser", cascade = CascadeType.REMOVE)
  private List<Player> partners;

  public User() {
  }

  public User(Long id) {
    this.id = id;
  }
}
