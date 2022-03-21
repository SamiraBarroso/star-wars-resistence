package br.com.letscode.starwarsresistence.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public record NegotiationDTO(
    @NotNull
    @Valid
    Long sellerId,

    @NotNull
    @Size(min = 1, max = 4, message = "1 to 4 items")
    @Valid
    List<ItemDTO> buyerItems,

    @NotNull
    @Size(min = 1, max = 4, message = "1 to 4 items")
    @Valid
    List<ItemDTO> sellerItems
) {
}
