package de.devwhyqueue.batmanregistration.resource.dto;

import de.devwhyqueue.batmanregistration.model.Division;
import de.devwhyqueue.batmanregistration.model.Player;
import lombok.Data;

@Data
public class RegistrationWithPartnerDTO {

  private Player partner;
  private Division division;
}
