package tn.esprit.tunisiacampbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tunisiacampbackend.DAO.DTO.CommentDto;
import tn.esprit.tunisiacampbackend.DAO.DTO.ToDtoConverter;
import tn.esprit.tunisiacampbackend.DAO.Entities.Comment;
import tn.esprit.tunisiacampbackend.DAO.Repositories.CommentRepository;
import tn.esprit.tunisiacampbackend.exception.CommentException;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

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
        this.commentRepository.findById(id).orElseThrow(
                () -> new CommentException("Can't delete. Comment not found!")
        );
        this.commentRepository.deleteById(id);
    }
}
