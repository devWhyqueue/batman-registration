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

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
public class Manager {

  @Id
  @NotNull
  private Long id;

  @JsonIgnore
  @OneToMany(mappedBy = "createdByManager", cascade = CascadeType.REMOVE)
  private List<Registration> registrations;
  @JsonIgnore
  @OneToMany(mappedBy = "createdByManager", cascade = CascadeType.REMOVE)
  private List<Player> players;

  public Manager() {
  }

  public Manager(Long id) {
    this.id = id;
  }
}
