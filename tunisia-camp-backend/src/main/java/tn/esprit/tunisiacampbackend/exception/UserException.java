package tn.esprit.tunisiacampbackend.exception;

public class UserException extends RuntimeException{
    public UserException(final String userNotFound) {
        super(userNotFound);
    }
}
