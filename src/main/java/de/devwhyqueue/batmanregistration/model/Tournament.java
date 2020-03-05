package de.devwhyqueue.batmanregistration.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(of = "id")
public class Tournament {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  @Size(min = 1, max = 50)
  private String name;
  @NotNull
  @FutureOrPresent
  private LocalDate start;
  @NotNull
  @FutureOrPresent
  private LocalDate end;
  @NotNull
  @FutureOrPresent
  private LocalDate closeOfEntries;

  public boolean isCloseOfEntriesExceeded() {
    return LocalDate.now().isAfter(this.closeOfEntries);
  }
}
