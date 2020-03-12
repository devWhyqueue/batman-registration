package de.devwhyqueue.batmanregistration.resource.dto;

import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.Player;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SingleRegistrationDTO {

  @NotNull
  private Division division;
  @NotNull
  private Player player;
}
