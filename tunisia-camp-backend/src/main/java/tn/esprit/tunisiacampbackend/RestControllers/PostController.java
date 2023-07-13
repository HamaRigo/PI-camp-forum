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
import tn.esprit.tunisiacampbackend.Services.UserService;
import tn.esprit.tunisiacampbackend.Services.UserServiceImpl;

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

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody final Post post) {
        User user = userService.getdefaultuser();
        System.out.println(user);
        post.setUser(user);
        System.out.println(post);
        return new ResponseEntity<>(this.postService.create(post), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<HashMap> getAllPosts() {
//        return new ResponseEntity<>(this.postService.getAll(), HttpStatus.OK);
        HashMap<String,Object> map = new HashMap<>();
        map.put("articles", this.postService.getAll());
        map.put("articlesCount", this.postService.getAll().size());
        return new ResponseEntity<>( map ,HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable final Long id) {
        return new ResponseEntity<>(this.postService.getById(id), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<PostDto> updatePost(@RequestBody final Post post) {
        return new ResponseEntity<>(this.postService.update(post), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePostById(@PathVariable final Long id) {
        this.postService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadImageOnServer(@RequestParam("file") MultipartFile file) throws IOException {
        this.postService.uploadImage(file);
        return new ResponseEntity<>("File is uploaded successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}/rate")
    public ResponseEntity<PostDto> ratePost(@PathVariable final Long id, @RequestBody final Integer buttonState) {
        this.postService.rate(id, buttonState);
        return new ResponseEntity<>(HttpStatus.OK);
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
