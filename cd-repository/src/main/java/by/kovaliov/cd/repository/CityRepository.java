package by.kovaliov.cd.repository;

import by.kovaliov.cd.model.City;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

  List<City> findByNameIgnoreCase(String name, Pageable pageable);
}