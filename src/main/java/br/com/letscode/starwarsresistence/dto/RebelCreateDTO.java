package br.com.letscode.starwarsresistence.dto;

import br.com.letscode.starwarsresistence.enums.Genre;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record RebelCreateDTO(
    @NotEmpty
    String name,

    @NotNull
    int age,

    @NotNull
    Genre genre,

    @NotNull
    @Valid
    LocationDTO location,

    @NotNull
    @Valid
    List<ItemDTO> inventory

) {
}
