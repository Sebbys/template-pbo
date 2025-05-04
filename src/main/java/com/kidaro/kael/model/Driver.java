package com.kidaro.kael.model;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY) // Many drivers can belong to one team
    @JoinColumn(name = "team_id") // Foreign key column in the Driver table
    private Team team;

    // Add other driver details if needed, e.g., nationality, number
}
