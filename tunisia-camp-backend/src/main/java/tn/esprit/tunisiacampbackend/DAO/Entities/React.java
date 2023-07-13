package tn.esprit.tunisiacampbackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class React {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private TypeReact type;

    @NotNull
    @ManyToOne(optional = false)
    private User user;

    @JsonIgnoreProperties("reacts")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Post post;
}
