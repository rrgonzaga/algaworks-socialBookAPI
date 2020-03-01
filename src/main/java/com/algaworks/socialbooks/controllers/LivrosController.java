package com.algaworks.socialbooks.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.socialbooks.domain.Livro;
import com.algaworks.socialbooks.repository.LivrosRepository;
/**
 * 
 * @author Rodrigo
 * @apiNote quando acessar http://localhost:8080/h2-console/, colocar jdbc:h2:mem:testdb para JDBC URL.
 */
@RestController
@RequestMapping("/livros")
public class LivrosController {
	
	@Autowired
	private LivrosRepository livrosRepository;
	
	/**
	 * Obtém todos os livros cadastrados
	 * @implNote O método GET obtém um ou mais recursos
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public List<Livro> listar() {
		
//		Livro l1 = new Livro("Rest Aplicado");
//		Livro l2 = new Livro("Git passo a passo");
//		
//		Livro[] livros = {l1,l2};
//		
//		return Arrays.asList(livros);
		
		return livrosRepository.findAll();
		
	}
	
	/**
	 * Criar um novo livro no BD
	 * @implNote O método POST é responsável por criar um novo recurso
	 * @param livro
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Livro salvar(@RequestBody Livro livro) {
		return livrosRepository.save(livro);
	}

}
