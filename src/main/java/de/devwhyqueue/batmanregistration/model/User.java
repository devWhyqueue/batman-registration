package de.devwhyqueue.batmanregistration.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "batman_user")
@Data
@EqualsAndHashCode
public class User {

  @Id
  private Long id;

  public User() {
  }

  public User(Long id) {
    this.id = id;
  }
}
