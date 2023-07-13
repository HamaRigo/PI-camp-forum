package tn.esprit.tunisiacampbackend.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tunisiacampbackend.DAO.Entities.Post;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import tn.esprit.tunisiacampbackend.DAO.Entities.TypeReact;

public interface ReactRepository extends JpaRepository<React, Long> {
    Integer countByPostAndTypeEquals(Post post, TypeReact type);
}
