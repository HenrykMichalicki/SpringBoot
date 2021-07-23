package br.org.generation.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // classe controladora, responde requisições dos tipo get, delete, put, post
@RequestMapping("/hello") // o caminho que ele responde a requisição = localhost:8080/hello
public class HelloController {

	@GetMapping // responde as requisições de consulta
	public String hello() {
		return "Hello Emprego 5k";
		
	}
}
