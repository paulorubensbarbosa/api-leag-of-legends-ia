package com.pr.lolia.application;

import com.pr.lolia.domain.exception.ChampionNotFoundException;
import com.pr.lolia.domain.model.Champion;
import com.pr.lolia.domain.ports.ChampionsRepository;

import java.util.List;

public record AskChampionsUseCase(ChampionsRepository repository) {

    public String askChampion(Long championId, String question){

        Champion champion = repository.findById(championId)
                .orElseThrow(()-> new ChampionNotFoundException(championId));

        String championContext = champion.generateContextByQuestion(question);

        //TODO: Evoluir a lógica de negócio para considerar a integração com as IAs Generativas.

        return championContext;
    }

}
