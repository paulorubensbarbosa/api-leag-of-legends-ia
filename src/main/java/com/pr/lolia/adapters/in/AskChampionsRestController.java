package com.pr.lolia.adapters.in;

import com.pr.lolia.application.AskChampionsUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Campeões", description = "Endpoints do dominio de campeões do LoL")
@RestController
@RequestMapping("/champions")
public record AskChampionsRestController(AskChampionsUseCase useCase) {

    @PostMapping("/{championId}/ask")
    public AskChampionResponse askChampion(@PathVariable Long championId, @RequestBody AskChampionRequest request){

        String answer = useCase.askChampion(championId, request.question());

        return new AskChampionResponse(answer);
    }

    public record AskChampionRequest(String question){}
    public record AskChampionResponse(String answer){}
    
}
