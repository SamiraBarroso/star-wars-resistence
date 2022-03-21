package br.com.letscode.starwarsresistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Report implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accuser_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Rebel accuser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "traitor_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Rebel traitor;
}