package tn.esprit.tunisiacampbackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Indexed
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 3, max = 40)
    private String title;

    @NotNull
    @Size(min = 5, max = 5000)
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    private LocalDateTime dateTimeOfPost = LocalDateTime.now();

    private String imageUrl;

    @NotNull
    @Min(0)
    private Integer ratingPoints;


    @NotNull
    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("post")
    private List<React> reacts;

    @OneToMany(mappedBy="post", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("post")
    private List<Comment> comments;
}
