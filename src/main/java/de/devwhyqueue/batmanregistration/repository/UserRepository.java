package de.devwhyqueue.batmanregistration.repository;

import de.devwhyqueue.batmanregistration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
