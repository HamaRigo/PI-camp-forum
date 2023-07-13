package tn.esprit.tunisiacampbackend.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tunisiacampbackend.DAO.DTO.CommentDto;
import tn.esprit.tunisiacampbackend.DAO.Entities.Comment;
import tn.esprit.tunisiacampbackend.Services.CommentService;

import java.util.Collection;
@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(final CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody final Comment comment) {
        return new ResponseEntity<>(this.commentService.create(comment), HttpStatus.OK);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Collection<CommentDto>> getCommentsByPostId(@PathVariable final Long id) {
        return new ResponseEntity<>(this.commentService.getAllByPostId(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestBody final Comment comment) {
        return new ResponseEntity<>(this.commentService.update(comment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCommentById(@PathVariable final Long id) {
        this.commentService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
