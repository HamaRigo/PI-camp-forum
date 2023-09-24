package tn.esprit.tunisiacampbackend.RestControllers;

import tn.esprit.tunisiacampbackend.DAO.DTO.UserDTO;
import tn.esprit.tunisiacampbackend.DAO.Entities.User;
import tn.esprit.tunisiacampbackend.DAO.Repositories.UserRepository;
import tn.esprit.tunisiacampbackend.Services.UserService;
import tn.esprit.tunisiacampbackend.exception.UsernameAlreadyUsedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class ConnectionController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userDao;

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> login(@RequestBody String username) throws UsernameAlreadyUsedException {
            User connectedUser = userService.connect(username);
            template.convertAndSend("/channel/login", connectedUser);

            return new ResponseEntity<>(connectedUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(@RequestBody String username) {
        User disconnectedUser = userService.disconnect(username);
        template.convertAndSend("/channel/logout", disconnectedUser);
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<User> findConnectedUsers() {
        return userDao.findAll();
    }

    @RequestMapping(value = "/clearAlluser", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearAll() {
        userDao.deleteAll();
    }

    @GetMapping(value = "/defaultUser")
    public ResponseEntity<UserDTO> defaultUser() {
        return new ResponseEntity<>(this.userService.getDefaultUser(), HttpStatus.OK);
    }
}