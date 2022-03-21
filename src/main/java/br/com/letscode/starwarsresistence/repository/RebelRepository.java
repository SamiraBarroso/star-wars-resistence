package br.com.letscode.starwarsresistence.repository;

import br.com.letscode.starwarsresistence.model.Rebel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RebelRepository extends JpaRepository<Rebel, Long> {
}
