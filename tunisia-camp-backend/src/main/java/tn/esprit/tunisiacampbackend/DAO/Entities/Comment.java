package tn.esprit.tunisiacampbackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 2, max = 500)
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private LocalDateTime dateTimeOfComment = LocalDateTime.now();

    @NotNull
    @ManyToOne(optional = false)
    private User user;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("comments")
    private Post post;
}
