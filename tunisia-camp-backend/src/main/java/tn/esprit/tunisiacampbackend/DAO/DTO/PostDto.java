package tn.esprit.tunisiacampbackend.DAO.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.tunisiacampbackend.DAO.Entities.Comment;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import tn.esprit.tunisiacampbackend.DAO.Entities.User;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class PostDto {
    private Long id;

    private String title;

    private String content;

    private LocalDateTime dateTimeOfPost;

    private String imageUrl;

    private Integer ratingPoints;


    private User user;

    private List<React> reacts;

    private List<Comment> comments;
}
