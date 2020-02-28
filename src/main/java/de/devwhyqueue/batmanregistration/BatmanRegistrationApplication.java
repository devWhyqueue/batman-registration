package de.devwhyqueue.batmanregistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

// TODO: Include for security
@SpringBootApplication(exclude = {
    SecurityAutoConfiguration.class
})
public class BatmanRegistrationApplication {

  public static void main(String[] args) {
    SpringApplication.run(BatmanRegistrationApplication.class, args);
  }

}
