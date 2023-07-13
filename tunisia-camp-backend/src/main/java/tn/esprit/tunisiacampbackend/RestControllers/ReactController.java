package tn.esprit.tunisiacampbackend.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tunisiacampbackend.DAO.DTO.ReactDto;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import tn.esprit.tunisiacampbackend.Services.ReactService;

import java.util.Collection;
@CrossOrigin(origins = "*", allowedHeaders = "*")

@RestController
@RequestMapping("/api/reactions")
public class ReactController {
    private final ReactService reactService;

    @Autowired
    public ReactController(final ReactService reactService) {
        this.reactService = reactService;
    }

    @PostMapping
    public ReactDto createReaction(@RequestBody React reaction) {
        return reactService.create(reaction);
    }

    @GetMapping("/post/{id}")
    public ResponseEntity<Collection<ReactDto>> getReactionsByPostId(@PathVariable final Long id) {
        return new ResponseEntity<>(this.reactService.getAllByPostId(id), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<ReactDto> updateReaction(@RequestBody React react) {
        ReactDto updatedReaction = reactService.update(react);
        return ResponseEntity.ok(updatedReaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long id) {
        reactService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
