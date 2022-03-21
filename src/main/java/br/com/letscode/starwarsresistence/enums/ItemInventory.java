package br.com.letscode.starwarsresistence.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum ItemInventory {
    GUN(4),
    MUNITION(3),
    WATER(2),
    FOOD(1);

    private final Integer points;

}
