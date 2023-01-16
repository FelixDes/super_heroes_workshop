package com.example.rest_fight.proxies;

import org.springframework.stereotype.Component;

public interface Proxyable<T> {
    T getRandom();
}
