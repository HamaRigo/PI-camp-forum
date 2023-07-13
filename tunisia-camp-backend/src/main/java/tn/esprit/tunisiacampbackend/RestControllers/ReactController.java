package tn.esprit.tunisiacampbackend.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tunisiacampbackend.DAO.DTO.ReactDto;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import tn.esprit.tunisiacampbackend.Services.ReactService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/reactions")
public class ReactController {
    @Autowired
    private ReactService reactService;

    @PostMapping
    public ResponseEntity<ReactDto> createReaction(@RequestBody React reaction) {
        return new ResponseEntity<>(reactService.create(reaction), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ReactDto> updateReaction(@RequestBody React react) {
        return new ResponseEntity<>(reactService.update(react), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReaction(@PathVariable Long id) {
        reactService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
