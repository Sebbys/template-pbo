package com.kidaro.kael.repository;

import com.kidaro.kael.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name); // Useful for seeding/lookup
}
