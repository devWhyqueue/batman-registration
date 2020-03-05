package de.devwhyqueue.batmanregistration.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class TournamentDiscipline {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @Min(1)
  private Integer capacity;
  @NotNull
  private Double registrationFee;

  @ManyToOne
  private Tournament tournament;
  @JsonManagedReference
  @ManyToOne
  private Discipline discipline;
}
