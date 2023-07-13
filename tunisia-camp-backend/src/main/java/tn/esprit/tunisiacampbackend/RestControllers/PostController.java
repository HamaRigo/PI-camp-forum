package tn.esprit.tunisiacampbackend.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.tunisiacampbackend.DAO.DTO.PostDto;
import tn.esprit.tunisiacampbackend.DAO.Entities.Post;
import tn.esprit.tunisiacampbackend.DAO.Entities.User;
import tn.esprit.tunisiacampbackend.Services.PostService;
import tn.esprit.tunisiacampbackend.Services.UserServiceImpl;
import tn.esprit.tunisiacampbackend.exception.PostException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/articles")
public class PostController {
    private final PostService postService;
    private final UserServiceImpl userService;

    @Autowired
    public PostController(PostService postService, UserServiceImpl userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> getAllPosts() {
        HashMap<String,Object> map = new HashMap<>();
        map.put("articles", this.postService.getAll());
        map.put("articlesCount", this.postService.getAll().size());
        return new ResponseEntity<>( map ,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable final Long id) {
        try {
            return new ResponseEntity<>(this.postService.getById(id), HttpStatus.OK);
        } catch (PostException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody final Post post) {
        System.out.println(post);
        /*if(post.getUser() == null) {
            User user = userService.getDefaultUser();
            post.setUser(user);
        }*/
        return new ResponseEntity<>(this.postService.create(post), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@RequestBody final Post post) {
        try {
            return new ResponseEntity<>(this.postService.update(post), HttpStatus.OK);
        } catch (PostException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable final Long id) {
        this.postService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImageOnServer(@RequestParam("file") MultipartFile file) throws IOException {
        this.postService.uploadImage(file);
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}/rate")
    public ResponseEntity<?> ratePost(@PathVariable final Long id, @RequestBody final Integer buttonState) {
        try {
            return new ResponseEntity<>(this.postService.rate(id, buttonState), HttpStatus.OK);
        } catch (PostException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Collection<PostDto>> getPostsPaginated(@PathVariable final Integer pageNumber) {
        return new ResponseEntity<>(this.postService.getAllPaginated(pageNumber), HttpStatus.OK);
    }

//    @GetMapping("/search")
//    public ResponseEntity<Collection<PostDto>> searchPost(String q) {
//        return new ResponseEntity<>(this.postService.search(q), HttpStatus.OK);
//    }
}
