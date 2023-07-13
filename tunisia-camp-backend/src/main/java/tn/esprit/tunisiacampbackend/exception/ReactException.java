package tn.esprit.tunisiacampbackend.exception;

public class ReactException extends RuntimeException {
    public ReactException(final String ReactNotFound) {
        super(ReactNotFound);
    }

}
