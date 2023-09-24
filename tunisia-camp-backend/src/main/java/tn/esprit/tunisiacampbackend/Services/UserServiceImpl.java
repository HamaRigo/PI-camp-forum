package tn.esprit.tunisiacampbackend.Services;

import tn.esprit.tunisiacampbackend.DAO.DTO.ToDtoConverter;
import tn.esprit.tunisiacampbackend.DAO.DTO.UserDTO;
import tn.esprit.tunisiacampbackend.exception.UsernameAlreadyUsedException;
import tn.esprit.tunisiacampbackend.DAO.Entities.User;
import tn.esprit.tunisiacampbackend.DAO.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO getDefaultUser() {
        User defaultUser = userRepository.findById(1L).orElse(null);
        if(defaultUser != null)
            return ToDtoConverter.userToDto(defaultUser);
        else
            return null;
    }

    @Override
    public User connect(String username) throws UsernameAlreadyUsedException {
        User dbUser = userRepository.findByUsername(username);

        if (dbUser != null) {

            if (dbUser.getConnected()) {
                throw new UsernameAlreadyUsedException("This user is already connected: " + dbUser.getUsername());
            }

            dbUser.setConnected(true);
            return userRepository.save(dbUser);
        }
        return null;
    }

    @Override
    public User disconnect(String username) {
        if (username == null) {
            return null;
        }

        User dbUser = userRepository.findByUsername(username);
//        if (dbUser != null) {
            dbUser.setConnected(false);
            return userRepository.save(dbUser);
        }
//        return null;
//    }
}