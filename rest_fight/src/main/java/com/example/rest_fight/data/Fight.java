package com.example.rest_fight.data;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
public class Fight {
    public static enum Teams {
        HEROES("heroes"),
        VILLAINS("villains");
        private String val;

        Teams(String val) {
            this.val = val;
        }

        public String getString() {
            return val;
        }
    }

    @Id
    @SequenceGenerator(name="hibernate_sequence",sequenceName="hibernate_sequence", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="hibernate_sequence")
    private Long id;
    @NotNull
    private Instant fightDate;
    @NotNull
    private String winnerName;
    @NotNull
    private int winnerLevel;
    @NotNull
    private String winnerPicture;
    @NotNull
    private String loserName;
    @NotNull
    private int loserLevel;
    @NotNull
    private String loserPicture;
    @NotNull
    private String winnerTeam;
    @NotNull
    private String loserTeam;

}