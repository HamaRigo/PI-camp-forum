package tn.esprit.tunisiacampbackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
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

    @UpdateTimestamp
    @NotNull
    private LocalDateTime dateTimeOfPost;

    private String imageUrl;

    @NotNull
    @Min(0)
    private Integer ratingPoints = 0;

    @Transient
    private Integer likesCount;

    @Transient
    private Integer dislikesCount;

    @JsonIgnoreProperties("posts")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @JsonIgnoreProperties("post")
    @OneToMany(mappedBy="post", cascade = CascadeType.REMOVE)
    private List<React> reacts;

    @JsonIgnoreProperties("post")
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}
