package tn.esprit.tunisiacampbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.tunisiacampbackend.DAO.DTO.PostDto;
import tn.esprit.tunisiacampbackend.DAO.DTO.ToDtoConverter;
import tn.esprit.tunisiacampbackend.DAO.Entities.Post;
import tn.esprit.tunisiacampbackend.DAO.Entities.TypeReact;
import tn.esprit.tunisiacampbackend.DAO.Repositories.PostRepository;
import tn.esprit.tunisiacampbackend.DAO.Repositories.ReactRepository;
import tn.esprit.tunisiacampbackend.exception.PostException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Comparator;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ReactRepository reactRepository;

    public Collection<PostDto> getAll() {
        Collection<Post> posts = this.postRepository.findAllSortedByDateTimeOfPost();
        setPostsReactsCount(posts);

        return posts.stream()
                .map(ToDtoConverter::postToDto)
                .collect(Collectors.toList());
    }

    public Collection<PostDto> getMostLiked(final Integer limit, final Long userId) {
        Collection<Post> posts;
        if(userId != null)
            posts = this.postRepository.findAllByReactsIsNotEmptyAndUserId(userId);
        else
            posts = this.postRepository.findAllByReactsIsNotEmpty();
        setPostsReactsCount(posts);

        return posts.stream()
                .sorted(Comparator.comparing(Post::getLikesCount).reversed())
                .limit(limit)
                .map(ToDtoConverter::postToDto)
                .collect(Collectors.toList());
    }

    public Collection<PostDto> getAllPaginated(final Integer pageNumber, final Integer limit, final String type) {
        int index = pageNumber - 1;
        String sortProp = type != null ? type : "dateTimeOfPost";
        Page<Post> posts = this.postRepository.findAll(PageRequest.of(index, limit, Sort.by(sortProp).descending()));
        setPostsReactsCount(posts.getContent());

        return posts.stream()
                .map(ToDtoConverter::postToDto)
                .collect(Collectors.toList());
    }

    public Collection<PostDto> getAllByUserPaginated(final Integer pageNumber, final Integer limit, final Long userId) {
        int index = pageNumber - 1;
        Page<Post> posts = this.postRepository.findAllByUserId(userId, PageRequest.of(index, limit, Sort.by("dateTimeOfPost").descending())); //limit or 20
        setPostsReactsCount(posts.getContent());

        return posts.stream()
                .map(ToDtoConverter::postToDto)
                .collect(Collectors.toList());
    }

    public void setPostsReactsCount(Collection<Post> posts) {
        for(Post post: posts) {
            if(post.getReacts() != null) {
                post.setLikesCount(reactRepository.countByPostAndTypeEquals(post, TypeReact.LIKE));
                post.setDislikesCount(reactRepository.countByPostAndTypeEquals(post, TypeReact.DISLIKE));
            }
        }
    }

    public long getPostsCount() {
        return this.postRepository.count();
    }

    public PostDto getById(final Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(
                () -> new PostException("Can't get. Post not found!")
        );
        if(post.getReacts() != null) {
            post.setLikesCount(reactRepository.countByPostAndTypeEquals(post, TypeReact.LIKE));
            post.setDislikesCount(reactRepository.countByPostAndTypeEquals(post, TypeReact.DISLIKE));
        }
        return ToDtoConverter.postToDto(post);
    }

    //    @PreAuthorize("hasRole('USER')")
    public PostDto create(final Post post) {
        return ToDtoConverter.postToDto(this.postRepository.save(post));
    }

//    @PreAuthorize("hasRole('USER')")
    public PostDto update(final Post post) {
        this.postRepository.findById(post.getId()).orElseThrow(
                () -> new PostException("Can't update. Post not found!")
        );
        return ToDtoConverter.postToDto(this.postRepository.save(post));
    }

//    @PreAuthorize("hasRole('USER')")
    public void delete(final Long id) {
        this.postRepository.findById(id).orElseThrow(
                () -> new PostException("Can't delete. Post not found!")
        );
        this.postRepository.deleteById(id);
    }

    public void uploadImage(final MultipartFile file) throws IOException {
        UUID imgGeneratedId = UUID.nameUUIDFromBytes(file.getBytes());
        File convertFile = new File("src/main/frontend/src/assets/images/" + imgGeneratedId + file.getOriginalFilename());
        Post foundPost = postRepository.findFirstByOrderByIdDesc();
        foundPost.setImageUrl("./assets/images/" + imgGeneratedId + file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fout = new FileOutputStream(convertFile);
        fout.write(file.getBytes());
        fout.close();
        postRepository.save(foundPost);
    }

//    @PreAuthorize("hasRole('USER')")
    public PostDto rate(final Long id, final Integer buttonState) {
        Post foundPost = this.postRepository.findById(id).orElseThrow(
                () -> new PostException("Can't rate. Post not found!")
        );
        if (buttonState.equals(0)) {
            foundPost.setRatingPoints(foundPost.getRatingPoints() - 1);
        } else if (buttonState.equals(1)) {
            foundPost.setRatingPoints(foundPost.getRatingPoints() + 1);
        }
        return ToDtoConverter.postToDto(postRepository.save(foundPost));
    }

//    @SuppressWarnings("unchecked")
//    public Collection search(final String query) {
//        Collection<Post> searchResults;
//        try {
//            searchResults = postSearch.search(query);
//            return searchResults.stream().map(ToDtoConverter::postToDto).collect(Collectors.toList());
//        } catch (Exception ignored) {
//
//        }
//        return null;
//    }
}
