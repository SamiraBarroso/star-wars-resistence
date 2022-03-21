package br.com.letscode.starwarsresistence.dto;

import br.com.letscode.starwarsresistence.enums.ItemInventory;

import javax.validation.constraints.NotNull;

public record ItemDTO (
        @NotNull
        ItemInventory item,
        @NotNull
        Integer quantity

){
}
