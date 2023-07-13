package tn.esprit.tunisiacampbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tunisiacampbackend.DAO.Entities.ProhibitedWord;
import tn.esprit.tunisiacampbackend.DAO.Repositories.ProhibitedWordRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProhibitedWordService {
    @Autowired
    private ProhibitedWordRepository prohibitedWordRepository;

    public List<String> getAllProhibitedWords() {
        List<String> words = new ArrayList<>();
        List<ProhibitedWord> prohibitedWords = prohibitedWordRepository.findAll();
        for (ProhibitedWord prohibitedWord : prohibitedWords) {
            words.add(prohibitedWord.getWord());
        }
        return words;
    }

    public List<ProhibitedWord> create(String words) {
        String[] wordArray = words.split(",");
        List<ProhibitedWord> prohibitedWords = new ArrayList<>();
        for (String word : wordArray) {
            ProhibitedWord prohibitedWord = new ProhibitedWord();
            prohibitedWord.setWord(word.trim());
            prohibitedWords.add(prohibitedWord);
        }
        return prohibitedWordRepository.saveAll(prohibitedWords);
    }

    public String sanitizeText(String text) {
        List<String> prohibitedWords = getAllProhibitedWords();
        return prohibitedWords.stream()
                .reduce(text, (c, word) -> c.replaceAll(word, "*".repeat(word.length())));
    }
}
