package com.project.services.statistics.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@AllArgsConstructor
@Data
public class User {
    private String name;
    private Set<String> roles;

}
