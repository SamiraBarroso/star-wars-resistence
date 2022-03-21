package br.com.letscode.starwarsresistence.dto;

import br.com.letscode.starwarsresistence.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record RebelDTO (
        Long id,
        String name,
        int age,
        Genre genre,
        LocationDTO location,
        List<ItemDTO> inventory,
        List<ReportDTO> reports,
        boolean traitor
){}
