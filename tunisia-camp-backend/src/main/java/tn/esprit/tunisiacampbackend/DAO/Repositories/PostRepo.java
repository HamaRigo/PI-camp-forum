package tn.esprit.tunisiacampbackend.DAO.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.tunisiacampbackend.DAO.Entities.Post;
import java.util.Collection;

public interface PostRepo extends JpaRepository<Post, Long> {
    @Query("SELECT p from  Post p ")
    Collection<Post> findAllSortedByDateTimeOfPost();

    Post findFirstByOrderByIdDesc();

    @Query("from Post p order by p.dateTimeOfPost desc")
    Page<Post> findAll(final Pageable pageable);
}
