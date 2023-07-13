package tn.esprit.tunisiacampbackend.DAO.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.tunisiacampbackend.DAO.Entities.Post;
import tn.esprit.tunisiacampbackend.DAO.Entities.TypeReact;
import tn.esprit.tunisiacampbackend.DAO.Entities.User;

@Getter
@Setter
@AllArgsConstructor
public class ReactDto {
    private Long id;
    private TypeReact type;
    private User user;
//    private Post post;
}
