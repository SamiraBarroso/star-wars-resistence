package br.com.letscode.starwarsresistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Genre {
    MALE("MASCULINO"),
    FEMALE("FEMININO"),
    OTHER("OUTRO");

    private String description;
}
