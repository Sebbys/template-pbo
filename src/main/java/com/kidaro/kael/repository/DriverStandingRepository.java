package com.kidaro.kael.repository;

import com.kidaro.kael.model.Driver;
import com.kidaro.kael.model.DriverStandings;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DriverStandingRepository extends JpaRepository<DriverStandings, Long> {
    // Add method to find by Driver entity
    Optional<DriverStandings> findByDriver(Driver driver);
}