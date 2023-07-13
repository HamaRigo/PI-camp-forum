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
     * @param user
     * @return The connected user
     * @throws UsernameAlreadyUsedException
     */
    User connect(User user) throws UsernameAlreadyUsedException;

    /**
     * <p>
     * Remove the user from the database.
     * </p>
     *
     * @param user
     */
    User disconnect(User user);

    UserDTO getDefaultUser();
}
