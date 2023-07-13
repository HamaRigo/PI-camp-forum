package tn.esprit.tunisiacampbackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

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

    @UpdateTimestamp
    @NotNull
    private LocalDateTime dateTimeOfComment;

    @NotNull
    @ManyToOne(optional = false)
    private User user;

    @JsonIgnoreProperties("comments")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Post post;
}
