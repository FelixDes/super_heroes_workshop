package com.project.services.rest_fight.proxies;

import com.project.services.rest_fight.client.Villain;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "villain-proxy", url = "${villain_proxy_random.url}", fallback = VillainProxy.VillainProxyFallback.class)
public interface VillainProxy extends Proxyable<Villain> {
    @GetMapping("${villain_proxy_random.path}")
    Villain getRandom();

    @Component
    class VillainProxyFallback implements VillainProxy, Proxyable<Villain>  {

        @Override
        public Villain getRandom() {
            Villain villain = new Villain();
            villain.setName("Fallback villain");
            villain.setPicture("https://dummyimage.com/280x380/b22222/ffffff&text=Fallback+Villain");
            villain.setPowers("Fallback villain powers");
            villain.setLevel(42);
            return villain;
        }
    }
}
