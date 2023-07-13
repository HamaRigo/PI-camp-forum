package tn.esprit.tunisiacampbackend.DAO.DTO;


import tn.esprit.tunisiacampbackend.DAO.Entities.Comment;
import tn.esprit.tunisiacampbackend.DAO.Entities.Post;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import tn.esprit.tunisiacampbackend.DAO.Entities.User;

import java.util.ArrayList;
import java.util.List;

public final class ToDtoConverter {

    public static ReactDto reactToDto(final React react) {
        return new ReactDto(
                react.getId(),
                react.getType(),
                userToDto(react.getUser())
        );
    }

    public static CommentDto commentToDto(final Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getDateTimeOfComment(),
                userToDto(comment.getUser())
        );
    }

    public static PostDto postToDto(final Post post) {
        List<CommentDto> commentDtos = new ArrayList<>();
        List<ReactDto> reactDtos = new ArrayList<>();
        if(post.getComments() != null) {
            for (Comment comment : post.getComments()) {
                CommentDto commentDto = commentToDto(comment);
                commentDtos.add(commentDto);
            }
        }
        if(post.getReacts() != null) {
            for (React react : post.getReacts()) {
                ReactDto reactDto = reactToDto(react);
                reactDtos.add(reactDto);
            }
        }
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getDateTimeOfPost(),
                post.getImageUrl(),
                post.getRatingPoints(),
                userToDto(post.getUser()),
                reactDtos,
                commentDtos
        );
    }

    public static UserDTO userToDto(final User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getImage(),
                user.getRole()
        );
    }
}
