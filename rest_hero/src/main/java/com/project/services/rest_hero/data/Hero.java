package com.project.services.rest_hero.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
    @EqualsAndHashCode.Exclude
    @JsonInclude(JsonInclude.Include.NON_NULL)
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
}
