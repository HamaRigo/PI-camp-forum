package tn.esprit.tunisiacampbackend.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import java.util.Collection;

public interface ReactRepo extends JpaRepository<React, Long> {
    Collection<React> findByPostId(Long id);
}
