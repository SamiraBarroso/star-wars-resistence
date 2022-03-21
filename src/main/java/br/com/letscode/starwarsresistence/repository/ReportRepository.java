package br.com.letscode.starwarsresistence.repository;

import br.com.letscode.starwarsresistence.model.Rebel;
import br.com.letscode.starwarsresistence.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByAccuserAndTraitor(Rebel accuserId, Rebel traitorId);

}
