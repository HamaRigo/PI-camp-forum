package tn.esprit.tunisiacampbackend.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.tunisiacampbackend.DAO.DTO.ReactDto;
import tn.esprit.tunisiacampbackend.DAO.DTO.ToDtoConverter;
import tn.esprit.tunisiacampbackend.DAO.Entities.React;
import tn.esprit.tunisiacampbackend.DAO.Repositories.ReactRepo;
import tn.esprit.tunisiacampbackend.exception.ReactException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ReactService {

    private final ReactRepo reactrepo;

    @Autowired
    public ReactService(final ReactRepo reactrepo) {
        this.reactrepo = reactrepo;
    }

    public ReactDto create(final React react) {
        this.reactrepo.save(react);
        return ToDtoConverter.reactToDto(react);
    }

    public List<ReactDto> getAllByPostId(final Long id) {
        Collection<React> foundReacts = this.reactrepo.findByPostId(id);

        return foundReacts.stream()
                .sorted(Comparator.comparing(React::getType).reversed())
                .map(ToDtoConverter::reactToDto)
                .collect(Collectors.toList());
    }

    //    @PreAuthorize("hasRole('USER')")
    public ReactDto update(final React react) {
        this.reactrepo.findById(react.getId()).orElseThrow(
                () -> new ReactException("Can't update. React not found!")
        );
        this.reactrepo.save(react);
        return ToDtoConverter.reactToDto(react);
    }

    //    @PreAuthorize("hasRole('USER')")
    public void delete(final Long id) {
        this.reactrepo.deleteById(id);
    }

}
