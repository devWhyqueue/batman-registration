package de.devwhyqueue.batmanregistration.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

  private DisciplineType disciplineType;
  private FieldType fieldType;

  @JsonManagedReference
  @ManyToOne
  private Division division;
  @JsonBackReference
  @OneToMany(mappedBy = "discipline")
  private List<TournamentDiscipline> tournamentDisciplines;
}
