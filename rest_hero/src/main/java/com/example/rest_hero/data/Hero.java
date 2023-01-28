package com.example.rest_hero.data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hero /*implements Persistable<Long>*/ {
    @Id
    private Long id;
    private String name;
    private String otherName;

    @NotNull
    @Min(1)
    private int level;
    private String picture;
    private String powers;

//    @Transient
//    private boolean newHero;
//
//    @Override
//    @Transient
//    public boolean isNew() {
//        return this.newHero || id == null;
//    }
//
//    public Hero setAsNew() {
//        this.newHero = true;
//        return this;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Hero hero = (Hero) o;

        if (level != hero.level) return false;
        if (!Objects.equals(name, hero.name)) return false;
        if (!Objects.equals(otherName, hero.otherName)) return false;
        if (!Objects.equals(picture, hero.picture)) return false;
        return Objects.equals(powers, hero.powers);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (otherName != null ? otherName.hashCode() : 0);
        result = 31 * result + level;
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (powers != null ? powers.hashCode() : 0);
        return result;
    }

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
