package tn.esprit.tunisiacampbackend.DAO.DTO;


import tn.esprit.tunisiacampbackend.DAO.Entities.Comment;
import tn.esprit.tunisiacampbackend.DAO.Entities.Post;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import tn.esprit.tunisiacampbackend.DAO.Entities.User;

public final class ToDtoConverter {

    public static ReactDto reactToDto(final React react) {
        return new ReactDto(
                react.getId(),
                react.getType(),
                react.getUser()
//                react.getPost()
        );
    }

    public static CommentDto commentToDto(final Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getDateTimeOfComment(),
                comment.getUser()
//                comment.getPost()
        );
    }

    public static PostDto postToDto(final Post post) {
        return new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getDateTimeOfPost(),
                post.getImageUrl(),
                post.getRatingPoints(),
//                userToDto(post.getUser()),
                post.getUser(),
                post.getReacts(),
                post.getComments()
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
                user.getRole(),
                user.getComments(),
                user.getReacts(),
                user.getPosts()
        );
    }

}
