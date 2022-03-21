package br.com.letscode.starwarsresistence.converts;

import br.com.letscode.starwarsresistence.dto.ItemDTO;
import br.com.letscode.starwarsresistence.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item itemDTOToItem(ItemDTO itemDto);
}
