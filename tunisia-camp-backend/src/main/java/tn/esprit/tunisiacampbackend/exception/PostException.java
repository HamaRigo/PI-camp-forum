package tn.esprit.tunisiacampbackend.exception;

public class PostException extends RuntimeException {
    public PostException(final String postNotFound) {
        super(postNotFound);
    }
}
