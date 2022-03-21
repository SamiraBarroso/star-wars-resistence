package br.com.letscode.starwarsresistence.converts;

import br.com.letscode.starwarsresistence.dto.RebelCreateDTO;
import br.com.letscode.starwarsresistence.dto.RebelDTO;
import br.com.letscode.starwarsresistence.model.Rebel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RebelMapper {

    RebelDTO rebelToRebelDTO(Rebel rebel);

    Rebel rebelCreateDTOTORebel(RebelCreateDTO rebelCreateDTO);

   // Rebel rebelDTOToRebel(RebelCreateDto rebelCreateDto);
}
