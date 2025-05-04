package com.kidaro.kael.repository;

import com.kidaro.kael.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
     Optional<Driver> findByName(String name); // Useful for seeding/lookup
}
