package tn.esprit.tunisiacampbackend.Services;

import tn.esprit.tunisiacampbackend.DAO.DTO.UserDTO;
import tn.esprit.tunisiacampbackend.exception.UsernameAlreadyUsedException;
import tn.esprit.tunisiacampbackend.DAO.Entities.User;
import tn.esprit.tunisiacampbackend.DAO.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userDao;
    public User getdefaultuser() {
        return userDao.findById(1L).orElse(null);
    }
    @Override
    public User connect(User user) throws UsernameAlreadyUsedException {
        User dbUser = userDao.findByUsername(user.getUsername());

        if (dbUser != null) {

            if (dbUser.getConnected()) {
                throw new UsernameAlreadyUsedException("This user is already connected: " + dbUser.getUsername());
            }

            dbUser.setConnected(true);
            return userDao.save(dbUser);
        }

        user.setConnected(true);
        return userDao.save(user);
    }

    @Override
    public User disconnect(User user) {
        if (user == null) {
            return null;
        }

        User dbUser = userDao.findByUsername(user.getUsername());
        if (dbUser == null) {
            return user;
        }

        dbUser.setConnected(false);
        return userDao.save(dbUser);
    }

}