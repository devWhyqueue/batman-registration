package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.User;
import de.devwhyqueue.batmanregistration.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User getOrCreate(Long id) {
    return this.userRepository.findById(id).orElse(this.userRepository.save(new User(id)));
  }
}
