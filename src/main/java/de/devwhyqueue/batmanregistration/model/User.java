package de.devwhyqueue.batmanregistration.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity(name = "batman_user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
}
