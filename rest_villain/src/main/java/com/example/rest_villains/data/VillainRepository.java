package com.example.rest_villains.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VillainRepository extends JpaRepository<Villain, Long> {
}
