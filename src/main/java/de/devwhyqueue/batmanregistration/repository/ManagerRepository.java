package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {

}
