package tn.esprit.tunisiacampbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tunisiacampbackend.DAO.DTO.CommentDto;
import tn.esprit.tunisiacampbackend.DAO.DTO.ToDtoConverter;
import tn.esprit.tunisiacampbackend.DAO.Entities.Comment;
import tn.esprit.tunisiacampbackend.DAO.Repositories.CommentRepo;
import tn.esprit.tunisiacampbackend.DAO.Repositories.PostRepo;
import tn.esprit.tunisiacampbackend.exception.CommentException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepo commentRepository;

    @Autowired
    public CommentService(final CommentRepo commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentDto create(final Comment comment) {
        this.commentRepository.save(comment);
        return ToDtoConverter.commentToDto(comment);
    }

    public List<CommentDto> getAllByPostId(final Long postId) {
        Collection<Comment> foundComments = this.commentRepository.findByPostId(postId);

        return foundComments.stream()
                .sorted(Comparator.comparing(Comment::getDateTimeOfComment).reversed())
                .map(ToDtoConverter::commentToDto)
                .collect(Collectors.toList());
    }

//    @PreAuthorize("hasRole('USER')")
    public CommentDto update(final Comment comment) {
        this.commentRepository.findById(comment.getId()).orElseThrow(
                () -> new CommentException("Can't update. Comment not found!")
        );
        this.commentRepository.save(comment);
        return ToDtoConverter.commentToDto(comment);
    }

//    @PreAuthorize("hasRole('USER')")
    public void delete(final Long id) {
        this.commentRepository.deleteById(id);
    }
}
