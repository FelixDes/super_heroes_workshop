package com.example.rest_fight.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface FightRepository extends JpaRepository<Fight, Long> {
}
