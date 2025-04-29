package com.kidaro.kael.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Priority {
    @Id @GeneratedValue
    private Long id;
    private String level; // e.g., High, Medium
    private String color; // Hex code or Tailwind class for color
}

