package de.devwhyqueue.batmanregistration.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(of = "id")
@ToString(exclude = "tournamentDisciplines")
public class Discipline {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull
  private DisciplineType disciplineType;
  @NotNull
  private FieldType fieldType;

  @ManyToOne
  private Division division;
  @JsonBackReference
  @OneToMany(mappedBy = "discipline")
  private List<TournamentDiscipline> tournamentDisciplines;
}
