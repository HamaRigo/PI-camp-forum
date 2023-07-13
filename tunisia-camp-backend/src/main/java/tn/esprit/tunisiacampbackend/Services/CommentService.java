package tn.esprit.tunisiacampbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tunisiacampbackend.DAO.DTO.CommentDto;
import tn.esprit.tunisiacampbackend.DAO.DTO.ToDtoConverter;
import tn.esprit.tunisiacampbackend.DAO.Entities.Comment;
import tn.esprit.tunisiacampbackend.DAO.Repositories.CommentRepo;
import tn.esprit.tunisiacampbackend.exception.CommentException;

@Service
public class CommentService {

    private final CommentRepo commentRepository;

    @Autowired
    public CommentService(final CommentRepo commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDto create(final Comment comment) {
        return ToDtoConverter.commentToDto(this.commentRepository.save(comment));
    }

//    @PreAuthorize("hasRole('USER')")
    public CommentDto update(final Comment comment) {
        this.commentRepository.findById(comment.getId()).orElseThrow(
                () -> new CommentException("Can't update. Comment not found!")
        );
        return ToDtoConverter.commentToDto(this.commentRepository.save(comment));
    }

//    @PreAuthorize("hasRole('USER')")
    public void delete(final Long id) {
        this.commentRepository.deleteById(id);
    }
}
