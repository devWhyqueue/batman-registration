package de.devwhyqueue.batmanregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "createdByUser")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @Size(min = 1, max = 50)
  private String firstName;
  @NotNull
  @Size(min = 1, max = 50)
  private String lastName;
  @NotNull
  private Gender gender;
  @NotNull
  @Size(min = 1, max = 50)
  private String club;

  @JsonIgnore
  @ManyToOne
  private User createdByUser;
}
