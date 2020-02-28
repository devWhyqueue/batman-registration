package de.devwhyqueue.batmanregistration.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Registration {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private Status status;
  private LocalDate registrationDate;

  @ManyToOne
  private Player player;
  @ManyToOne
  private Player partner;
  @ManyToOne
  private TournamentDiscipline tournamentDiscipline;
}
