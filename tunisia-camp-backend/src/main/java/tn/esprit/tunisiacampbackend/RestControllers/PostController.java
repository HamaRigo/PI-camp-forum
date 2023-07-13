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
import tn.esprit.tunisiacampbackend.Services.ProhibitedWordService;
import tn.esprit.tunisiacampbackend.exception.PostException;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/articles")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private ProhibitedWordService prohibitedWordService;

    @GetMapping
    public ResponseEntity<HashMap<String,Object>> getAllPosts() {
        return new ResponseEntity<>(getListPosts(this.postService.getAll()), HttpStatus.OK);
    }

    @GetMapping("/liked")
    public ResponseEntity<HashMap<String,Object>> getAllPostsLiked(@RequestParam final Integer limit, @RequestParam(required = false) final Long userId) {
        Collection<PostDto> posts = this.postService.getMostLiked(limit, userId);
        HashMap<String, Object> myMap = new HashMap<String, Object>() {{
            put("articles", posts);
            put("articlesCount", posts.size());
        }};
        return new ResponseEntity<>(myMap, HttpStatus.OK);
    }

    @GetMapping("/paginated")
    public ResponseEntity<HashMap<String,Object>> getPostsPaginated(@RequestParam final Integer page, @RequestParam final Integer limit, @RequestParam(required = false) final String type) {
        return new ResponseEntity<>(getListPosts(this.postService.getAllPaginated(page, limit, type)), HttpStatus.OK);
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
        sanitizePostContent(post);
        return new ResponseEntity<>(this.postService.create(post), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updatePost(@RequestBody final Post post) {
        try {
            sanitizePostContent(post);
            return new ResponseEntity<>(this.postService.update(post), HttpStatus.OK);
        } catch (PostException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    public void sanitizePostContent(Post post) {
        String sanitizedPostContent = prohibitedWordService.sanitizeText(post.getContent());
        post.setContent(sanitizedPostContent);
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
}
