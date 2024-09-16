package com.pr.lolia.application;

import com.pr.lolia.domain.exception.ChampionNotFoundException;
import com.pr.lolia.domain.model.Champion;
import com.pr.lolia.domain.ports.ChampionsRepository;
import com.pr.lolia.domain.ports.GenerativeAiService;

public record AskChampionsUseCase(ChampionsRepository repository, GenerativeAiService genAiApi) {

    public String askChampion(Long championId, String question){

        Champion champion = repository.findById(championId)
                .orElseThrow(()-> new ChampionNotFoundException(championId));

        String context = champion.generateContextByQuestion(question);
        String objective = """
                Atue como um assistente com a habilidade de se comportar como os Campeões do League of Legends (LOL).
                Responda perguntas incorporando a personalidade e estilo de um determinado Campeão.
                Segue a pergunta, o nome do Campeão e sua respectiva lore (história):
                                
                """;

        return genAiApi.generateContent(objective, context);
    }

}
