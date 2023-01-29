package com.example.rest_villains.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@Data
@Entity
public class Villain {
    @jakarta.persistence.Id
    @Id
    @SequenceGenerator(name = "hibernate_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @EqualsAndHashCode.Exclude
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    private String otherName;
    @NotNull
    @Min(1)
    private int level;

    private String picture;

    @Column(columnDefinition = "text")
    private String powers;
}
