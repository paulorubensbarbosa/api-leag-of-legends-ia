package com.pr.lolia;

import com.pr.lolia.application.AskChampionsUseCase;
import com.pr.lolia.application.ListChampionsUseCase;
import com.pr.lolia.domain.ports.ChampionsRepository;
import com.pr.lolia.domain.ports.GenerativeAiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionsRepository championsRepository){
		return new ListChampionsUseCase(championsRepository);
	}

	@Bean
	public AskChampionsUseCase provideAksChampionUseCase(ChampionsRepository championsRepository, GenerativeAiService genAiService){
		return new AskChampionsUseCase(championsRepository, genAiService);
	}

}
