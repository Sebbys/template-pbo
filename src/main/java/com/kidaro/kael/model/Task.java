package com.kidaro.kael.model;
import lombok.*;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;



@Entity
@Data // Lombok annotation to generate getters, setters, equals, hashCode, and toString
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate a constructor with all fields
public class Task {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private boolean done;
    private LocalDate dueDate;

    @ManyToOne
    private Priority priority;

    @ManyToMany
    private Set<Tag> tags = new HashSet<>();
}

