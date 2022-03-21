package br.com.letscode.starwarsresistence.converts;

import br.com.letscode.starwarsresistence.dto.LocationDTO;
import br.com.letscode.starwarsresistence.model.Location;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDTO locationToLocationDTO(Location location);

    Location locationDTOToLocation(LocationDTO locationDTO);
}
