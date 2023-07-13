package tn.esprit.tunisiacampbackend.DAO.Repositories;

import tn.esprit.tunisiacampbackend.DAO.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
