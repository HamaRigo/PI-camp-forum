package tn.esprit.tunisiacampbackend.DAO.Entities;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
//@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private Boolean connected = false;

    String firstName;
    String lastName;
    String email;
    String password;
    int phoneNumber;
    String image;
    @Enumerated(EnumType.STRING)
    Role role;

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Post> posts;

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<React> reacts;

    @JsonIgnore
    @OneToMany(mappedBy="user")
    private List<Comment> comments;

//    @OneToMany(mappedBy = "user")
//    List<Order> orders ;

    //    @OneToMany(mappedBy = "user")
//    List<Feedback> feedbackList;
    public User() {
        super();
    }

    public User(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + "]";
    }

}
