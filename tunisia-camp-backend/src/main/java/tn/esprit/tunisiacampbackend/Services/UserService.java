package tn.esprit.tunisiacampbackend.Services;

import tn.esprit.tunisiacampbackend.DAO.DTO.UserDTO;
import tn.esprit.tunisiacampbackend.exception.UsernameAlreadyUsedException;
import tn.esprit.tunisiacampbackend.DAO.Entities.User;

public interface UserService {
    /**
     * <p>
     * Find the user by its user name. If the user is not saved yet,
     * then save the user into database, otherwise throw a {@link UsernameAlreadyUsedException}
     * </p>
     *
     * @param username
     * @return The connected user
     * @throws UsernameAlreadyUsedException
     */
    User connect(String username) throws UsernameAlreadyUsedException;

    /**
     * <p>
     * Remove the user from the database.
     * </p>
     *
     * @param username
     */
    User disconnect(String username);

    UserDTO getDefaultUser();
}
