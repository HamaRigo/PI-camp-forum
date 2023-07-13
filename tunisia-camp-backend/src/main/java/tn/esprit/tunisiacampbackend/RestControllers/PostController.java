package tn.esprit.tunisiacampbackend.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.tunisiacampbackend.DAO.DTO.PostDto;
import tn.esprit.tunisiacampbackend.DAO.Entities.Post;
import tn.esprit.tunisiacampbackend.Services.PostService;
import tn.esprit.tunisiacampbackend.exception.PostException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/articles")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> getAllPosts() {
        return new ResponseEntity<>(getListPosts(this.postService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public ResponseEntity<HashMap<String,Object>> getPostsPaginated(@RequestParam final Integer page, @RequestParam final Integer limit) {
        return new ResponseEntity<>(getListPosts(this.postService.getAllPaginated(page, limit)), HttpStatus.OK);
    }

    @GetMapping("/paginatedByUser")
    public ResponseEntity<HashMap<String,Object>> getPostsByUserPaginated(@RequestParam final Integer page, @RequestParam final Integer limit, @RequestParam final Long userId) {
        return new ResponseEntity<>(getListPosts(this.postService.getAllByUserPaginated(page, limit, userId)), HttpStatus.OK);
    }

    public HashMap<String,Object> getListPosts(Collection<PostDto> articles) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("articles", articles);
        map.put("articlesCount", this.postService.getPostsCount());
        return map;
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

//    @GetMapping("/search")
//    public ResponseEntity<Collection<PostDto>> searchPost(String q) {
//        return new ResponseEntity<>(this.postService.search(q), HttpStatus.OK);
//    }
}
