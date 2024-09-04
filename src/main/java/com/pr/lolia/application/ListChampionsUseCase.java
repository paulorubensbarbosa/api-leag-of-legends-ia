package com.pr.lolia.application;

import com.pr.lolia.domain.model.Champion;
import com.pr.lolia.domain.ports.ChampionsRepository;

import java.util.List;

public record ListChampionsUseCase (ChampionsRepository repository) {

    public List<Champion> findAll(){
        return repository.findAll();
    }

}
