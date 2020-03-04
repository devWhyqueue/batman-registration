package de.devwhyqueue.batmanregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Arrays;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Registration {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private State state;
  private LocalDate registrationDate;

  @JsonIgnore
  @ManyToOne
  private User user;
  @Transient
  private Player player;
  @ManyToOne
  private Player partner;
  @ManyToOne
  private TournamentDiscipline tournamentDiscipline;

  public Registration() {
  }

  public Registration(User user, Player player, Player partner,
      TournamentDiscipline tournamentDiscipline, Integer numOfCurrentRegistrations) {
    this.registrationDate = LocalDate.now();
    this.user = user;
    this.player = player;
    this.partner = partner;
    this.tournamentDiscipline = tournamentDiscipline;
    setState(numOfCurrentRegistrations);
  }

  private void setState(Integer numOfRegistrations) {
    if (numOfRegistrations == this.tournamentDiscipline.getCapacity()) {
      this.state = State.WAITING;
    } else {
      this.state = State.PAYMENT_PENDING;
    }
  }

  public void cancel() {
    if (Arrays.asList(State.WAITING, State.PAYMENT_PENDING, State.CANCELLED).contains(this.state)) {
      this.state = State.CANCELLED;
    } else {
      this.state = State.REFUND_PENDING;
    }
  }
}
