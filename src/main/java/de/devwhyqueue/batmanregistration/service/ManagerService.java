package de.devwhyqueue.batmanregistration.service;

import de.devwhyqueue.batmanregistration.model.Manager;
import de.devwhyqueue.batmanregistration.repository.ManagerRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ManagerService {

  private ManagerRepository managerRepository;

  public ManagerService(ManagerRepository managerRepository) {
    this.managerRepository = managerRepository;
  }

  public Manager getOrCreate(Long id) {
    Optional<Manager> manager = this.managerRepository.findById(id);
    if (manager.isEmpty()) {
      return this.managerRepository.save(new Manager(id));
    } else {
      return manager.get();
    }
  }
}
