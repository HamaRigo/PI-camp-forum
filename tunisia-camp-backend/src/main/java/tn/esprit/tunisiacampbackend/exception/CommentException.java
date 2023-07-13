package tn.esprit.tunisiacampbackend.exception;

public class CommentException extends RuntimeException {
    public CommentException(final String commentNotFound) {
        super(commentNotFound);
    }
}
