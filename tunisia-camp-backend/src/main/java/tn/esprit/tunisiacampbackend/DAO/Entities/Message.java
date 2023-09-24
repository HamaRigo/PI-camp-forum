package tn.esprit.tunisiacampbackend.DAO.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @JsonIgnore
    @Id
    @GeneratedValue
    private Long id;

    private String channel;

    private String sender;

    private String receiver;

    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date readDate;
}