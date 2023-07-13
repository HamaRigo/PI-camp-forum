package tn.esprit.tunisiacampbackend.DAO.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.tunisiacampbackend.DAO.Entities.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDTO {
    Long id;
    String username;
    String firstName;
    String lastName;
    String email;
    String password;
    int phoneNumber;
    String image;
    Role role;
}
