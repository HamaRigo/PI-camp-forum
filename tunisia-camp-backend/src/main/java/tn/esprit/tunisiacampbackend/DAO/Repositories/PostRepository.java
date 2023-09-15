package tn.esprit.tunisiacampbackend.DAO.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.tunisiacampbackend.DAO.Entities.Post;
import tn.esprit.tunisiacampbackend.DAO.Entities.TypeReact;

import java.util.Collection;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p from  Post p")
    Collection<Post> findAllSortedByDateTimeOfPost();
    Collection<Post> findAllByReactsIsNotEmptyAndReactsTypeEquals(TypeReact typeReact);
    Collection<Post> findAllByReactsIsNotEmptyAndReactsTypeEqualsAndUserId(TypeReact typeReact,Long userId);
    Post findFirstByOrderByIdDesc();
    Page<Post> findAllByUserId(Long userId, final Pageable pageable);
    Page<Post> findAll(final Pageable pageable);
}
