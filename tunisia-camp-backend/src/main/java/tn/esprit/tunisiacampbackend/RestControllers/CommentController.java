package tn.esprit.tunisiacampbackend.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tunisiacampbackend.DAO.DTO.CommentDto;
import tn.esprit.tunisiacampbackend.DAO.Entities.Comment;
import tn.esprit.tunisiacampbackend.Services.CommentService;
import tn.esprit.tunisiacampbackend.Services.ProhibitedWordService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private ProhibitedWordService prohibitedWordService;

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody final Comment comment) {
        sanitizeComment(comment);
        return new ResponseEntity<>(this.commentService.create(comment), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<CommentDto> updateComment(@RequestBody final Comment comment) {
        sanitizeComment(comment);
        return new ResponseEntity<>(this.commentService.update(comment), HttpStatus.OK);
    }

    public void sanitizeComment(Comment comment) {
        String sanitizedComment = prohibitedWordService.sanitizeText(comment.getContent());
        comment.setContent(sanitizedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable final Long id) {
        this.commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
