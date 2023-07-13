package tn.esprit.tunisiacampbackend.DAO.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

    private UserDTO user;

    private List<ReactDto> reacts;

    private List<CommentDto> comments;
}
