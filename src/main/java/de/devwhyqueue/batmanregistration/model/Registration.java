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

  public boolean isCancellationRequestedOrCancelled() {
    return Arrays.asList(State.REFUND_PENDING, State.CANCELLED).contains(this.state);
  }

  public void cancel() {
    if (Arrays.asList(State.WAITING, State.PAYMENT_PENDING, State.CANCELLED).contains(this.state)) {
      this.state = State.CANCELLED;
    } else {
      this.state = State.REFUND_PENDING;
    }
  }

}
