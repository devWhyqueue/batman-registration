package de.devwhyqueue.batmanregistration.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(of = "name")
@ToString(of = "name")
public class Division {

  @Id
  @NotNull
  private String name;

  @JsonIgnore
  @OneToMany(mappedBy = "division")
  private List<Discipline> disciplines;
}
