package tn.esprit.tunisiacampbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tunisiacampbackend.DAO.DTO.ReactDto;
import tn.esprit.tunisiacampbackend.DAO.DTO.ToDtoConverter;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import tn.esprit.tunisiacampbackend.DAO.Repositories.ReactRepo;
import tn.esprit.tunisiacampbackend.exception.ReactException;

@Service
public class ReactService {

    private final ReactRepo reactRepo;
    @Autowired
    public ReactService(final ReactRepo reactRepo) {
        this.reactRepo = reactRepo;
    }

    public ReactDto create(final React react) {
        return ToDtoConverter.reactToDto(this.reactRepo.save(react));
    }

    //    @PreAuthorize("hasRole('USER')")
    public ReactDto update(final React react) {
        this.reactRepo.findById(react.getId()).orElseThrow(
                () -> new ReactException("Can't update. React not found!")
        );
        return ToDtoConverter.reactToDto(this.reactRepo.save(react));
    }

    //    @PreAuthorize("hasRole('USER')")
    public void delete(final Long id) {
        this.reactRepo.findById(id).orElseThrow(
                () -> new ReactException("Can't delete. React not found!")
        );
        this.reactRepo.deleteById(id);
    }
}
