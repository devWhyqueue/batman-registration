package de.devwhyqueue.batmanregistration.model;

import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Player extends User {

  private String firstName;
  private String lastName;
  private Gender gender;
  private String club;
}
