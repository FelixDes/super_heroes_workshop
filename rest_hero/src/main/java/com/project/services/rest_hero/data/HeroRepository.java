package com.project.services.rest_hero.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface HeroRepository extends ReactiveCrudRepository<Hero, Long> {
}
