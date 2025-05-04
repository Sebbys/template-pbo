package com.kidaro.kael.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class DriverStandings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY) // Each standing entry is for one specific driver
    @JoinColumn(name = "driver_id", unique = true) // Foreign key, ensure one standing per driver
    private Driver driver;

    private int points;
}
