package com.example.rest_villains.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
public class Villain {
    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    private String otherName;
    @NotNull
    @Min(1)
    private int level;

    private String picture;

    //@Column(columnDefinition = "TEXT")
    private String powers;

    @Override
    public String toString() {
        // JSONObject from org.json gives level: [42] so it's hard to use in tests
        var str = new StringBuilder();
        str.append("{");
        if (Objects.nonNull(id)) str.append(String.format("\"id\": %d,", id));
        if (Objects.nonNull(name)) str.append(String.format("\"name\": \"%s\",", name));
        if (Objects.nonNull(otherName)) str.append(String.format("\"otherName\": \"%s\",", otherName));
        if (Objects.nonNull(picture)) str.append(String.format("\"picture\": \"%s\",", picture));
        if (Objects.nonNull(powers)) str.append(String.format("\"powers\": \"%s\",", powers));
        str.append(String.format("\"level\": %d", level));
        str.append("}");
        return str.toString();
    }
}
