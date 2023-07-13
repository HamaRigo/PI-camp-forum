package tn.esprit.tunisiacampbackend.DAO.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tunisiacampbackend.DAO.Entities.Comment;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    Collection<Comment> findByPostId(final Long postId);
}
