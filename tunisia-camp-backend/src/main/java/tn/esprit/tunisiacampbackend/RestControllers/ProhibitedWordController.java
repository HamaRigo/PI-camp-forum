package tn.esprit.tunisiacampbackend.RestControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.tunisiacampbackend.DAO.Entities.ProhibitedWord;
import tn.esprit.tunisiacampbackend.Services.ProhibitedWordService;

import java.util.List;

@RestController
@RequestMapping("/prohibited-words")
public class ProhibitedWordController {
    @Autowired
    private ProhibitedWordService prohibitedWordService;

    @PostMapping
    public ResponseEntity<List<ProhibitedWord>> createProhibitedWords(@RequestParam String words) {
        return new ResponseEntity<>(prohibitedWordService.create(words), HttpStatus.OK);
    }
}