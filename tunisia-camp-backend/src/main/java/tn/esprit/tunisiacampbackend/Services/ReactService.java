package tn.esprit.tunisiacampbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tunisiacampbackend.DAO.DTO.ReactDto;
import tn.esprit.tunisiacampbackend.DAO.DTO.ToDtoConverter;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import tn.esprit.tunisiacampbackend.DAO.Repositories.ReactRepository;
import tn.esprit.tunisiacampbackend.exception.ReactException;

@Service
public class ReactService {
    @Autowired
    private ReactRepository reactRepository;

    public ReactDto create(final React react) {
        return ToDtoConverter.reactToDto(this.reactRepository.save(react));
    }

    public ReactDto update(final React react) {
        this.reactRepository.findById(react.getId()).orElseThrow(
                () -> new ReactException("Can't update. React not found!")
        );
        return ToDtoConverter.reactToDto(this.reactRepository.save(react));
    }

    public void delete(final Long id) {
        this.reactRepository.findById(id).orElseThrow(
                () -> new ReactException("Can't delete. React not found!")
        );
        this.reactRepository.deleteById(id);
    }
}
