package tn.esprit.tunisiacampbackend.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tunisiacampbackend.DAO.Entities.ProhibitedWord;

public interface ProhibitedWordRepository extends JpaRepository<ProhibitedWord, Long> {
}