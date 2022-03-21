package br.com.letscode.starwarsresistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long latitude;

    @Column(nullable = false)
    private long longitude;

    @Column(nullable = false)
    private String baseName;

    @OneToOne(mappedBy = "location")
    @JsonBackReference
    private Rebel rebel;
}
