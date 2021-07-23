package br.org.generation.atividade1.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Atividade1") // caminho para acessar o site
public class AtividadeHelloWorldController {

		@GetMapping
		public String HabilidadeMentalidade() {
			return "Mentalidade: Mentalidade de Crescimento e Persistência | Habilidade: Atenção aos detalhes";
			
		}
		
		@RequestMapping("/Atividade2")
		public String Objetivos() {
			return "Aprender a criar o processo de SpringBoot de uma forma mais eficaz. </br> "
					+ "Assistir a vídeos para conseguir fixar melhor o aprendizado. </br> "
					+ "Aplicar as atividades no projeto integrador";
		}
		
}
