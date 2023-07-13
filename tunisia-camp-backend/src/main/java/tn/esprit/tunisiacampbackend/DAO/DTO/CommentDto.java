package tn.esprit.tunisiacampbackend.DAO.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private Long id;

    private String content;

    private LocalDateTime dateTimeOfComment;

    private UserDTO user;
}
