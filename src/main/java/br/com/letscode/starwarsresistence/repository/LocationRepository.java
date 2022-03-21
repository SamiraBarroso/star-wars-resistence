package br.com.letscode.starwarsresistence.repository;

import br.com.letscode.starwarsresistence.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
