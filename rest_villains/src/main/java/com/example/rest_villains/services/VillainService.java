package com.example.rest_villains.services;

import com.example.rest_villains.data.Villain;
import com.example.rest_villains.data.VillainRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static jakarta.transaction.Transactional.TxType.REQUIRED;
import static jakarta.transaction.Transactional.TxType.SUPPORTS;

@Service
@Transactional(REQUIRED)
public class VillainService {
    private final VillainRepository villainRepository;

    public VillainService(VillainRepository villainRepository) {
        this.villainRepository = villainRepository;
    }

    @Transactional(SUPPORTS)
    public List<Villain> findAllVillains() {
        return villainRepository.findAll();
    }

    @Transactional(SUPPORTS)
    public Villain findVillainById(Long id) {
        var optVillain = villainRepository.findById(id);
        return optVillain.orElse(null);
    }

    @Transactional(SUPPORTS)
    public Villain findRandomVillain() {
        int idx = (int) (Math.random() * villainRepository.count());
        Page<Villain> villainPage = villainRepository.findAll(PageRequest.of(idx, 1));
        Villain v = null;
        if (villainPage.hasContent()) {
            v = villainPage.getContent().get(0);
        }
        return v;
    }

    public Villain saveVillain(@Valid Villain villain) {
        villainRepository.saveAndFlush(villain);
        return villain;
    }

    public Villain updateVillain(@Valid Villain villain) {
        var optVillain = villainRepository.findById(villain.getId());
        if (optVillain.isPresent()) {
            Villain entity = optVillain.get();
            entity.setName(villain.getName());
            entity.setOtherName(villain.getOtherName());
            entity.setLevel(villain.getLevel());
            entity.setPicture(villain.getPicture());
            entity.setPowers(villain.getPowers());
            return entity;
        } else {
            villainRepository.saveAndFlush(villain);
            return villain;
        }
    }

    public void deleteVillain(Long id) {
        villainRepository.deleteById(id);
    }
}
