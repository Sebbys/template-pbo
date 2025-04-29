package com.kidaro.kael.model;

import lombok.*;
import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String color; // Hex code or Tailwind class for color
}
